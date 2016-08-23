package org.freemars.ui.wizard.newgame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import org.freemars.ui.image.FreeMarsImageManager;
import org.freemars.ui.image.ImagePanel;
import org.freerealm.nation.Nation;

/**
 *
 * @author Deniz ARIKAN
 */
public class NationCheckBoxPanel extends JPanel {

    private JCheckBox jCheckBox;
    private JPanel lineEndPanel;
    private JPanel colorChooserPanel;
    private ColorChooserButton color1ChooserButton;
    private ColorChooserButton color2ChooserButton;
    private ImagePanel nationFlagPanel;
    private Nation nation;

    public NationCheckBoxPanel(Nation nation) {
        super(new BorderLayout(10, 10));
        setNation(nation);
        add(getCheckBox(), BorderLayout.CENTER);
        add(getLineEndPanel(), BorderLayout.LINE_END);
        getCheckBox().setText(getNation().getName());
        getCheckBox().setSelected(false);
        getNationFlagPanel().setImage(FreeMarsImageManager.getImage(nation, false, -1, 16));
        getColor1ChooserButton().setEnabled(false);
        getColor1ChooserButton().setSelectedColor(nation.getDefaultColor1());
        getColor2ChooserButton().setEnabled(false);
        getColor2ChooserButton().setSelectedColor(nation.getDefaultColor2());
    }

    public void setCheckBoxSelected(boolean selected) {
        getCheckBox().setSelected(selected);
    }

    public void setToolTipRecursively(JComponent c, String text) {
        c.setToolTipText(text);
        for (Component cc : c.getComponents()) {
            if (cc instanceof JComponent) {
                setToolTipRecursively((JComponent) cc, text);
            }
        }
    }

    private JCheckBox getCheckBox() {
        if (jCheckBox == null) {
            jCheckBox = new JCheckBox();
            jCheckBox.addItemListener(new ItemListener() {

                public void itemStateChanged(ItemEvent e) {
                    if (getCheckBox().isSelected()) {
                        getColor1ChooserButton().setEnabled(true);
                        getColor2ChooserButton().setEnabled(true);
                    } else {
                        getColor1ChooserButton().setEnabled(false);
                        getColor2ChooserButton().setEnabled(false);
                    }
                }
            });
        }
        return jCheckBox;
    }

    private JPanel getColorChooserPanel() {
        if (colorChooserPanel == null) {
            colorChooserPanel = new JPanel(new GridLayout(1, 2, 1, 0));
            colorChooserPanel.add(getColor1ChooserButton());
            colorChooserPanel.add(getColor2ChooserButton());
        }
        return colorChooserPanel;
    }

    private ColorChooserButton getColor1ChooserButton() {
        if (color1ChooserButton == null) {
            color1ChooserButton = new ColorChooserButton();
            color1ChooserButton.setPreferredSize(new Dimension(15, 15));
        }
        return color1ChooserButton;
    }

    private ColorChooserButton getColor2ChooserButton() {
        if (color2ChooserButton == null) {
            color2ChooserButton = new ColorChooserButton();
            color2ChooserButton.setPreferredSize(new Dimension(15, 15));
        }
        return color2ChooserButton;
    }

    public Nation getNation() {
        return nation;
    }

    private void setNation(Nation nation) {
        this.nation = nation;
    }

    public boolean isSelected() {
        return getCheckBox().isSelected();
    }

    public Color getSelectedColor1() {
        return getColor1ChooserButton().getSelectedColor();
    }

    public Color getSelectedColor2() {
        return getColor2ChooserButton().getSelectedColor();
    }

    public void addItemListener(ItemListener itemListener) {
        getCheckBox().addItemListener(itemListener);
    }

    public void setActionListenerFromParent(ActionListener actionListenerFromParent) {
        getColor1ChooserButton().setActionListenerFromParent(actionListenerFromParent);
        getColor2ChooserButton().setActionListenerFromParent(actionListenerFromParent);
    }

    private JPanel getLineEndPanel() {
        if (lineEndPanel == null) {
            lineEndPanel = new JPanel(new GridLayout(0, 2, 10, 0));
            lineEndPanel.add(getNationFlagPanel());
            lineEndPanel.add(getColorChooserPanel());
        }
        return lineEndPanel;
    }

    private ImagePanel getNationFlagPanel() {
        if (nationFlagPanel == null) {
            nationFlagPanel = new ImagePanel();
            nationFlagPanel.setImageVerticalAlignment(ImagePanel.CENTER_ALIGNMENT);
        }
        return nationFlagPanel;
    }
}
