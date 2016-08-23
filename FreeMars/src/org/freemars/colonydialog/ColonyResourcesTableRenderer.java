package org.freemars.colonydialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;
import org.freemars.ui.image.FreeMarsImageManager;

/**
 *
 * @author Deniz ARIKAN
 */
public class ColonyResourcesTableRenderer implements TableCellRenderer {

    private static final Color SELECTED_ROW_COLOR = new Color(212, 123, 123);
    private static final Color UNSELECTED_ROW_COLOR = Color.white;
    private Font font;
    private Icon greenUpArrowIcon;
    private Icon redDownArrowIcon;

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        font = table.getFont().deriveFont(14f);
        ArrayList rowValues = (ArrayList) value;
        String resourceName = (String) rowValues.get(1);
        int quantity = Integer.parseInt(rowValues.get(2).toString());
        int production = Integer.parseInt(rowValues.get(3).toString());
        int consumption = Integer.parseInt(rowValues.get(4).toString());
        int capacity = Integer.parseInt(rowValues.get(5).toString());
        Component rendererComponent = null;
        if (column == 0) {
            Icon icon = (Icon) rowValues.get(0);
            rendererComponent = getResourceIconAndNameColumnRenderer(icon, resourceName);
        } else if (column == 1) {
            rendererComponent = getResourceProductionColumnRenderer(production, consumption);
        } else if (column == 2) {
            rendererComponent = getResourceQuantityColumnRenderer(quantity, capacity);
        }
        if (rendererComponent != null) {
            if (isSelected) {
                rendererComponent.setBackground(SELECTED_ROW_COLOR);
            } else {
                if (row < 3 && quantity + production < consumption) {
                    rendererComponent.setBackground(new Color(255, 129, 129));
                } else if (row < 3 && production < consumption) {
                    rendererComponent.setBackground(new Color(255, 250, 205));
                } else if (row < 3 && production >= consumption) {
                    rendererComponent.setBackground(new Color(197, 227, 0xBF));
                } else {
                    rendererComponent.setBackground(UNSELECTED_ROW_COLOR);
                }
            }
        }
        return rendererComponent;
    }

    private Component getResourceIconAndNameColumnRenderer(Icon icon, String resourceName) {
        JPanel rendererPanel = new JPanel(new BorderLayout(5, 0));
        JLabel resourceIconAndNameLabel = new JLabel();
        resourceIconAndNameLabel.setFont(font);
        resourceIconAndNameLabel.setIcon(icon);
        resourceIconAndNameLabel.setText(" " + resourceName);
        rendererPanel.add(Box.createHorizontalStrut(5), BorderLayout.LINE_START);
        rendererPanel.add(resourceIconAndNameLabel, BorderLayout.CENTER);
        return rendererPanel;
    }

    private Component getResourceProductionColumnRenderer(int production, int consumption) {
        JPanel rendererPanel = new JPanel(new BorderLayout());
        JLabel rendererLabel = new JLabel();
        rendererLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        rendererLabel.setFont(font);
        String labelText = "";
        Icon icon = null;
        if (consumption == 0) {
            labelText = String.valueOf(production);
            if (production > 0) {
                rendererLabel.setIcon(getGreenUpArrowIcon());
                rendererLabel.setForeground(new Color(40, 150, 40));
                rendererLabel.setFont(font.deriveFont(Font.BOLD));
                labelText = "+" + labelText;
            }
        } else {
            if (production > 0) {
                rendererLabel.setFont(font.deriveFont(Font.BOLD));
                labelText = labelText + "+" + String.valueOf(production) + "(";
            }
            labelText = labelText + "-" + consumption;
            if (production > 0) {
                labelText = labelText + ")";
            }
            if (production > consumption) {
                rendererLabel.setIcon(getGreenUpArrowIcon());
            }
            if (production >= consumption) {
                rendererLabel.setForeground(new Color(40, 150, 40));
            } else {
                rendererLabel.setIcon(getRedDownArrowIcon());
                rendererLabel.setFont(font.deriveFont(Font.BOLD));
                rendererLabel.setForeground(Color.red);
            }
        }
        rendererLabel.setText(labelText);
        rendererPanel.add(rendererLabel, BorderLayout.CENTER);
        rendererPanel.add(Box.createHorizontalStrut(5), BorderLayout.LINE_END);
        return rendererPanel;
    }

    public Component getResourceQuantityColumnRenderer(int quantity, int capacity) {
        JProgressBar progressBar = new JProgressBar();
        JPanel rendererPanel = new JPanel(new BorderLayout(10, 10));
        progressBar.setMaximum(capacity);
        progressBar.setValue(quantity);
        progressBar.setStringPainted(true);
        progressBar.setString(String.valueOf(quantity));
        JPanel progressBarPanel = new JPanel(new BorderLayout(5, 5));
        progressBarPanel.add(new JLabel(), BorderLayout.LINE_START);
        progressBarPanel.add(progressBar, BorderLayout.CENTER);
        progressBarPanel.add(new JLabel(), BorderLayout.LINE_END);
        progressBarPanel.setOpaque(false);
        rendererPanel.add(Box.createHorizontalStrut(5), BorderLayout.PAGE_START);
        rendererPanel.add(progressBarPanel, BorderLayout.CENTER);
        rendererPanel.add(Box.createHorizontalStrut(5), BorderLayout.PAGE_END);
        return rendererPanel;
    }

    private Icon getGreenUpArrowIcon() {
        if (greenUpArrowIcon == null) {
            Image image = FreeMarsImageManager.getImage("GREEN_UP_ARROW", 12, 12);
            greenUpArrowIcon = new ImageIcon(image);
        }
        return greenUpArrowIcon;
    }

    private Icon getRedDownArrowIcon() {
        if (redDownArrowIcon == null) {
            Image image = FreeMarsImageManager.getImage("RED_DOWN_ARROW", 12, 12);
            redDownArrowIcon = new ImageIcon(image);
        }
        return redDownArrowIcon;
    }
}
