package org.freemars.ui.player;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.freemars.player.PlayerSummaryPanel;
import org.freemars.ui.util.FreeMarsDialog;

/**
 *
 * @author Deniz ARIKAN
 */
public class DemographicsDialog extends FreeMarsDialog {

    private final int FRAME_WIDTH = 740;
    private final int FRAME_HEIGHT = 450;
    private JPanel pageEndPanel;
    private JButton closeButton;
    private PlayerSummaryPanel playerSummaryPanel;

    public DemographicsDialog(JFrame parent) {
        super(parent);
        setModal(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setTitle("Demographics");
    }

    public void display() {
        getContentPane().setLayout(new BorderLayout(5, 5));
        getContentPane().setBackground(Color.white);
        initializeWidgets();
        pack();
        final java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width - FRAME_WIDTH) / 2, (screenSize.height - FRAME_HEIGHT) / 2, FRAME_WIDTH, FRAME_HEIGHT);
        setVisible(true);
        toFront();
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

    private void initializeWidgets() {
        add(Box.createVerticalStrut(10), BorderLayout.PAGE_START);
        add(Box.createHorizontalStrut(10), BorderLayout.LINE_START);
        add(getPlayerSummaryPanel(), BorderLayout.CENTER);
        add(Box.createHorizontalStrut(10), BorderLayout.LINE_END);
        add(getPageEndPanel(), BorderLayout.PAGE_END);
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
            pageEndPanel.add(getCloseButton());
            pageEndPanel.setBackground(Color.white);
        }
        return pageEndPanel;
    }

    private JButton getCloseButton() {
        if (closeButton == null) {
            closeButton = new JButton("Close");
            closeButton.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    dispose();
                }
            });
        }
        return closeButton;
    }
}
