package org.freemars.editor.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;
import org.freemars.editor.model.EditorModel;
import org.freemars.ui.util.FreeMarsDialog;

/**
 *
 * @author Deniz ARIKAN
 */
public class MapInformationDialog extends FreeMarsDialog {

    private static final int FRAME_WIDTH = 340;
    private static final int FRAME_HEIGHT = 380;
    private JTabbedPane mainTabbedPane;
    private JPanel mapInformationPanel;
    private JPanel detailedInformationPanel;
    private JScrollPane detailedInformationScrollPane;
    private JTable detailedInformationTable;
    private DefaultTableModel detailedInformationTableModel;
    private JLabel mapNameLabel;
    private JTextField mapNameTextField;
    private JLabel mapDescriptionLabel;
    private JScrollPane mapDescriptionScrollPane;
    private JTextArea mapDescriptionTextArea;
    private JLabel mapWidthLabel;
    private JSpinner mapWidthSpinner;
    private JLabel mapHeightLabel;
    private JSpinner mapHeightSpinner;
    private JLabel suggestedPlayersLabel;
    private JSpinner suggestedPlayersSpinner;
    private JPanel pageEndPanel;
    private JButton confirmButton;
    private JButton cancelButton;

    public MapInformationDialog(Frame owner) {
        super(owner);
        setModal(true);
        setTitle("Map information");
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(Box.createHorizontalStrut(15), BorderLayout.LINE_START);
        getContentPane().add(Box.createHorizontalStrut(15), BorderLayout.LINE_END);
        getContentPane().add(getMainTabbedPane(), BorderLayout.CENTER);
        getContentPane().add(getPageEndPanel(), BorderLayout.PAGE_END);
    }

