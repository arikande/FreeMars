package org.freemars.diplomacy.gift;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeListener;
import org.freemars.ui.image.FreeMarsImageManager;
import org.freemars.ui.util.FreeMarsDialog;

/**
 *
 * @author Deniz ARIKAN
 */
public class OfferGiftDialog extends FreeMarsDialog {

    public static final String CREDIT_GIFT_DETAIL_PANEL_NAME = "CreditGift";
    public static final String RESOURCE_GIFT_DETAIL_PANEL_NAME = "ResourceGift";
    public static final String UNIT_GIFT_DETAIL_PANEL_NAME = "UnitGift";

    private final int FRAME_WIDTH = 700;
    private final int FRAME_HEIGHT = 360;
    private JPanel centerPanel;
    private JPanel footerPanel;
    private JPanel giftTypePanel;
    private JPanel giftDetailPanel;
    private JToggleButton creditGiftToggleButton;
    private JToggleButton resourceGiftToggleButton;
    private JToggleButton unitGiftToggleButton;
    private JButton sendGiftButton;
    private JButton cancelButton;
    private ButtonGroup giftTypeButtonGroup;
    private JPanel creditGiftDetailPanel;
    private JPanel resourceGiftDetailPanel;
    private CardLayout giftDetailPanelLayout;
    private JPanel creditGiftCenterPanel;
    private JSlider creditGiftAmountSlider;
    private JPanel creditGiftAmountPanel;
    private JLabel creditGiftAmountLabel;
    private JLabel currencyUnitAmountLabel;
    private JPanel resourceGiftPageStartPanel;
    private JPanel resourceGiftCenterPanel;
    private JPanel resourceGiftPageEndPanel;
    private JPanel resourceTypeSelectorPanel;
    private JComboBox<String> resourceGiftTransferColonySelectorComboBox;
    private JLabel resourceGiftTransferColonyLabel;
    private JPanel resourceGiftAmountPanel;
    private JSlider resourceGiftAmountSlider;
    private JLabel resourceGiftAmountLabel;
    private JPanel unitGiftDetailPanel;
    private JPanel giftableUnitsSelectionPanel;
    private ButtonGroup resourceTypeButtonGroup;
    private ButtonGroup giftableUnitsButtonGroup;
    private final List<JToggleButton> resourceTypeButtons;

