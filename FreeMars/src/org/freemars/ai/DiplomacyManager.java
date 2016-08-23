package org.freemars.ai;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.freemars.controller.FreeMarsController;
import org.freerealm.Realm;
import org.freerealm.command.SendResourceGiftCommand;
import org.freerealm.command.SendWealthGiftCommand;
import org.freerealm.diplomacy.PlayerRelation;
import org.freerealm.player.Player;
import org.freerealm.resource.Resource;
import org.freerealm.settlement.Settlement;

/**
 *
 * @author Deniz ARIKAN
 */
public class DiplomacyManager {

    private static final Logger logger = Logger.getLogger(DiplomacyManager.class);
    private final FreeMarsController freeMarsController;
    private final AIPlayer aiPlayer;
    private final Random random = new Random(System.currentTimeMillis());

    public DiplomacyManager(FreeMarsController freeMarsController, AIPlayer aiPlayer) {
        this.freeMarsController = freeMarsController;
        this.aiPlayer = aiPlayer;
    }

    public void manage() {
        Iterator<Player> playersIterator = freeMarsController.getFreeMarsModel().getPlayersIterator();
        while (playersIterator.hasNext()) {
            Player player = playersIterator.next();
            if (player.isActive() && !player.equals(aiPlayer)) {
                PlayerRelation playerRelation = aiPlayer.getDiplomacy().getPlayerRelation(player);
                if (playerRelation.getStatus() != PlayerRelation.NO_CONTACT) {
                    int attitudeTo = aiPlayer.getDiplomacy().getPlayerRelation(player).getAttitude();
                    int attitudeFrom = player.getDiplomacy().getPlayerRelation(aiPlayer).getAttitude();
                    int attitudeDifference = attitudeTo - attitudeFrom;
                    if (logger.isTraceEnabled()) {
                        StringBuilder log = new StringBuilder();
                        log.append("Managing diplomacy with ");
                        log.append(player.getName());
                        log.append(". Attitude difference is ");
                        log.append(attitudeDifference);
                        log.append(".");
                        logger.trace(log);
                    }
                    if (attitudeDifference > 50 && isSendingGift()) {
                        switch (getGiftType()) {
                            case 0:
                                sendCreditGift(player);
                                break;
                            case 1:
                                sendResourceGift(player);
                                break;
                            case 2:
                                sendUnitGift();
                                break;
                        }
                    }
                }
            }
        }
    }

    private boolean isSendingGift() {
        random.setSeed(System.currentTimeMillis());
        int randomInt = random.nextInt(5);
        return randomInt == 0;
    }

    private int getGiftType() {
        random.setSeed(System.currentTimeMillis());
        return random.nextInt(2);
    }

    private void sendResourceGift(Player toPlayer) {
        List<ResourceGiftOption> resourceGiftOptions = new ArrayList<ResourceGiftOption>();
        Iterator<Settlement> settlementsIterator = aiPlayer.getSettlementsIterator();
        while (settlementsIterator.hasNext()) {
            Settlement settlement = settlementsIterator.next();
            Iterator<Resource> resourcesIterator = settlement.getContainedResourcesIterator();
            while (resourcesIterator.hasNext()) {
                Resource resource = resourcesIterator.next();
                int resourceQuantity = settlement.getResourceQuantity(resource);
                if (resourceQuantity > 500) {
                    Iterator<Settlement> toSettlementsIterator = toPlayer.getSettlementsIterator();
                    while (toSettlementsIterator.hasNext()) {
                        Settlement toSettlement = toSettlementsIterator.next();
                        resourceGiftOptions.add(new ResourceGiftOption(settlement, toSettlement, resource));
                    }
                }
            }
        }
        int optionCount = resourceGiftOptions.size();
        random.setSeed(System.currentTimeMillis());
        ResourceGiftOption resourceGiftOption = resourceGiftOptions.get(random.nextInt(optionCount));
        Settlement fromSettlement = resourceGiftOption.getFromSettlement();
        Settlement toSettlement = resourceGiftOption.getToSettlement();
        Resource giftResource = resourceGiftOption.getResource();
        int maximumMultiplier = fromSettlement.getResourceQuantity(giftResource) / 100;
        random.setSeed(System.currentTimeMillis());
        int resourceGiftQuantity = 100 * (random.nextInt(maximumMultiplier) + 3);
        int resourceUnitValue = (int) 1.5 * freeMarsController.getFreeMarsModel().getEarth().getEarthBuysAtPrice(giftResource);
        Realm realm = freeMarsController.getFreeMarsModel().getRealm();
        SendResourceGiftCommand sendResourceGiftCommand
                = new SendResourceGiftCommand(realm, fromSettlement, toSettlement, giftResource, resourceGiftQuantity, resourceUnitValue);
        freeMarsController.execute(sendResourceGiftCommand);
    }

    private void sendCreditGift(Player toPlayer) {
        random.setSeed(System.currentTimeMillis());
        int creditGiftMultiplier = random.nextInt(10) + 2;
        int amount = 500 * creditGiftMultiplier;
        if (aiPlayer.getWealth() > amount) {
            SendWealthGiftCommand sendCreditGiftCommand
                    = new SendWealthGiftCommand(freeMarsController.getFreeMarsModel().getRealm(), aiPlayer, toPlayer, amount);
            freeMarsController.execute(sendCreditGiftCommand);
        }
    }

    private void sendUnitGift() {
    }

    private class ResourceGiftOption {

        private final Settlement fromSettlement;
        private final Settlement toSettlement;
        private final Resource resource;

        public ResourceGiftOption(Settlement fromSettlement, Settlement toSettlement, Resource resource) {
            this.fromSettlement = fromSettlement;
            this.toSettlement = toSettlement;
            this.resource = resource;
        }

        public Settlement getFromSettlement() {
            return fromSettlement;
        }

        public Settlement getToSettlement() {
            return toSettlement;
        }

        public Resource getResource() {
            return resource;
        }

    }

}
