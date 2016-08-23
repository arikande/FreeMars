package org.freemars.about;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import org.freemars.model.FreeMarsModel;
import org.freemars.ui.util.FreeMarsDialog;
import org.freerealm.FreeRealmConstants;

/**
 *
 * @author Deniz ARIKAN
 */
public class AboutDialog extends FreeMarsDialog {

    private static final int FRAME_WIDTH = 600;
    private static final int FRAME_HEIGHT = 700;
    private static final Font VERSIONS_FONT = new Font("Arial", Font.BOLD, 16);
    private static final Font UPDATE_HISTORY_FONT = new Font("Arial", Font.PLAIN, 12);
    private JPanel versionsPanel;
    private JPanel updateHistoryPanel;
    private JLabel freeMarsVersionLabel;
    private JLabel freeMarsVersionValueLabel;
    private JLabel freeRealmVersionLabel;
    private JLabel freeRealmVersionValueLabel;
    private JTabbedPane updateHistoryTabbedPane;
    private JPanel freeMarsUpdateHistoryPanel;
    private JScrollPane freeMarsUpdateHistoryScrollPane;
    private JTextArea freeMarsUpdateHistoryTextArea;
    private JPanel freeRealmUpdateHistoryPanel;
    private JScrollPane freeRealmUpdateHistoryScrollPane;
    private JTextArea freeRealmUpdateHistoryTextArea;

    public AboutDialog(Frame owner) {
        super(owner);
        setModal(true);
        setLayout(new BorderLayout());
        setTitle("About");
        initializeWidgets();
    }

    public void display() {
        display(FRAME_WIDTH, FRAME_HEIGHT);
    }

    private void initializeWidgets() {
        add(getVersionsPanel(), BorderLayout.PAGE_START);
        add(getUpdateHistoryPanel(), BorderLayout.CENTER);
        getFreeMarsUpdateHistoryTextArea().setCaretPosition(0);
        getFreeRealmUpdateHistoryTextArea().setCaretPosition(0);
    }

    private JPanel getVersionsPanel() {
        if (versionsPanel == null) {
            versionsPanel = new JPanel(new GridLayout(4, 2, 10, 10));
            versionsPanel.add(Box.createVerticalBox());
            versionsPanel.add(Box.createVerticalBox());
            versionsPanel.add(getFreeMarsVersionLabel());
            versionsPanel.add(getFreeMarsVersionValueLabel());
            versionsPanel.add(getFreeRealmVersionLabel());
            versionsPanel.add(getFreeRealmVersionValueLabel());
            versionsPanel.add(Box.createVerticalBox());
            versionsPanel.add(Box.createVerticalBox());
        }
        return versionsPanel;
    }

    private JPanel getUpdateHistoryPanel() {
        if (updateHistoryPanel == null) {
            updateHistoryPanel = new JPanel(new BorderLayout());
            updateHistoryPanel.add(getUpdateHistoryTabbedPane(), BorderLayout.CENTER);
        }
        return updateHistoryPanel;
    }

    private JLabel getFreeMarsVersionLabel() {
        if (freeMarsVersionLabel == null) {
            freeMarsVersionLabel = new JLabel(" Free Mars");
            freeMarsVersionLabel.setFont(VERSIONS_FONT);
        }
        return freeMarsVersionLabel;
    }

    private JLabel getFreeMarsVersionValueLabel() {
        if (freeMarsVersionValueLabel == null) {
            freeMarsVersionValueLabel = new JLabel(FreeMarsModel.getVersion());
            freeMarsVersionValueLabel.setFont(VERSIONS_FONT);
        }
        return freeMarsVersionValueLabel;
    }

    private JLabel getFreeRealmVersionLabel() {
        if (freeRealmVersionLabel == null) {
            freeRealmVersionLabel = new JLabel(" Free Realm");
            freeRealmVersionLabel.setFont(VERSIONS_FONT);
        }
        return freeRealmVersionLabel;
    }

    private JLabel getFreeRealmVersionValueLabel() {
        if (freeRealmVersionValueLabel == null) {
            freeRealmVersionValueLabel = new JLabel(FreeRealmConstants.getVersion());
            freeRealmVersionValueLabel.setFont(VERSIONS_FONT);
        }
        return freeRealmVersionValueLabel;
    }

