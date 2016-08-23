package org.freemars.ui.player;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import org.freemars.player.PlayerSummaryPanel;
import org.freemars.ui.image.FreeMarsImageManager;
import org.freemars.ui.image.ImagePanel;
import org.freemars.ui.util.FreeMarsDialog;

/**
 *
 * @author Deniz ARIKAN
 */
public class VictoryDialog extends FreeMarsDialog {

    private final int FRAME_WIDTH = 700;
    private final int FRAME_HEIGHT = 600;
    private JPanel pageStartPanel;
    private ImagePanel victoryImagePanel;
    private JPanel messagePanel;
    private JScrollPane scrollPane;
    private JTextArea textArea;
    private PlayerSummaryPanel playerSummaryPanel;
    private JPanel pageEndPanel;
    private JButton closeDialogAndOpenMainMenuButton;
    private JButton continuePlayingButton;

    public VictoryDialog(Frame owner) {
        super(owner);
        setTitle("Victory!");
    }

    public void setPopulationValueLabelText(String text) {
        getPlayerSummaryPanel().setPopulationValueLabelText(text);
    }

    public void setMostPopulousColonyValueLabelText(String text) {
        getPlayerSummaryPanel().setMostPopulousColonyValueLabelText(text);
    }

    public void setNumberOfColoniesValueLabelText(String text) {
        getPlayerSummaryPanel().setNumberOfColoniesValueLabelText(text);
    }

    public void setNumberOfUnitsValueLabelText(String text) {
        getPlayerSummaryPanel().setNumberOfUnitsValueLabelText(text);
    }

    public void setMapExploredValueLabelText(String text) {
        getPlayerSummaryPanel().setMapExploredValueLabelText(text);
    }

    public void setTimeOnMarsValueLabelText(String text) {
        getPlayerSummaryPanel().setTimeOnMarsValueLabelText(text);
    }

    public void setWaterProductionValueLabelText(String text) {
        getPlayerSummaryPanel().setWaterProductionValueLabelText(text);
    }

    public void setWaterConsumptionValueLabelText(String text) {
        getPlayerSummaryPanel().setWaterConsumptionValueLabelText(text);
    }

    public void setFoodProductionValueLabelText(String text) {
        getPlayerSummaryPanel().setFoodProductionValueLabelText(text);
    }

    public void setFoodConsumptionValueLabelText(String text) {
        getPlayerSummaryPanel().setFoodConsumptionValueLabelText(text);
    }

    public void setWealthValueValueLabelText(String text) {
        getPlayerSummaryPanel().setWealthValueValueLabelText(text);
    }

    public void setWealthPerColonistValueLabelText(String text) {
        getPlayerSummaryPanel().setWealthPerColonistValueLabelText(text);
    }

    public void setEarthTaxValueLabelText(String text) {
        getPlayerSummaryPanel().setEarthTaxValueLabelText(text);
    }

    public void setColonialTaxValueLabelText(String text) {
        getPlayerSummaryPanel().setColonialTaxValueLabelText(text);
    }

    public void setColonialTaxIncomeValueLabelText(String text) {
        getPlayerSummaryPanel().setColonialTaxIncomeValueLabelText(text);
    }

    public void setTotalUpkeepValueLabelText(String text) {
        getPlayerSummaryPanel().setTotalUpkeepValueLabelText(text);
    }

    public void setIncomeFromExportsValueLabelText(String text) {
        getPlayerSummaryPanel().setIncomeFromExportsValueLabelText(text);
    }

    public void setTotalTaxPaidToEarthValueLabelText(String text) {
        getPlayerSummaryPanel().setTotalTaxPaidToEarthValueLabelText(text);
    }

    public void setProfitFromExportsValueLabelText(String text) {
        getPlayerSummaryPanel().setProfitFromExportsValueLabelText(text);
    }

    public void setFavoriteExportValueLabelText(String text) {
        getPlayerSummaryPanel().setFavoriteExportValueLabelText(text);
    }

    public void setMostProfitableExportValueLabelText(String text) {
        getPlayerSummaryPanel().setMostProfitableExportValueLabelText(text);
    }

    public void setFarmersValueLabelText(String text) {
        getPlayerSummaryPanel().setFarmersValueLabelText(text);
    }

    public void setIronMinersValueLabelText(String text) {
        getPlayerSummaryPanel().setIronMinersValueLabelText(text);
    }

    public void setSilicaMinersValueLabelText(String text) {
        getPlayerSummaryPanel().setSilicaMinersValueLabelText(text);
    }

