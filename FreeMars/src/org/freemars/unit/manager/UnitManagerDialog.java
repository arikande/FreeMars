package org.freemars.unit.manager;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Point;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.MouseInputAdapter;
import org.freemars.ui.util.FreeMarsDialog;

/**
 *
 * @author Deniz ARIKAN
 */
public class UnitManagerDialog extends FreeMarsDialog {

    private static final int FRAME_WIDTH = 1200;
    private static final int FRAME_HEIGHT = 700;
    private JPanel mainPanel;
    private JScrollPane unitsScrollPane;
    private UnitsTable unitsTable;
    private UnitsTableModel unitsTableModel;
    private JPanel unitTypeCountPanel;
    private JPanel footerPanel;
    private JButton closeButton;
    private int frameWidth = FRAME_WIDTH;
    private int frameHeight = FRAME_HEIGHT;

    public UnitManagerDialog(Frame owner) {
        super(owner);
        setModal(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setTitle("Unit manager");
        initializeWidgets();
    }

    public void display() {
        pack();
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        if (frameWidth > screenSize.width) {
            frameWidth = screenSize.width;
        }
        if (frameHeight > screenSize.height) {
            frameHeight = screenSize.height;
        }
        setBounds((screenSize.width - frameWidth) / 2, (screenSize.height - frameHeight) / 2, frameWidth, frameHeight);
        setVisible(true);
        toFront();
    }

    public void setCloseButtonAction(Action action) {
        getCloseButton().setAction(action);
        getCloseButton().setText("Close");
    }

    public void setUnitsTableMouseInputAdapter(MouseInputAdapter mouseInputAdapter) {
        getUnitsTable().addMouseListener(mouseInputAdapter);
        getUnitsTable().addMouseMotionListener(mouseInputAdapter);
    }

    public void clearRows() {
        getUnitsTableModel().clearRows();
        getUnitsTableModel().fireTableDataChanged();
    }

    public void addRow(UnitsTableRow unitManagerDialogTableRow) {
        getUnitsTableModel().addRow(unitManagerDialogTableRow);
        getUnitsTableModel().fireTableDataChanged();
        getUnitsTable().revalidate();
    }

    public int getUnitsTableRowAtPoint(Point point) {
        return getUnitsTable().rowAtPoint(point);
    }

    public int getUnitIdAtPoint(Point point) {
        return getUnitsTable().getUnitIdAtPoint(point);
    }

    public void setUnitsTableSelectionInterval(int start, int end) {
        getUnitsTable().getSelectionModel().setSelectionInterval(start, end);
    }

    public void clearUnitTypeCount() {
        getUnitTypeCountPanel().removeAll();
    }

    public void repaintUnitTypeCount() {
        getUnitTypeCountPanel().revalidate();
        getUnitTypeCountPanel().repaint();
    }

    public void addUnitTypeCount(JLabel unitTypeCount) {
        getUnitTypeCountPanel().add(unitTypeCount);
    }

    private void initializeWidgets() {
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(getMainPanel(), BorderLayout.CENTER);
        getContentPane().add(getFooterPanel(), BorderLayout.PAGE_END);
    }

    private JPanel getMainPanel() {
        if (mainPanel == null) {
            mainPanel = new JPanel(new BorderLayout());
            mainPanel.add(getUnitsScrollPane(), BorderLayout.CENTER);
            mainPanel.add(getUnitTypeCountPanel(), BorderLayout.PAGE_END);
        }
        return mainPanel;
    }

    private JScrollPane getUnitsScrollPane() {
        if (unitsScrollPane == null) {
            unitsScrollPane = new JScrollPane(getUnitsTable());
        }
        return unitsScrollPane;
    }

    private UnitsTable getUnitsTable() {
        if (unitsTable == null) {
            unitsTable = new UnitsTable(getUnitsTableModel());
        }
        return unitsTable;
    }

    private JPanel getUnitTypeCountPanel() {
        if (unitTypeCountPanel == null) {
            unitTypeCountPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        }
        return unitTypeCountPanel;
    }

    private UnitsTableModel getUnitsTableModel() {
        if (unitsTableModel == null) {
            unitsTableModel = new UnitsTableModel();
        }
        return unitsTableModel;
    }

    private JPanel getFooterPanel() {
        if (footerPanel == null) {
            footerPanel = new JPanel();
            footerPanel.add(getCloseButton());
        }
        return footerPanel;
    }

    private JButton getCloseButton() {
        if (closeButton == null) {
            closeButton = new JButton("Close");
        }
        return closeButton;
    }
}