    public void display() {
        pack();
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width - FRAME_WIDTH) / 2, (screenSize.height - FRAME_HEIGHT) / 2, FRAME_WIDTH, FRAME_HEIGHT);
        setVisible(true);
        toFront();
    }

    public void setConfirmButtonAction(Action action) {
        getConfirmButton().setAction(action);
        getConfirmButton().setText("Confirm");
    }

    public void setCancelButtonAction(Action action) {
        getCancelButton().setAction(action);
        getCancelButton().setText("Cancel");
    }

    public String getMapNameTextFieldValue() {
        return getMapNameTextField().getText();
    }

    public void setMapNameTextFieldValue(String text) {
        getMapNameTextField().setText(text);
    }

    public String getMapDescriptionTextAreaValue() {
        return getMapDescriptionTextArea().getText();
    }

    public void setMapDescriptionTextAreaValue(String text) {
        getMapDescriptionTextArea().setText(text);
    }

    public int getMapWidthSpinnerValue() {
        return Integer.parseInt(getMapWidthSpinner().getValue().toString());
    }

    public void setMapWidthSpinnerValue(int value) {
        getMapWidthSpinner().setValue(value);
    }

    public int getMapHeightSpinnerValue() {
        return Integer.parseInt(getMapHeightSpinner().getValue().toString());
    }

    public void setMapHeightSpinnerValue(int value) {
        getMapHeightSpinner().setValue(value);
    }

    public int getSuggestedPlayersSpinnerValue() {
        return Integer.parseInt(getSuggestedPlayersSpinner().getValue().toString());
    }

    public void setSuggestedPlayersSpinnerValue(int value) {
        getSuggestedPlayersSpinner().setValue(value);
    }

    public void addDetailInformation(String name, Object value) {
        getDetailedInformationTableModel().addRow(new Object[]{name, value});
    }

    private JTabbedPane getMainTabbedPane() {
        if (mainTabbedPane == null) {
            mainTabbedPane = new JTabbedPane();
            mainTabbedPane.addTab("Map", getMapInformationPanel());
            mainTabbedPane.addTab("Details", getDetailedInformationPanel());
        }
        return mainTabbedPane;
    }

    private JPanel getMapInformationPanel() {
        if (mapInformationPanel == null) {
            mapInformationPanel = new JPanel(new GridLayout(0, 1));
            mapInformationPanel.add(getMapNameLabel());
            mapInformationPanel.add(getMapNameTextField());
            mapInformationPanel.add(getMapDescriptionLabel());
            mapInformationPanel.add(getMapDescriptionScrollPane());
            mapInformationPanel.add(getMapWidthLabel());
            mapInformationPanel.add(getMapWidthSpinner());
            mapInformationPanel.add(getMapHeightLabel());
            mapInformationPanel.add(getMapHeightSpinner());
            mapInformationPanel.add(getSuggestedPlayersLabel());
            mapInformationPanel.add(getSuggestedPlayersSpinner());
        }
        return mapInformationPanel;
    }

    private JPanel getDetailedInformationPanel() {
        if (detailedInformationPanel == null) {
            detailedInformationPanel = new JPanel(new BorderLayout());
            detailedInformationPanel.add(getDetailedInformationScrollPane(), BorderLayout.CENTER);
        }
        return detailedInformationPanel;
    }

    private JScrollPane getDetailedInformationScrollPane() {
        if (detailedInformationScrollPane == null) {
            detailedInformationScrollPane = new JScrollPane(getDetailedInformationTable());
        }
        return detailedInformationScrollPane;
    }

    private JTable getDetailedInformationTable() {
        if (detailedInformationTable == null) {
            detailedInformationTable = new JTable(getDetailedInformationTableModel());
            detailedInformationTable.setTableHeader(null);
            detailedInformationTable.setRowHeight(20);
            detailedInformationTable.setEnabled(false);
            detailedInformationTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        }
        return detailedInformationTable;
    }

    private DefaultTableModel getDetailedInformationTableModel() {
        if (detailedInformationTableModel == null) {
            detailedInformationTableModel = new DefaultTableModel();
            detailedInformationTableModel.addColumn("Name");
            detailedInformationTableModel.addColumn("Value");
        }
        return detailedInformationTableModel;
    }

    private JLabel getMapNameLabel() {
        if (mapNameLabel == null) {
            mapNameLabel = new JLabel("Name");
        }
        return mapNameLabel;
    }

    private JTextField getMapNameTextField() {
        if (mapNameTextField == null) {
            mapNameTextField = new JTextField();
        }
        return mapNameTextField;
    }

    private JLabel getMapDescriptionLabel() {
        if (mapDescriptionLabel == null) {
            mapDescriptionLabel = new JLabel("Description");
        }
        return mapDescriptionLabel;
    }

    private JScrollPane getMapDescriptionScrollPane() {
        if (mapDescriptionScrollPane == null) {
            mapDescriptionScrollPane = new JScrollPane(getMapDescriptionTextArea());
        }
        return mapDescriptionScrollPane;
    }

    private JTextArea getMapDescriptionTextArea() {
        if (mapDescriptionTextArea == null) {
            mapDescriptionTextArea = new JTextArea();
        }
        return mapDescriptionTextArea;
    }

    private JLabel getMapWidthLabel() {
        if (mapHeightLabel == null) {
            mapWidthLabel = new JLabel("Width");
        }
        return mapWidthLabel;
    }

    private JSpinner getMapWidthSpinner() {
        if (mapWidthSpinner == null) {
            SpinnerModel spinnerModel = new SpinnerNumberModel(40, 40, 70, 1);
            mapWidthSpinner = new JSpinner(spinnerModel);
            mapWidthSpinner.setEditor(new JSpinner.DefaultEditor(mapWidthSpinner));
        }
        return mapWidthSpinner;
    }

    private JLabel getMapHeightLabel() {
        if (mapHeightLabel == null) {
            mapHeightLabel = new JLabel("Height");
        }
        return mapHeightLabel;
    }

    private JSpinner getMapHeightSpinner() {
        if (mapHeightSpinner == null) {
            SpinnerModel spinnerModel = new SpinnerNumberModel(80, 80, 130, 1);
            mapHeightSpinner = new JSpinner(spinnerModel);
            mapHeightSpinner.setEditor(new JSpinner.DefaultEditor(mapHeightSpinner));
        }
        return mapHeightSpinner;
    }

    private JLabel getSuggestedPlayersLabel() {
        if (suggestedPlayersLabel == null) {
            suggestedPlayersLabel = new JLabel("Suggested players");
        }
        return suggestedPlayersLabel;
    }

    private JSpinner getSuggestedPlayersSpinner() {
        if (suggestedPlayersSpinner == null) {
            SpinnerModel spinnerModel
                    = new SpinnerNumberModel(4, EditorModel.SUGGESTED_PLAYERS_MINIMUM_VALUE, EditorModel.SUGGESTED_PLAYERS_MAXIMUM_VALUE, 1);
            suggestedPlayersSpinner = new JSpinner(spinnerModel);
            suggestedPlayersSpinner.setEditor(new JSpinner.DefaultEditor(suggestedPlayersSpinner));
        }
        return suggestedPlayersSpinner;
    }

    private JPanel getPageEndPanel() {
        if (pageEndPanel == null) {
            pageEndPanel = new JPanel();
            pageEndPanel.add(getConfirmButton());
            pageEndPanel.add(getCancelButton());
        }
        return pageEndPanel;
    }

    private JButton getConfirmButton() {
        if (confirmButton == null) {
            confirmButton = new JButton("Confirm");
        }
        return confirmButton;
    }

    private JButton getCancelButton() {
        if (cancelButton == null) {
            cancelButton = new JButton();
        }
        return cancelButton;
    }
}
