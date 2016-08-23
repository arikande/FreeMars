package org.freemars.colonydialog;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import javax.swing.Action;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import org.freemars.ui.image.FreeMarsImageManager;
import org.freemars.ui.image.ImagePanel;
import org.freerealm.modifier.Modifier;
import org.freerealm.settlement.SettlementBuildable;
import org.freerealm.settlement.SettlementBuildableCostCalculator;
import org.freerealm.unit.FreeRealmUnitType;

/**
 *
 * @author Deniz ARIKAN
 */
public class ColonyProductionManagementPanel extends JPanel {

    private ColonyDialogModel model;
    private JPanel currentProductionPanel;
    private ImagePanel currentProductionImagePanel;
    private JPanel currentProductionInfoPanel;
    private JLabel currentProductionLabel;
    private JPanel currentProductionFooterPanel;
    private JProgressBar currentProductionProgressBar;
    private JPanel currentProductionOptionsPanel;
    private JButton buyProductionButton;
    private JCheckBox contiuousProductionCheckBox;
    private JPanel queuePanel;
    private JScrollPane queueScrollPane;
    private JList queueList;
    private DefaultListModel currentQueueListModel;
    private JButton displayQueueManagementDialogButton;

    public ColonyProductionManagementPanel() {
        super(new BorderLayout(10, 0));
        add(getCurrentProductionPanel(), BorderLayout.LINE_START);
        add(getQueuePanel(), BorderLayout.CENTER);
    }

    public void update() {
        getContiuousProductionCheckBox().setSelected(false);
        getContiuousProductionCheckBox().setEnabled(false);
        getBuyProductionButton().setEnabled(false);
        SettlementBuildable currentProduction = model.getColony().getCurrentProduction();
        if (currentProduction != null) {
            int currentProductionCost = new SettlementBuildableCostCalculator(currentProduction, new Modifier[]{model.getColony().getPlayer()}).getCost();
            Image currentProductionImage = FreeMarsImageManager.getImage(currentProduction);
            BufferedImage resizedImprovementImage = FreeMarsImageManager.createResizedCopy(currentProductionImage, -1, 70, false, this);
            getCurrentProductionImagePanel().setImage(resizedImprovementImage);
            getCurrentProductionLabel().setText(currentProduction.toString());
            getCurrentProductionProgressBar().setMaximum(currentProductionCost);
            getCurrentProductionProgressBar().setValue(model.getColony().getProductionPoints());

            getCurrentProductionProgressBar().setString(String.valueOf(model.getColony().getProductionPoints()) + " / " + String.valueOf(currentProductionCost));
            if (currentProduction instanceof FreeRealmUnitType) {
                getContiuousProductionCheckBox().setSelected(true);
                getContiuousProductionCheckBox().setEnabled(true);
            }
            int productionCost = new SettlementBuildableCostCalculator(model.getColony().getCurrentProduction(), new Modifier[]{model.getColony().getPlayer()}).getCost();
            int remainingProductionCost = productionCost - model.getColony().getProductionPoints();
            if (remainingProductionCost > 0 && model.getColony().getPlayer().getWealth() > 0) {
                getBuyProductionButton().setEnabled(true);
            }
        } else {
            getCurrentProductionLabel().setText("Nothing");
            getCurrentProductionImagePanel().setImage(null);
            getCurrentProductionProgressBar().setMaximum(0);
            getCurrentProductionProgressBar().setValue(0);
            getCurrentProductionProgressBar().setString("");
        }
        getCurrentProductionImagePanel().repaint();
        getContiuousProductionCheckBox().setSelected(model.getColony().isContiuousProduction());
        updateCurrentQueueList();
    }

    public void setModel(ColonyDialogModel model) {
        this.model = model;
    }

    public void setBuyProductionAction(Action action) {
        getBuyProductionButton().setAction(action);
        if (model.getColony().getCurrentProduction() == null || model.getColony().getProductionPoints() >= model.getColony().getCurrentProduction().getProductionCost()) {
            getBuyProductionButton().setEnabled(false);
        } else {
            getBuyProductionButton().setEnabled(true);
        }
    }

    public void addColonyContinuousProductionActionListener(ActionListener actionListener) {
        getContiuousProductionCheckBox().addActionListener(actionListener);
    }

    public void setDisplayQueueManagementDialogButtonAction(Action action) {
        getDisplayQueueManagementDialogButton().setAction(action);
    }