    private JTabbedPane getUpdateHistoryTabbedPane() {
        if (updateHistoryTabbedPane == null) {
            updateHistoryTabbedPane = new JTabbedPane();
            updateHistoryTabbedPane.addTab("Free Mars", getFreeMarsUpdateHistoryPanel());
            updateHistoryTabbedPane.addTab("Free Realm", getFreeRealmUpdateHistoryPanel());
        }
        return updateHistoryTabbedPane;
    }

    private JPanel getFreeMarsUpdateHistoryPanel() {
        if (freeMarsUpdateHistoryPanel == null) {
            freeMarsUpdateHistoryPanel = new JPanel(new BorderLayout());
            freeMarsUpdateHistoryPanel.add(getFreeMarsUpdateHistoryScrollPane(), BorderLayout.CENTER);
        }
        return freeMarsUpdateHistoryPanel;
    }

    private JScrollPane getFreeMarsUpdateHistoryScrollPane() {
        if (freeMarsUpdateHistoryScrollPane == null) {
            freeMarsUpdateHistoryScrollPane = new JScrollPane(getFreeMarsUpdateHistoryTextArea(), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        }
        return freeMarsUpdateHistoryScrollPane;
    }

    private JTextArea getFreeMarsUpdateHistoryTextArea() {
        if (freeMarsUpdateHistoryTextArea == null) {
            freeMarsUpdateHistoryTextArea = new JTextArea();
            freeMarsUpdateHistoryTextArea.setEditable(false);
            freeMarsUpdateHistoryTextArea.setLineWrap(true);
            freeMarsUpdateHistoryTextArea.setWrapStyleWord(true);
            freeMarsUpdateHistoryTextArea.setFont(UPDATE_HISTORY_FONT);
            try {
                BufferedInputStream bufferedInputStream = new BufferedInputStream(ClassLoader.getSystemResourceAsStream("free_mars_versioninfo.txt"));
                BufferedReader bufferedReader = (new BufferedReader(new InputStreamReader(bufferedInputStream)));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    freeMarsUpdateHistoryTextArea.append(line + "\n");
                }
            } catch (IOException iOException) {
            }
        }
        return freeMarsUpdateHistoryTextArea;
    }

    private JPanel getFreeRealmUpdateHistoryPanel() {
        if (freeRealmUpdateHistoryPanel == null) {
            freeRealmUpdateHistoryPanel = new JPanel(new BorderLayout());
            freeRealmUpdateHistoryPanel.add(getFreeRealmUpdateHistoryScrollPane(), BorderLayout.CENTER);
        }
        return freeRealmUpdateHistoryPanel;
    }

    private JScrollPane getFreeRealmUpdateHistoryScrollPane() {
        if (freeRealmUpdateHistoryScrollPane == null) {
            freeRealmUpdateHistoryScrollPane = new JScrollPane(getFreeRealmUpdateHistoryTextArea(), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        }
        return freeRealmUpdateHistoryScrollPane;
    }

    private JTextArea getFreeRealmUpdateHistoryTextArea() {
        if (freeRealmUpdateHistoryTextArea == null) {
            freeRealmUpdateHistoryTextArea = new JTextArea();
            freeRealmUpdateHistoryTextArea.setEditable(false);
            freeRealmUpdateHistoryTextArea.setLineWrap(true);
            freeRealmUpdateHistoryTextArea.setWrapStyleWord(true);
            freeRealmUpdateHistoryTextArea.setFont(UPDATE_HISTORY_FONT);
            try {
                BufferedInputStream bufferedInputStream = new BufferedInputStream(ClassLoader.getSystemResourceAsStream("free_realm_versioninfo.txt"));
                BufferedReader bufferedReader = (new BufferedReader(new InputStreamReader(bufferedInputStream)));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    freeRealmUpdateHistoryTextArea.append(line + "\n");
                }
            } catch (IOException iOException) {
            }
        }
        return freeRealmUpdateHistoryTextArea;
    }
}
