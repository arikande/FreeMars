package org.freerealm.map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.freerealm.Realm;
import org.freerealm.diplomacy.PlayerRelation;
import org.freerealm.player.Player;
import org.freerealm.property.MoveProperty;
import org.freerealm.settlement.Settlement;
import org.freerealm.tile.Tile;
import org.freerealm.tile.improvement.TileImprovementType;
import org.freerealm.unit.Unit;

public class AStarPathFinder implements PathFinder {

    private final Realm realm;
    private final ArrayList<Node> closed = new ArrayList<Node>();
    private final ArrayList<Node> open = new ArrayList<Node>();
    private final int maxSearchDistance;
    private final Node[][] nodes;

    public AStarPathFinder(Realm realm, int maxSearchDistance) {
        this.realm = realm;
        this.maxSearchDistance = maxSearchDistance;
        nodes = new Node[realm.getMapWidth()][realm.getMapHeight()];
        for (int x = 0; x < realm.getMapWidth(); x++) {
            for (int y = 0; y < realm.getMapHeight(); y++) {
                nodes[x][y] = new Node(x, y);
            }
        }
    }

    public Path findPath(Unit unit, Coordinate target) {
        return findPath(unit, unit.getCoordinate(), target);
    }

    public Path findPath(Unit unit, Coordinate start, Coordinate target) {
        if (!isValidLocation(unit, target)) {
            return null;
        }
        nodes[start.getAbscissa()][start.getOrdinate()].cost = 0;
        nodes[start.getAbscissa()][start.getOrdinate()].depth = 0;
        closed.clear();
        open.clear();
        open.add(nodes[start.getAbscissa()][start.getOrdinate()]);
        nodes[target.getAbscissa()][target.getOrdinate()].parent = null;
        int maxDepth = 0;
        while ((maxDepth < maxSearchDistance) && (!open.isEmpty())) {
            Node current = open.get(0);
            if (current == nodes[target.getAbscissa()][target.getOrdinate()]) {
                break;
            }
            open.remove(current);
            closed.add(current);
            Coordinate currentCoordinate = new Coordinate(current.x, current.y);

            List<Coordinate> neighborCoordinates = realm.getCircleCoordinates(currentCoordinate, 1);
            for (Coordinate neighborCoordinate : neighborCoordinates) {
                if (isValidLocation(unit, neighborCoordinate)) {
                    Tile tile = realm.getTile(neighborCoordinate);
                    float nextStepCost = current.cost + tile.getMovementCost();
                    Node neighbour = nodes[neighborCoordinate.getAbscissa()][neighborCoordinate.getOrdinate()];
                    if (nextStepCost < neighbour.cost) {
                        if (open.contains(neighbour)) {
                            open.remove(neighbour);
                        }
                        if (closed.contains(neighbour)) {
                            closed.remove(neighbour);
                        }
                    }
                    if (!open.contains(neighbour) && !(closed.contains(neighbour))) {
                        neighbour.cost = nextStepCost;
                        neighbour.heuristic = tile.getMovementCost();
                        maxDepth = Math.max(maxDepth, neighbour.setParent(current));
                        open.add(neighbour);
                        Collections.<Node>sort(open);
                    }
                }
            }
        }
        if (nodes[target.getAbscissa()][target.getOrdinate()].parent == null) {
            return null;
        }
        Path path = new Path();
        Node targetNode = nodes[target.getAbscissa()][target.getOrdinate()];
        while (targetNode != nodes[start.getAbscissa()][start.getOrdinate()]) {
            path.prependStep(targetNode.x, targetNode.y);
            targetNode = targetNode.parent;
        }
        return path;
    }

    private boolean isValidLocation(Unit unit, Coordinate coordinate) {
        if (coordinate == null) {
            return false;
        }
        if (coordinate.getOrdinate() < 0) {
            return false;
        }
        Tile tile = realm.getTile(coordinate);
        if (tile == null) {
            return false;
        }
        if (!unit.getPlayer().isCoordinateExplored(coordinate)) {
            return false;
        }
        MoveProperty moveAbility = (MoveProperty) unit.getType().getProperty(MoveProperty.NAME);
        if (moveAbility == null) {
            return false;
        }

        if (isCoordinateBlockedByOtherPlayers(unit.getPlayer(), coordinate)) {
            return false;
        }

        //TODO : Use set movement point property instead of checking for roads
        TileImprovementType road = realm.getTileImprovementTypeManager().getImprovement("Road");
        if (tile.hasImprovement(road)) {
            return true;
        }

        if (!moveAbility.hasTileType(tile.getType())) {
            return false;
        }
        return true;
    }

    private boolean isCoordinateBlockedByOtherPlayers(Player movingPlayer, Coordinate coordinate) {
        Tile tile = realm.getTile(coordinate);
        Player tileOwnerPlayer = realm.getTileOwner(coordinate);
        if (tileOwnerPlayer == null || movingPlayer.equals(tileOwnerPlayer)) {
            return false;
        }
        PlayerRelation playerRelation = tileOwnerPlayer.getDiplomacy().getPlayerRelation(movingPlayer);
        if (playerRelation.getStatus() == PlayerRelation.AT_WAR) {
            return false;
        }
        if (tile.getSettlement() != null) {
            Settlement settlement = tile.getSettlement();
            if (!movingPlayer.equals(settlement.getPlayer())) {
                return true;
            }
        }
        if (tile.getNumberOfUnits() > 0) {
            Unit tileUnit = tile.getFirstUnit();
            if (!movingPlayer.equals(tileUnit.getPlayer())) {
                return true;
            }
        }
        if (playerRelation.getStatus() == PlayerRelation.NO_DIPLOMACY_ALLOWED || playerRelation.getStatus() == PlayerRelation.AT_PEACE) {
            return true;
        }
        return false;
    }

    private class Node implements Comparable {

        private int x;
        private int y;
        private float cost;
        private Node parent;
        private float heuristic;
        private int depth;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int setParent(Node parent) {
            depth = parent.depth + 1;
            this.parent = parent;
            return depth;
        }

        public int compareTo(Object other) {
            Node o = (Node) other;
            float f = heuristic + cost;
            float of = o.heuristic + o.cost;
            if (f < of) {
                return -1;
            } else if (f > of) {
                return 1;
            } else {
                return 0;
            }
        }

        @Override
        public String toString() {
            String string = new String();
            string = string + x + "," + y + " - " + cost;
            return string;
        }
    }
}
