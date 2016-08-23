package org.freemars.diplomacy;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Image;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import org.freemars.ui.image.ImagePanel;
import org.freemars.ui.util.FreeMarsDialog;

/**
 *
 * @author Deniz ARIKAN
 */
public class NegotiationDialog extends FreeMarsDialog {

    private final int FRAME_WIDTH = 600;
    private final int FRAME_HEIGHT = 340;
    private JPanel pageStartPanel;
    private ImagePanel player1FlagImagePanel;
    private JPanel diplomaticStatusPanel;
    private JLabel diplomaticStatusLabel;
    private JPanel attitudesPanel;
    private JLabel player1ToPlayer2AttitudeLabel;
    private JLabel player2ToPlayer1AttitudeLabel;
    private ImagePanel player2FlagImagePanel;
    private JPanel centerPanel;
    private JPanel diplomaticActionsPanel;
    private JButton exchangeMapsButton;
    private JButton offerGiftButton;
    private JButton offerPeaceButton;
    private JButton offerAllianceButton;
    private JButton breakAllianceButton;
    private JButton declareWarButton;

    public NegotiationDialog(Frame owner) {
        super(owner);
        setModal(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setTitle("Negotiation");
        initializeWidgets();
    }

    public void display() {
        pack();
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width - FRAME_WIDTH) / 2, (screenSize.height - FRAME_HEIGHT) / 2, FRAME_WIDTH, FRAME_HEIGHT);
        setVisible(true);
        toFront();
    }

    public void updateDiplomaticActionButtons() {
        int buttonsAdded = 0;
        getDiplomaticActionsPanel().removeAll();
        if (getExchangeMapsButton().getAction() != null) {
            getDiplomaticActionsPanel().add(getExchangeMapsButton());
            buttonsAdded = buttonsAdded + 1;
        }
        if (getOfferGiftButton().getAction() != null) {
            getDiplomaticActionsPanel().add(getOfferGiftButton());
            buttonsAdded = buttonsAdded + 1;
        }
        if (getOfferPeaceButton().getAction() != null) {
            getDiplomaticActionsPanel().add(getOfferPeaceButton());
            buttonsAdded = buttonsAdded + 1;
        }
        if (getOfferAllianceButton().getAction() != null) {
            getDiplomaticActionsPanel().add(getOfferAllianceButton());
            buttonsAdded = buttonsAdded + 1;
        }
        if (getBreakAllianceButton().getAction() != null) {
            getDiplomaticActionsPanel().add(getBreakAllianceButton());
            buttonsAdded = buttonsAdded + 1;
        }
        if (getDeclareWarButton().getAction() != null) {
            getDiplomaticActionsPanel().add(getDeclareWarButton());
            buttonsAdded = buttonsAdded + 1;
        }
        for (int i = buttonsAdded; i < 4; i++) {
            getDiplomaticActionsPanel().add(new JLabel());
        }
        repaint();
    }

    public void setPlayer1FlagImage(Image image) {
        getPlayer1FlagImagePanel().setImage(image);
    }

    public void setPlayer2FlagImage(Image image) {
        getPlayer2FlagImagePanel().setImage(image);
    }

    public void setDiplomaticStatusLabelText(String text) {
        getDiplomaticStatusLabel().setText(text);
    }

    public void setExchangeMapsButtonAction(Action action) {
        getExchangeMapsButton().setAction(action);
    }

    public void setOfferGiftButtonAction(Action action) {
        getOfferGiftButton().setAction(action);
    }

    public void setOfferAllianceButtonAction(Action action) {
        getOfferAllianceButton().setAction(action);
    }

    public void setBreakAllianceButtonAction(Action action) {
        getBreakAllianceButton().setAction(action);
    }

    public void setOfferPeaceButtonAction(Action action) {
        getOfferPeaceButton().setAction(action);
    }

    public void setDeclareWarButtonAction(Action action) {
        getDeclareWarButton().setAction(action);
    }

    public void setPlayer1ToPlayer2AttitudeLabelText(String text) {
        getPlayer1ToPlayer2AttitudeLabel().setText(text);
    }

    public void setPlayer2ToPlayer1AttitudeLabelText(String text) {
        getPlayer2ToPlayer1Attitude().setText(text);
    }

    private void initializeWidgets() {
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(getPageStartPanel(), BorderLayout.PAGE_START);
        getContentPane().add(getCenterPanel(), BorderLayout.CENTER);
    }

    private JPanel getPageStartPanel() {
        if (pageStartPanel == null) {
            pageStartPanel = new JPanel(new BorderLayout());
            pageStartPanel.setPreferredSize(new Dimension(600, 80));
            pageStartPanel.add(getPlayer1FlagImagePanel(), BorderLayout.LINE_START);
            pageStartPanel.add(getDiplomaticStatusPanel(), BorderLayout.CENTER);
            pageStartPanel.add(getPlayer2FlagImagePanel(), BorderLayout.LINE_END);
        }
        return pageStartPanel;
    }

    private JPanel getDiplomaticStatusPanel() {
        if (diplomaticStatusPanel == null) {
            diplomaticStatusPanel = new JPanel(new BorderLayout());
            diplomaticStatusPanel.add(getDiplomaticStatusLabel(), BorderLayout.CENTER);
            diplomaticStatusPanel.add(getAttitudesPanel(), BorderLayout.PAGE_END);
        }
        return diplomaticStatusPanel;
    }

    private JLabel getDiplomaticStatusLabel() {
        if (diplomaticStatusLabel == null) {
            diplomaticStatusLabel = new JLabel();
            diplomaticStatusLabel.setFont(diplomaticStatusLabel.getFont().deriveFont(16f));
            diplomaticStatusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        }
        return diplomaticStatusLabel;
    }

    private JPanel getAttitudesPanel() {
        if (attitudesPanel == null) {
            attitudesPanel = new JPanel(new GridLayout(1, 0));
            attitudesPanel.add(getPlayer1ToPlayer2AttitudeLabel());
            attitudesPanel.add(getPlayer2ToPlayer1Attitude());
        }
        return attitudesPanel;
    }

    private JLabel getPlayer1ToPlayer2AttitudeLabel() {
        if (player1ToPlayer2AttitudeLabel == null) {
            player1ToPlayer2AttitudeLabel = new JLabel();
            player1ToPlayer2AttitudeLabel.setHorizontalAlignment(SwingConstants.LEFT);
        }
        return player1ToPlayer2AttitudeLabel;
    }

    private JLabel getPlayer2ToPlayer1Attitude() {
        if (player2ToPlayer1AttitudeLabel == null) {
            player2ToPlayer1AttitudeLabel = new JLabel();
            player2ToPlayer1AttitudeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        }
        return player2ToPlayer1AttitudeLabel;
    }

    private ImagePanel getPlayer1FlagImagePanel() {
        if (player1FlagImagePanel == null) {
            player1FlagImagePanel = new ImagePanel();
            player1FlagImagePanel.setPreferredSize(new Dimension(140, 60));
            player1FlagImagePanel.setImageHorizontalAlignment(CENTER_ALIGNMENT);
            player1FlagImagePanel.setImageVerticalAlignment(CENTER_ALIGNMENT);
        }
        return player1FlagImagePanel;
    }

    private ImagePanel getPlayer2FlagImagePanel() {
        if (player2FlagImagePanel == null) {
            player2FlagImagePanel = new ImagePanel();
            player2FlagImagePanel.setPreferredSize(new Dimension(140, 60));
            player2FlagImagePanel.setImageHorizontalAlignment(CENTER_ALIGNMENT);
            player2FlagImagePanel.setImageVerticalAlignment(CENTER_ALIGNMENT);
        }
        return player2FlagImagePanel;
    }

    private JPanel getCenterPanel() {
        if (centerPanel == null) {
            int space = 20;
            centerPanel = new JPanel(new BorderLayout());
            centerPanel.add(Box.createVerticalStrut(space), BorderLayout.PAGE_START);
            centerPanel.add(Box.createHorizontalStrut(space * 3), BorderLayout.LINE_START);
            centerPanel.add(getDiplomaticActionsPanel(), BorderLayout.CENTER);
            centerPanel.add(Box.createHorizontalStrut(space * 3), BorderLayout.LINE_END);
            centerPanel.add(Box.createVerticalStrut(space), BorderLayout.PAGE_END);

        }
        return centerPanel;
    }

    private JPanel getDiplomaticActionsPanel() {
        if (diplomaticActionsPanel == null) {
            diplomaticActionsPanel = new JPanel(new GridLayout(0, 1, 0, 10));

        }
        return diplomaticActionsPanel;
    }

    private JButton getExchangeMapsButton() {
        if (exchangeMapsButton == null) {
            exchangeMapsButton = new JButton();
        }
        return exchangeMapsButton;
    }

    private JButton getOfferGiftButton() {
        if (offerGiftButton == null) {
            offerGiftButton = new JButton();
        }
        return offerGiftButton;
    }

    private JButton getOfferPeaceButton() {
        if (offerPeaceButton == null) {
            offerPeaceButton = new JButton();
        }
        return offerPeaceButton;
    }

    private JButton getOfferAllianceButton() {
        if (offerAllianceButton == null) {
            offerAllianceButton = new JButton();
        }
        return offerAllianceButton;
    }

    private JButton getBreakAllianceButton() {
        if (breakAllianceButton == null) {
            breakAllianceButton = new JButton();
        }
        return breakAllianceButton;
    }

    private JButton getDeclareWarButton() {
        if (declareWarButton == null) {
            declareWarButton = new JButton();
        }
        return declareWarButton;
    }

}
