package org.freemars.diplomacy;

import java.awt.Dimension;
import org.freemars.controller.FreeMarsController;
import org.freemars.ui.image.FreeMarsImageManager;
import org.freemars.ui.util.FreeMarsImageDialog;
import org.freemars.util.Utility;
import org.freerealm.player.Player;

/**
 *
 * @author Deniz ARIKAN
 */
public class NoDiplomacyAllowedWithUnitsDialog extends FreeMarsImageDialog {

    private static final int FRAME_WIDTH = 540;
    private static final int FRAME_HEIGHT = 390;

    public NoDiplomacyAllowedWithUnitsDialog(FreeMarsController freeMarsController, Player otherPlayer) {
        super(freeMarsController.getCurrentFrame());
        setImagePreferredSize(new Dimension(130, 250));
        setImage(FreeMarsImageManager.getImage("NO_DIPLOMATIC_RELATIONS"));

        String diplomaticRelationsEnableTurnProperty = freeMarsController.getFreeMarsModel().getRealm().getProperty("diplomatic_relations_enable_turn");
        int diplomaticRelationsEnableTurn = Integer.parseInt(diplomaticRelationsEnableTurnProperty);
        int remainingTime = diplomaticRelationsEnableTurn - freeMarsController.getFreeMarsModel().getNumberOfTurns();

        StringBuilder message = new StringBuilder();
        message.append("<br><br>");
        message.append("Sir,");
        message.append("<br><br>");
        message.append("This unit belongs to ");
        message.append(otherPlayer.getNation().getAdjective());
        message.append(" colonists.");
        message.append("<br><br>");
        message.append("According to the International Treaty for Mars Colonization (ITMC) ");
        message.append("we can not enter dipomatic relations with other colonists for the ");
        message.append("first ");
        message.append(Utility.convertTurnCountToTimePeriod(diplomaticRelationsEnableTurn));
        message.append(" of Martian colonization.");
        message.append("<br><br>");
        message.append("During this period, diplomatic relations between Mars colonists ");
        message.append("will be managed by their related Earth governments.");
        message.append("<br><br>");
        message.append("There is still ");
        message.append(Utility.convertTurnCountToTimePeriod(remainingTime));
        message.append(" to start our own diplomatic relations.");

        appendText(message.toString());

    }

    public void display() {
        pack();
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width - FRAME_WIDTH) / 2, (screenSize.height - FRAME_HEIGHT) / 2, FRAME_WIDTH, FRAME_HEIGHT);
        setVisible(true);
        toFront();
    }
}
