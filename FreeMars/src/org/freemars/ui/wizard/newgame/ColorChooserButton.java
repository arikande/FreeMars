package org.freemars.ui.wizard.newgame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPopupMenu;

/**
 *
 * @author Deniz ARIKAN
 */
public class ColorChooserButton extends JButton implements ActionListener {

    private Color selectedColor;
    private Dimension colorChooserSize;
    private Dimension colorChooserDimension;
    private final Color[] colorOptions;
    private JPopupMenu jPopupMenu;
    private ActionListener actionListenerFromParent;

    public ColorChooserButton() {
        setSelectedColor(Color.BLUE);
        colorChooserSize = new Dimension(144, 120);
        colorChooserDimension = new Dimension(6, 5);
        colorOptions = new Color[]{Color.WHITE, new Color(50, 75, 180), new Color(50, 175, 60), new Color(0, 145, 215), new Color(250, 15, 155),
                    Color.YELLOW, Color.CYAN, Color.MAGENTA, Color.ORANGE, Color.PINK,
                    new Color(255, 200, 200), new Color(255, 150, 150), new Color(255, 100, 100), Color.RED, new Color(140, 0, 0),
                    Color.GREEN, new Color(0, 200, 0), new Color(0, 170, 0), new Color(0, 140, 0), new Color(0, 100, 0),
                    new Color(200, 200, 255), new Color(150, 150, 255), new Color(100, 100, 255), Color.BLUE, new Color(0, 0, 140),
                    Color.LIGHT_GRAY, new Color(150, 150, 150), Color.GRAY, Color.DARK_GRAY, Color.BLACK};
        addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        jPopupMenu = new JPopupMenu();
        jPopupMenu.setLayout(new GridLayout((int) getColorChooserDimension().getWidth(), (int) getColorChooserDimension().getHeight()));
        for (int i = 0; i < colorOptions.length; i++) {
            Color color = colorOptions[i];
            JButton jButton = new JButton();
            jButton.setBackground(color);
            jButton.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    setSelectedColor(((JButton) e.getSource()).getBackground());
                    closePopup();
                    actionListenerFromParent.actionPerformed(null);
                }
            });
            jPopupMenu.add(jButton);
        }
        jPopupMenu.setPreferredSize(getColorChooserSize());
        jPopupMenu.show(this, this.getWidth(), 0);
    }

    public void setActionListenerFromParent(ActionListener actionListenerFromParent) {
        this.actionListenerFromParent = actionListenerFromParent;
    }

    private Dimension getColorChooserSize() {
        return colorChooserSize;
    }

    private void closePopup() {
        if ((jPopupMenu != null) && (jPopupMenu.isVisible())) {
            jPopupMenu.setVisible(false);
        }
    }

    public void setColorChooserSize(Dimension colorChooserSize) {
        this.colorChooserSize = colorChooserSize;
    }

    public Color getSelectedColor() {
        return selectedColor;
    }

    public void setSelectedColor(Color selectedColor) {
        this.selectedColor = selectedColor;
        setBackground(selectedColor);
    }

    private Dimension getColorChooserDimension() {
        return colorChooserDimension;
    }

    public void setColorChooserDimension(Dimension colorChooserDimension) {
        this.colorChooserDimension = colorChooserDimension;
    }
}