    public OfferGiftDialog(Frame owner) {
        super(owner);
        setModal(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setTitle("Offer gift");
        resourceTypeButtons = new ArrayList<JToggleButton>();
        initializeWidgets();
    }

    public void display() {
        pack();
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width - FRAME_WIDTH) / 2, (screenSize.height - FRAME_HEIGHT) / 2, FRAME_WIDTH, FRAME_HEIGHT);
        setVisible(true);
        toFront();
    }

    public void displayGiftDetailPanel(String panelName) {
        getGiftDetailPanelLayout().show(getGiftDetailPanel(), panelName);
    }

    public void addCreditGiftToggleButtonActionListener(ActionListener actionListener) {
        getCreditGiftToggleButton().addActionListener(actionListener);
    }

    public void addResourceGiftToggleButtonActionListener(ActionListener actionListener) {
        getResourceGiftToggleButton().addActionListener(actionListener);
    }

    public void addUnitGiftToggleButtonActionListener(ActionListener actionListener) {
        getUnitGiftToggleButton().addActionListener(actionListener);
    }

    public void setSendGiftButtonEnabled(boolean enabled) {
        getSendGiftButton().setEnabled(enabled);
    }

    public void setSendGiftButtonAction(Action action) {
        getSendGiftButton().setAction(action);
    }

    public void setCancelButtonAction(Action action) {
        getCancelButton().setAction(action);
    }

    public int getCreditGiftAmountSliderValue() {
        return getCreditGiftAmountSlider().getValue();
    }

    public void setCreditGiftAmountSliderValue(int value) {
        getCreditGiftAmountSlider().setValue(value);
    }

    public void setCreditGiftAmountSliderMaximum(int maximum) {
        getCreditGiftAmountSlider().setMaximum(maximum);
    }

    public void setCreditGiftAmountSliderMajorTickSpacing(int majorTickSpacing) {
        getCreditGiftAmountSlider().setMajorTickSpacing(majorTickSpacing);
    }

    public void addCreditAmountSliderChangeListener(ChangeListener changeListener) {
        getCreditGiftAmountSlider().addChangeListener(changeListener);
    }

    public void setCreditGiftAmountLabelText(String text) {
        getCreditGiftAmountLabel().setText(text);
    }

    public void addResourceGiftTransferColonyItem(String colonyName) {
        getResourceGiftTransferColonySelectorComboBox().addItem(colonyName);
    }

    public void addResourceGiftTransferColonySelectorComboBoxItemListener(ItemListener itemListener) {
        getResourceGiftTransferColonySelectorComboBox().addItemListener(itemListener);
    }

    public void clearResourceGiftTypeSelection() {
        getResourceTypeButtonGroup().clearSelection();
    }

    public void clearGiftableUnitsSelection() {
        getGiftableUnitsButtonGroup().clearSelection();
    }

    public void addResourceGiftType(Icon icon, ActionListener actionListener, String actionCommand) {
        JToggleButton resourceTypeToggleButton = new JToggleButton(icon);
        resourceTypeToggleButton.setFont(resourceTypeToggleButton.getFont().deriveFont(11f));
        resourceTypeToggleButton.addActionListener(actionListener);
        resourceTypeToggleButton.setActionCommand(actionCommand);
        getResourceTypeButtonGroup().add(resourceTypeToggleButton);
        resourceTypeButtons.add(resourceTypeToggleButton);
        getResourceTypeSelectorPanel().add(resourceTypeToggleButton);
    }

    public void setResourceGiftTypeButtonText(int index, String text) {
        resourceTypeButtons.get(index).setText(text);
    }

    public void setResourceGiftTypeButtonEnabled(int index, boolean enabled) {
        resourceTypeButtons.get(index).setEnabled(enabled);
    }

    public void setResourceGiftTypeButtonsEnabled(boolean enabled) {
        for (JToggleButton resourceTypeButton : resourceTypeButtons) {
            resourceTypeButton.setEnabled(enabled);
        }
    }

    public void addResourceGiftAmountSliderChangeListener(ChangeListener changeListener) {
        getResourceGiftAmountSlider().addChangeListener(changeListener);
    }

    public void setResourceGiftAmountSliderEnabled(boolean enabled) {
        getResourceGiftAmountSlider().setEnabled(enabled);
    }

    public void setResourceGiftAmountSliderMaximum(int maximum) {
        getResourceGiftAmountSlider().setMaximum(maximum);
    }

    public int getResourceGiftAmountSliderValue() {
        return getResourceGiftAmountSlider().getValue();
    }

    public void setResourceGiftAmountSliderValue(int value) {
        getResourceGiftAmountSlider().setValue(value);
    }

    public void setResourceGiftAmountSliderTickSpacing(int tickSpacing) {
        getResourceGiftAmountSlider().setLabelTable(null);
        getResourceGiftAmountSlider().setMajorTickSpacing(tickSpacing);
        getResourceGiftAmountSlider().setMinorTickSpacing(tickSpacing);
        getResourceGiftAmountSlider().revalidate();
        getResourceGiftAmountSlider().repaint();
    }

    public void setResourceGiftAmountLabelText(String text) {
        getResourceGiftAmountLabel().setText(text);
    }

    public void addGiftableUnitToggleButton(String text, Icon icon, ActionListener actionListener, String actionCommand) {
        JToggleButton giftableUnitToggleButton = new JToggleButton(text, icon);
        giftableUnitToggleButton.setVerticalTextPosition(AbstractButton.BOTTOM);
        giftableUnitToggleButton.setHorizontalTextPosition(AbstractButton.CENTER);
        giftableUnitToggleButton.setFont(giftableUnitToggleButton.getFont().deriveFont(11f));
        giftableUnitToggleButton.addActionListener(actionListener);
        giftableUnitToggleButton.setActionCommand(actionCommand);
        getGiftableUnitsButtonGroup().add(giftableUnitToggleButton);
        getGiftableUnitsSelectionPanel().add(giftableUnitToggleButton);
    }

    private void initializeWidgets() {
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(getCenterPanel(), BorderLayout.CENTER);
        getContentPane().add(getFooterPanel(), BorderLayout.PAGE_END);
        getGiftTypeButtonGroup().add(getCreditGiftToggleButton());
        getGiftTypeButtonGroup().add(getResourceGiftToggleButton());
        getGiftTypeButtonGroup().add(getUnitGiftToggleButton());
        getCreditGiftToggleButton().setSelected(true);
    }

    private JPanel getCenterPanel() {
        if (centerPanel == null) {
            centerPanel = new JPanel(new BorderLayout());
            centerPanel.add(getGiftTypePanel(), BorderLayout.PAGE_START);
            centerPanel.add(getGiftDetailPanel(), BorderLayout.CENTER);
        }
        return centerPanel;
    }

    private JPanel getFooterPanel() {
        if (footerPanel == null) {
            footerPanel = new JPanel();
            footerPanel.add(getSendGiftButton());
            footerPanel.add(getCancelButton());
        }
        return footerPanel;
    }

    private JPanel getGiftTypePanel() {
        if (giftTypePanel == null) {
            giftTypePanel = new JPanel(new GridLayout(1, 0));
            giftTypePanel.add(getCreditGiftToggleButton());
            giftTypePanel.add(getResourceGiftToggleButton());
            giftTypePanel.add(getUnitGiftToggleButton());
        }
        return giftTypePanel;
    }

    private ButtonGroup getGiftTypeButtonGroup() {
        if (giftTypeButtonGroup == null) {
            giftTypeButtonGroup = new ButtonGroup();
        }
        return giftTypeButtonGroup;
    }

    private JToggleButton getCreditGiftToggleButton() {
        if (creditGiftToggleButton == null) {
            creditGiftToggleButton = new JToggleButton("Credits");
            Image creditGiftImage = FreeMarsImageManager.getImage("EARTH_CREDITS");
            creditGiftImage = FreeMarsImageManager.createResizedCopy(creditGiftImage, -1, 48, false, centerPanel);
            creditGiftToggleButton.setIcon(new ImageIcon(creditGiftImage));
            creditGiftToggleButton.setFocusPainted(false);
        }
        return creditGiftToggleButton;
    }

    private JToggleButton getResourceGiftToggleButton() {
        if (resourceGiftToggleButton == null) {
            resourceGiftToggleButton = new JToggleButton("Resources");
            Image resourceGiftImage = FreeMarsImageManager.getImage("COLONY_MANAGER_RESOURCES");
            resourceGiftImage = FreeMarsImageManager.createResizedCopy(resourceGiftImage, -1, 48, false, centerPanel);
            resourceGiftToggleButton.setIcon(new ImageIcon(resourceGiftImage));
            resourceGiftToggleButton.setFocusPainted(false);
        }
        return resourceGiftToggleButton;
    }

    private JToggleButton getUnitGiftToggleButton() {
        if (unitGiftToggleButton == null) {
            unitGiftToggleButton = new JToggleButton("Unit");
            Image unitGiftImage = FreeMarsImageManager.getImage("UNIT_3_SW");
            unitGiftImage = FreeMarsImageManager.createResizedCopy(unitGiftImage, -1, 48, false, centerPanel);
            unitGiftToggleButton.setIcon(new ImageIcon(unitGiftImage));
            unitGiftToggleButton.setFocusPainted(false);
        }
        return unitGiftToggleButton;
    }

    private JPanel getGiftDetailPanel() {
        if (giftDetailPanel == null) {
            giftDetailPanel = new JPanel(getGiftDetailPanelLayout());
            giftDetailPanel.add(getCreditGiftDetailPanel(), CREDIT_GIFT_DETAIL_PANEL_NAME);
            giftDetailPanel.add(getResourceGiftDetailPanel(), RESOURCE_GIFT_DETAIL_PANEL_NAME);
            giftDetailPanel.add(getUnitGiftDetailPanel(), UNIT_GIFT_DETAIL_PANEL_NAME);
        }
        return giftDetailPanel;
    }

    private CardLayout getGiftDetailPanelLayout() {
        if (giftDetailPanelLayout == null) {
            giftDetailPanelLayout = new CardLayout();
        }
        return giftDetailPanelLayout;
    }

    private JButton getSendGiftButton() {
        if (sendGiftButton == null) {
            sendGiftButton = new JButton("Send");
        }
        return sendGiftButton;
    }

    private JButton getCancelButton() {
        if (cancelButton == null) {
            cancelButton = new JButton("Cancel");
        }
        return cancelButton;
    }

    private JPanel getCreditGiftDetailPanel() {
        if (creditGiftDetailPanel == null) {
            creditGiftDetailPanel = new JPanel(new BorderLayout());
            creditGiftDetailPanel.add(Box.createVerticalStrut(30), BorderLayout.PAGE_START);
            creditGiftDetailPanel.add(Box.createHorizontalStrut(100), BorderLayout.LINE_START);
            creditGiftDetailPanel.add(getCreditGiftCenterPanel(), BorderLayout.CENTER);
            creditGiftDetailPanel.add(Box.createHorizontalStrut(100), BorderLayout.LINE_END);
            creditGiftDetailPanel.add(Box.createVerticalStrut(30), BorderLayout.PAGE_END);
        }
        return creditGiftDetailPanel;
    }

    private JPanel getCreditGiftCenterPanel() {
        if (creditGiftCenterPanel == null) {
            creditGiftCenterPanel = new JPanel(new GridLayout(0, 1));
            creditGiftCenterPanel.add(getCreditGiftAmountPanel());
            creditGiftCenterPanel.add(getCreditGiftAmountSlider());
        }
        return creditGiftCenterPanel;
    }

    private JPanel getCreditGiftAmountPanel() {
        if (creditGiftAmountPanel == null) {
            creditGiftAmountPanel = new JPanel(new GridLayout(1, 0));
            creditGiftAmountPanel.add(getCreditGiftAmountLabel());
            creditGiftAmountPanel.add(getCurrencyUnitAmountLabel());
        }
        return creditGiftAmountPanel;
    }

    private JLabel getCreditGiftAmountLabel() {
        if (creditGiftAmountLabel == null) {
            creditGiftAmountLabel = new JLabel("0", SwingConstants.RIGHT);
            creditGiftAmountLabel.setFont(creditGiftAmountLabel.getFont().deriveFont(22f));
        }
        return creditGiftAmountLabel;
    }

    private JLabel getCurrencyUnitAmountLabel() {
        if (currencyUnitAmountLabel == null) {
            currencyUnitAmountLabel = new JLabel(" credits");
            currencyUnitAmountLabel.setFont(currencyUnitAmountLabel.getFont().deriveFont(22f));
        }
        return currencyUnitAmountLabel;
    }

    private JSlider getCreditGiftAmountSlider() {
        if (creditGiftAmountSlider == null) {
            creditGiftAmountSlider = new JSlider();
            creditGiftAmountSlider.setMinimum(0);
            creditGiftAmountSlider.setPaintTicks(true);
            creditGiftAmountSlider.setPaintLabels(true);
        }
        return creditGiftAmountSlider;
    }

    private JPanel getResourceGiftDetailPanel() {
        if (resourceGiftDetailPanel == null) {
            resourceGiftDetailPanel = new JPanel(new BorderLayout());
            resourceGiftDetailPanel.add(getResourceGiftPageStartPanel(), BorderLayout.PAGE_START);
            resourceGiftDetailPanel.add(getResourceGiftCenterPanel(), BorderLayout.CENTER);
            resourceGiftDetailPanel.add(getResourceGiftPageEndPanel(), BorderLayout.PAGE_END);
        }
        return resourceGiftDetailPanel;
    }

    private JPanel getResourceGiftPageStartPanel() {
        if (resourceGiftPageStartPanel == null) {
            resourceGiftPageStartPanel = new JPanel();
            resourceGiftPageStartPanel.add(getResourceGiftTransferColonyLabel());
            resourceGiftPageStartPanel.add(getResourceGiftTransferColonySelectorComboBox());
        }
        return resourceGiftPageStartPanel;
    }

    private JPanel getResourceGiftCenterPanel() {
        if (resourceGiftCenterPanel == null) {
            resourceGiftCenterPanel = new JPanel(new BorderLayout());
            resourceGiftCenterPanel.add(getResourceTypeSelectorPanel(), BorderLayout.CENTER);
        }
        return resourceGiftCenterPanel;
    }

    private JPanel getResourceGiftPageEndPanel() {
        if (resourceGiftPageEndPanel == null) {
            resourceGiftPageEndPanel = new JPanel(new BorderLayout());
            resourceGiftPageEndPanel.setPreferredSize(new Dimension(0, 50));
            resourceGiftPageEndPanel.add(Box.createHorizontalStrut(100), BorderLayout.LINE_START);
            resourceGiftPageEndPanel.add(getResourceGiftAmountPanel(), BorderLayout.CENTER);
            resourceGiftPageEndPanel.add(Box.createHorizontalStrut(100), BorderLayout.LINE_END);
        }
        return resourceGiftPageEndPanel;
    }

    private JPanel getResourceTypeSelectorPanel() {
        if (resourceTypeSelectorPanel == null) {
            resourceTypeSelectorPanel = new JPanel(new GridLayout(0, 4));
        }
        return resourceTypeSelectorPanel;
    }

    private ButtonGroup getResourceTypeButtonGroup() {
        if (resourceTypeButtonGroup == null) {
            resourceTypeButtonGroup = new ButtonGroup();
        }
        return resourceTypeButtonGroup;
    }

    private ButtonGroup getGiftableUnitsButtonGroup() {
        if (giftableUnitsButtonGroup == null) {
            giftableUnitsButtonGroup = new ButtonGroup();
        }
        return giftableUnitsButtonGroup;
    }

    private JLabel getResourceGiftTransferColonyLabel() {
        if (resourceGiftTransferColonyLabel == null) {
            resourceGiftTransferColonyLabel = new JLabel("Send from :");
        }
        return resourceGiftTransferColonyLabel;
    }

    private JComboBox<String> getResourceGiftTransferColonySelectorComboBox() {
        if (resourceGiftTransferColonySelectorComboBox == null) {
            resourceGiftTransferColonySelectorComboBox = new JComboBox<String>();
        }
        return resourceGiftTransferColonySelectorComboBox;
    }

    private JPanel getResourceGiftAmountPanel() {
        if (resourceGiftAmountPanel == null) {
            resourceGiftAmountPanel = new JPanel(new BorderLayout());
            resourceGiftAmountPanel.add(getResourceGiftAmountSlider(), BorderLayout.CENTER);
            resourceGiftAmountPanel.add(getResourceGiftAmountLabel(), BorderLayout.LINE_END);
        }
        return resourceGiftAmountPanel;
    }

    private JLabel getResourceGiftAmountLabel() {
        if (resourceGiftAmountLabel == null) {
            resourceGiftAmountLabel = new JLabel();
            resourceGiftAmountLabel.setFont(resourceGiftAmountLabel.getFont().deriveFont(18f));
        }
        return resourceGiftAmountLabel;
    }

    private JSlider getResourceGiftAmountSlider() {
        if (resourceGiftAmountSlider == null) {
            resourceGiftAmountSlider = new JSlider();
            resourceGiftAmountSlider.setMinimum(0);
            resourceGiftAmountSlider.setValue(0);
            resourceGiftAmountSlider.setPaintTicks(true);
            resourceGiftAmountSlider.setPaintLabels(true);
        }
        return resourceGiftAmountSlider;
    }

    public JPanel getUnitGiftDetailPanel() {
        if (unitGiftDetailPanel == null) {
            unitGiftDetailPanel = new JPanel(new BorderLayout());
            unitGiftDetailPanel.add(Box.createVerticalStrut(10), BorderLayout.PAGE_START);
            unitGiftDetailPanel.add(Box.createHorizontalStrut(10), BorderLayout.LINE_START);
            unitGiftDetailPanel.add(getGiftableUnitsSelectionPanel(), BorderLayout.CENTER);
            unitGiftDetailPanel.add(Box.createHorizontalStrut(10), BorderLayout.LINE_END);
            unitGiftDetailPanel.add(Box.createVerticalStrut(10), BorderLayout.PAGE_END);
        }
        return unitGiftDetailPanel;
    }

    public JPanel getGiftableUnitsSelectionPanel() {
        if (giftableUnitsSelectionPanel == null) {
            giftableUnitsSelectionPanel = new JPanel(new GridLayout(0, 4, 10, 10));
        }
        return giftableUnitsSelectionPanel;
    }

}
