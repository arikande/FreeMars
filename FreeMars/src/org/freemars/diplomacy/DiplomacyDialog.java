package org.freemars.diplomacy;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.MouseAdapter;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.TableModel;
import org.freemars.ui.util.FreeMarsDialog;

/**
 *
 * @author Deniz ARIKAN
 */
public class DiplomacyDialog extends FreeMarsDialog {

    private final int FRAME_WIDTH = 720;
    private final int FRAME_HEIGHT = 360;
    private JPanel mainPanel;
    private JScrollPane diplomacyScrollPane;
    private DiplomacyTable diplomacyTable;

    private JPanel footerPanel;
    private JButton closeButton;

    public DiplomacyDialog(Frame owner) {
        super(owner);
        setModal(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setTitle("Diplomacy");
        initializeWidgets();
    }

    public void display() {
        pack();
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width - FRAME_WIDTH) / 2, (screenSize.height - FRAME_HEIGHT) / 2, FRAME_WIDTH, FRAME_HEIGHT);
        setVisible(true);
        toFront();
    }

    public void setDiplomacyTableModel(TableModel tableModel) {
        getDiplomacyTable().setModel(tableModel);
        getDiplomacyTable().getColumnModel().getColumn(1).setCellRenderer(new NationNameAndFlagRenderer());
    }

    public void setDiplomacyTableMouseAdapter(MouseAdapter mouseAdapter) {
        getDiplomacyTable().addMouseListener(mouseAdapter);
        getDiplomacyTable().addMouseMotionListener(mouseAdapter);
    }

    public void setCloseAction(Action action) {
        getCloseButton().setAction(action);
    }

    private void initializeWidgets() {
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(getMainPanel(), BorderLayout.CENTER);
        getContentPane().add(getFooterPanel(), BorderLayout.PAGE_END);
    }

    private JPanel getMainPanel() {
        if (mainPanel == null) {
            mainPanel = new JPanel(new BorderLayout());
            mainPanel.add(getDiplomacyScrollPane());
        }
        return mainPanel;
    }

    private JScrollPane getDiplomacyScrollPane() {
        if (diplomacyScrollPane == null) {
            diplomacyScrollPane = new JScrollPane(getDiplomacyTable());
        }
        return diplomacyScrollPane;
    }

    private DiplomacyTable getDiplomacyTable() {
        if (diplomacyTable == null) {
            diplomacyTable = new DiplomacyTable();
        }
        return diplomacyTable;
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
            closeButton = new JButton();
        }
        return closeButton;
    }
}