    private void updateCurrentQueueList() {
        getCurrentQueueListModel().removeAllElements();
        Iterator<SettlementBuildable> cityImprovementTypeIterator = model.getColony().getProductionQueueIterator();
        while (cityImprovementTypeIterator.hasNext()) {
            SettlementBuildable settlementBuildable = cityImprovementTypeIterator.next();
            getCurrentQueueListModel().addElement(settlementBuildable);
        }
    }

    private JPanel getCurrentProductionPanel() {
        if (currentProductionPanel == null) {
            currentProductionPanel = new JPanel(new BorderLayout(0, 10));
            currentProductionPanel.add(getCurrentProductionInfoPanel(), BorderLayout.CENTER);
            currentProductionPanel.add(getCurrentProductionFooterPanel(), BorderLayout.PAGE_END);
        }
        return currentProductionPanel;
    }

    private ImagePanel getCurrentProductionImagePanel() {
        if (currentProductionImagePanel == null) {
            currentProductionImagePanel = new ImagePanel();
        }
        return currentProductionImagePanel;
    }

    private JPanel getCurrentProductionInfoPanel() {
        if (currentProductionInfoPanel == null) {
            currentProductionInfoPanel = new JPanel(new BorderLayout());
            currentProductionInfoPanel.add(getCurrentProductionImagePanel(), BorderLayout.CENTER);
            currentProductionInfoPanel.add(getCurrentProductionLabel(), BorderLayout.PAGE_END);
        }
        return currentProductionInfoPanel;
    }

    private JLabel getCurrentProductionLabel() {
        if (currentProductionLabel == null) {
            currentProductionLabel = new JLabel();
            currentProductionLabel.setHorizontalTextPosition(SwingConstants.CENTER);
            currentProductionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        }
        return currentProductionLabel;
    }

    private JPanel getCurrentProductionFooterPanel() {
        if (currentProductionFooterPanel == null) {
            currentProductionFooterPanel = new JPanel(new BorderLayout(0, 10));
            currentProductionFooterPanel.add(getCurrentProductionProgressBar(), BorderLayout.PAGE_START);
            currentProductionFooterPanel.add(getCurrentProductionOptionsPanel(), BorderLayout.CENTER);

        }
        return currentProductionFooterPanel;
    }

    private JProgressBar getCurrentProductionProgressBar() {
        if (currentProductionProgressBar == null) {
            currentProductionProgressBar = new JProgressBar();
            currentProductionProgressBar.setStringPainted(true);
        }
        return currentProductionProgressBar;
    }

    private JPanel getCurrentProductionOptionsPanel() {
        if (currentProductionOptionsPanel == null) {
            currentProductionOptionsPanel = new JPanel();
            currentProductionOptionsPanel.add(getBuyProductionButton());
            currentProductionOptionsPanel.add(getContiuousProductionCheckBox());

        }
        return currentProductionOptionsPanel;
    }

    private JButton getBuyProductionButton() {
        if (buyProductionButton == null) {
            buyProductionButton = new JButton();
        }
        return buyProductionButton;
    }

    private JCheckBox getContiuousProductionCheckBox() {
        if (contiuousProductionCheckBox == null) {
            contiuousProductionCheckBox = new JCheckBox("Continue");
        }
        return contiuousProductionCheckBox;
    }

    private JPanel getQueuePanel() {
        if (queuePanel == null) {
            queuePanel = new JPanel(new BorderLayout(0, 10));
            queuePanel.add(getQueueScrollPane(), BorderLayout.CENTER);
            queuePanel.add(getDisplayQueueManagementDialogButton(), BorderLayout.PAGE_END);
        }
        return queuePanel;
    }

    private JScrollPane getQueueScrollPane() {
        if (queueScrollPane == null) {
            queueScrollPane = new JScrollPane(getQueueList());
        }
        return queueScrollPane;
    }

    private JList getQueueList() {
        if (queueList == null) {
            queueList = new JList(getCurrentQueueListModel());
        }
        return queueList;
    }

    private DefaultListModel getCurrentQueueListModel() {
        if (currentQueueListModel == null) {
            currentQueueListModel = new DefaultListModel();
        }
        return currentQueueListModel;
    }

    private JButton getDisplayQueueManagementDialogButton() {
        if (displayQueueManagementDialogButton == null) {
            displayQueueManagementDialogButton = new JButton();
        }
        return displayQueueManagementDialogButton;
    }
}