    public void setMineralMinersValueLabelText(String text) {
        getPlayerSummaryPanel().setMineralMinersValueLabelText(text);
    }

    public void setCloseDialogAndOpenMainMenuButtonAction(Action action) {
        getCloseDialogAndOpenMainMenuButton().setAction(action);
        getCloseDialogAndOpenMainMenuButton().setText("Return to main menu");
    }

    public void setContinuePlayingButtonAction(Action action) {
        getContinuePlayingButton().setAction(action);
        getContinuePlayingButton().setText("Continue playing");
    }

    public void display() {
        getContentPane().setLayout(new BorderLayout());
        getContentPane().setBackground(Color.white);
        initializeWidgets();
        pack();
        final java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width - FRAME_WIDTH) / 2, (screenSize.height - FRAME_HEIGHT) / 2, FRAME_WIDTH, FRAME_HEIGHT);
        setVisible(true);
        toFront();
    }

    private void initializeWidgets() {
        add(Box.createHorizontalStrut(10), BorderLayout.LINE_START);
        add(getPageStartPanel(), BorderLayout.PAGE_START);
        add(getPlayerSummaryPanel(), BorderLayout.CENTER);
        add(getPageEndPanel(), BorderLayout.PAGE_END);
        add(Box.createHorizontalStrut(10), BorderLayout.LINE_END);
    }

    private JPanel getPageStartPanel() {
        if (pageStartPanel == null) {
            pageStartPanel = new JPanel(new BorderLayout());
            pageStartPanel.setBackground(Color.white);
            pageStartPanel.add(Box.createHorizontalStrut(10), BorderLayout.LINE_START);
            pageStartPanel.add(getVictoryImagePanel(), BorderLayout.CENTER);
            pageStartPanel.add(getMessagePanel(), BorderLayout.PAGE_END);
            pageStartPanel.add(Box.createHorizontalStrut(10), BorderLayout.LINE_END);
        }
        return pageStartPanel;
    }

    private ImagePanel getVictoryImagePanel() {
        if (victoryImagePanel == null) {
            victoryImagePanel = new ImagePanel(FreeMarsImageManager.getImage("HUMAN_PLAYER_VICTORIOUS"));
            victoryImagePanel.setPreferredSize(new Dimension(FRAME_WIDTH, 100));
        }
        return victoryImagePanel;
    }

    private JPanel getMessagePanel() {
        if (messagePanel == null) {
            messagePanel = new JPanel(new BorderLayout());
            messagePanel.setBackground(Color.white);
            messagePanel.add(Box.createHorizontalStrut(10), BorderLayout.LINE_START);
            messagePanel.add(getScrollPane(), BorderLayout.CENTER);
            messagePanel.add(Box.createHorizontalStrut(10), BorderLayout.LINE_END);
        }
        return messagePanel;
    }

    private JScrollPane getScrollPane() {
        if (scrollPane == null) {
            scrollPane = new JScrollPane(getTextArea());
            scrollPane.setBorder(null);
        }
        return scrollPane;
    }

    private JTextArea getTextArea() {
        if (textArea == null) {
            textArea = new JTextArea();
            textArea.setEditable(false);
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setFont(new Font("Arial", 1, 12));
            textArea.append("\n");
            textArea.append("We are victorious! We have defeated the entire Earth ");
            textArea.append("expeditionary force and have reached our objectives. ");
            textArea.append("Mars is free and will remain free. ");
            textArea.append("\n\n");
        }
        return textArea;
    }

    private PlayerSummaryPanel getPlayerSummaryPanel() {
        if (playerSummaryPanel == null) {
            playerSummaryPanel = new PlayerSummaryPanel();
            playerSummaryPanel.setBackground(Color.white);
        }
        return playerSummaryPanel;
    }

    private JPanel getPageEndPanel() {
        if (pageEndPanel == null) {
            pageEndPanel = new JPanel();
            pageEndPanel.add(getCloseDialogAndOpenMainMenuButton());
            pageEndPanel.add(getContinuePlayingButton());
            pageEndPanel.setBackground(Color.white);
        }
        return pageEndPanel;
    }

    private JButton getCloseDialogAndOpenMainMenuButton() {
        if (closeDialogAndOpenMainMenuButton == null) {
            closeDialogAndOpenMainMenuButton = new JButton();
        }
        return closeDialogAndOpenMainMenuButton;
    }

    private JButton getContinuePlayingButton() {
        if (continuePlayingButton == null) {
            continuePlayingButton = new JButton();
        }
        return continuePlayingButton;
    }
}
