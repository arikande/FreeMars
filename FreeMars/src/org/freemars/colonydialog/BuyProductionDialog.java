package org.freemars.colonydialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JSlider;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeListener;
import org.freemars.ui.image.ImagePanel;
import org.freemars.ui.util.FreeMarsDialog;

/**
 *
 * @author Deniz ARIKAN
 */
public class BuyProductionDialog extends FreeMarsDialog {

    private final int FRAME_WIDTH = 400;
    private final int FRAME_HEIGHT = 210;
    private JPanel centerPanel;
    private JPanel productionInfoPanel;
    private ImagePanel imagePanel;
    private JLabel productionInfoLabel;
    private JPanel buyInfoPanel;
    private JPanel buyValuesPanel;
    private JLabel productionPointLabel;
    private JLabel productionPointTextLabel;
    private JLabel creditLabel;
    private JLabel creditTextLabel;
    private JSlider productionPointSlider;
    private JPanel pageEndPanel;
    private JButton buyButton;
    private JButton cancelButton;

    public BuyProductionDialog(Frame parent) {
        super(parent);
        setModal(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());
        initializeWidgets();
        pack();
    }

    @Override
    protected JRootPane createRootPane() {
        ActionListener actionListener = new ActionListener() {

            public void actionPerformed(ActionEvent actionEvent) {
                getBuyButton().doClick();
            }
        };
        JRootPane modifiedRootPane = super.createRootPane();
        KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
        modifiedRootPane.registerKeyboardAction(actionListener, stroke, JComponent.WHEN_IN_FOCUSED_WINDOW);
        return modifiedRootPane;
    }

    public void display() {
        final java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width - FRAME_WIDTH) / 2, (screenSize.height - FRAME_HEIGHT) / 2, FRAME_WIDTH, FRAME_HEIGHT);
        setVisible(true);
        toFront();
    }

    public void setBuyButtonAction(Action action) {
        getBuyButton().setAction(action);
    }

    public void setCancelButtonAction(Action action) {
        getCancelButton().setAction(action);
        getCancelButton().setText("Cancel");
    }

    public void addBuyProductionSliderChangeListener(ChangeListener changeListener) {
        getProductionPointSlider().addChangeListener(changeListener);
    }

    public int getProductionPointSliderValue() {
        return getProductionPointSlider().getValue();
    }

    public void setProductionPointSliderValue(int value) {
        getProductionPointSlider().setValue(value);
    }

    public void setProductionInfoLabelText(String text) {
        getProductionInfoLabel().setText(text);
    }

    public void setProductionPointSliderMaximum(int maximum) {
        getProductionPointSlider().setMaximum(maximum);
    }

    public void setProductionPointSliderMajorTickSpacing(int majorTickSpacing) {
        getProductionPointSlider().setMajorTickSpacing(majorTickSpacing);
    }

    public void setCreditValueLabelText(String text) {
        getCreditLabel().setText(text);
    }

    public void setProductionPointValueLabelText(String text) {
        getProductionPointLabel().setText(text);
    }

    public void setImage(Image image) {
        getImagePanel().setImage(image);
    }

    private void initializeWidgets() {
        add(getCenterPanel(), BorderLayout.CENTER);
        add(getPageEndPanel(), BorderLayout.PAGE_END);
    }

    private JPanel getCenterPanel() {
        if (centerPanel == null) {
            centerPanel = new JPanel(new BorderLayout());
            centerPanel.add(getProductionInfoPanel(), BorderLayout.LINE_START);
            centerPanel.add(getBuyInfoPanel(), BorderLayout.CENTER);
        }
        return centerPanel;
    }

    private JPanel getProductionInfoPanel() {
        if (productionInfoPanel == null) {
            productionInfoPanel = new JPanel(new BorderLayout());
            productionInfoPanel.setPreferredSize(new Dimension(120, 0));
            productionInfoPanel.add(getImagePanel(), BorderLayout.CENTER);
            productionInfoPanel.add(getProductionInfoLabel(), BorderLayout.PAGE_END);
        }
        return productionInfoPanel;
    }

    private ImagePanel getImagePanel() {
        if (imagePanel == null) {
            imagePanel = new ImagePanel();
        }
        return imagePanel;
    }

    private JLabel getProductionInfoLabel() {
        if (productionInfoLabel == null) {
            productionInfoLabel = new JLabel("", SwingConstants.CENTER);
        }
        return productionInfoLabel;
    }

    private JPanel getBuyInfoPanel() {
        if (buyInfoPanel == null) {
            buyInfoPanel = new JPanel(new BorderLayout());
            buyInfoPanel.add(getBuyValuesPanel(), BorderLayout.CENTER);
            buyInfoPanel.add(getProductionPointSlider(), BorderLayout.PAGE_END);
        }
        return buyInfoPanel;
    }

    private JPanel getBuyValuesPanel() {
        if (buyValuesPanel == null) {
            buyValuesPanel = new JPanel(new GridLayout(0, 2));
            buyValuesPanel.add(getProductionPointLabel());
            buyValuesPanel.add(getProductionPointTextLabel());
            buyValuesPanel.add(getCreditLabel());
            buyValuesPanel.add(getCreditTextLabel());
        }
        return buyValuesPanel;
    }

    private JLabel getProductionPointLabel() {
        if (productionPointLabel == null) {
            productionPointLabel = new JLabel("", SwingConstants.RIGHT);
            productionPointLabel.setFont(productionPointLabel.getFont().deriveFont(18f));
        }
        return productionPointLabel;
    }

    private JLabel getProductionPointTextLabel() {
        if (productionPointTextLabel == null) {
            productionPointTextLabel = new JLabel(" production");
            productionPointTextLabel.setFont(productionPointLabel.getFont().deriveFont(18f));
        }
        return productionPointTextLabel;
    }

    private JLabel getCreditLabel() {
        if (creditLabel == null) {
            creditLabel = new JLabel("", SwingConstants.RIGHT);
            creditLabel.setFont(creditLabel.getFont().deriveFont(18f));
        }
        return creditLabel;
    }

    private JLabel getCreditTextLabel() {
        if (creditTextLabel == null) {
            creditTextLabel = new JLabel(" credits");
            creditTextLabel.setFont(creditLabel.getFont().deriveFont(18f));
        }
        return creditTextLabel;
    }

    private JSlider getProductionPointSlider() {
        if (productionPointSlider == null) {
            productionPointSlider = new JSlider();
            productionPointSlider.setMinimum(0);
            productionPointSlider.setPaintTicks(true);
            productionPointSlider.setPaintLabels(true);
        }
        return productionPointSlider;
    }

    private JPanel getPageEndPanel() {
        if (pageEndPanel == null) {
            pageEndPanel = new JPanel();
            pageEndPanel.add(getBuyButton());
            pageEndPanel.add(getCancelButton());
        }
        return pageEndPanel;
    }

    private JButton getBuyButton() {
        if (buyButton == null) {
            buyButton = new JButton();
        }
        return buyButton;
    }

    private JButton getCancelButton() {
        if (cancelButton == null) {
            cancelButton = new JButton();
        }
        return cancelButton;
    }
}
