package org.freemars.ui.map.tile;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import org.freerealm.RealmConstants;
import org.freerealm.map.Direction;

/**
 *
 * @author Deniz ARIKAN
 */
public class TilePainter {

    public static void paintTileTerrain(Graphics graphics, TilePaintModel tilePaintModel, Point paintPoint) {
        Image centerImage = tilePaintModel.getPaintImage(RealmConstants.CENTER);
        graphics.drawImage(centerImage, paintPoint.x, paintPoint.y, null);
        for (Direction direction : RealmConstants.directions) {
            if (!direction.equals(RealmConstants.CENTER) && tilePaintModel.getPaintImage(direction) != null) {
                Image image = tilePaintModel.getPaintImage(direction);
                graphics.drawImage(image, paintPoint.x, paintPoint.y, null);
            }
        }
    }

    public static void paintTileBonusResource(Graphics graphics, TilePaintModel tilePaintModel, Point paintPoint) {
        if (tilePaintModel.getBonusResourceImage() != null) {
            Image resourceBonusImage = tilePaintModel.getBonusResourceImage();
            int imageWidth = resourceBonusImage.getWidth(null);
            int imageHeight = resourceBonusImage.getHeight(null);
            int imageX = (int) paintPoint.x + (tilePaintModel.getWidth() / 2) - (imageWidth / 2);
            int imageY = (int) paintPoint.y + tilePaintModel.getHeight() / 2 - imageHeight / 2;
            graphics.drawImage(resourceBonusImage, imageX, imageY, null);
        }
    }

    public static void paintTileVegatation(Graphics graphics, TilePaintModel tilePaintModel, Point paintPoint) {
        if (tilePaintModel.getVegetationImage() != null) {
            Image vegetationImage = tilePaintModel.getVegetationImage();
            graphics.drawImage(vegetationImage, paintPoint.x, paintPoint.y, null);
        }
    }

    public static void paintTileInfo(Graphics graphics, TilePaintModel tilePaintModel, Point paintPoint) {
        if (tilePaintModel.isDisplayingGridLines()) {
            graphics.setColor(tilePaintModel.getGridLineColor());
            paintRhombus(graphics, tilePaintModel, paintPoint);
        }
        List<String> textToPaint = new ArrayList<String>();
        if (tilePaintModel.isDisplayingTileType()) {
            textToPaint.add(tilePaintModel.getTileTypeName());
        }
        if (tilePaintModel.isDisplayingCoordinate()) {
            textToPaint.add(tilePaintModel.getTileCoordinate().toString());
        }
        int i = 0;
        Point textPaintPoint = new Point(paintPoint.x - 4, paintPoint.y + 4);
        if (textToPaint.size() == 2) {
            textPaintPoint.y = textPaintPoint.y - 10;
        }
        int fontSize = 12;
        for (String string : textToPaint) {
            textPaintPoint.y = textPaintPoint.y + (i * 15);
            paintText(graphics, tilePaintModel, textPaintPoint, string, new Font("Arial", Font.BOLD, fontSize), tilePaintModel.getGridTextColor());
            i++;
        }
    }

    private static void paintRhombus(Graphics graphics, TilePaintModel tilePaintModel, Point point) {
        int x1 = (int) point.x;
        int x2 = x1 + tilePaintModel.getWidth() / 2;
        int x3 = x1 + tilePaintModel.getWidth();
        int y1 = (int) point.y;
        int y2 = y1 + tilePaintModel.getHeight() / 2;
        int y3 = y1 + tilePaintModel.getHeight();
        graphics.drawLine(x1, y2, x2, y1);
        graphics.drawLine(x2, y1, x3, y2);
        graphics.drawLine(x3, y2, x2, y3);
        graphics.drawLine(x1, y2, x2, y3);
    }

    private static void paintText(Graphics graphics, TilePaintModel tilePaintModel, Point point, String text, Font font, Color color) {
        graphics.setFont(font);
        graphics.setColor(color);
        int textAbscissa = (int) point.x + tilePaintModel.getWidth() * 3 / 8;
        int textOrdinate = (int) point.y + tilePaintModel.getHeight() / 2;
        graphics.drawString(text, textAbscissa, textOrdinate);
    }
}
