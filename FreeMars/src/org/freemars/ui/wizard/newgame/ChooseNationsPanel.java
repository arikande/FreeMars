package org.freemars.ui.wizard.newgame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import org.freemars.ui.image.FreeMarsImageManager;
import org.freerealm.nation.Nation;
import org.freerealm.nation.NationManager;

public class ChooseNationsPanel extends JPanel {

    private JPanel contentPanel;
    private JPanel innerPanel;
    private final JLabel iconLabel;
    private final ImageIcon icon;
    private JPanel explanationsPanel;
    private JLabel explanationLabel1;
    private JLabel explanationLabel2;
    private JPanel playerOptionsPanel;
    private JPanel pageEndPanel;
    private JPanel humanPlayerInformationPanel;
    private JLabel humanPlayerLabel;
    private JComboBox humanPlayerComboBox;
    private JLabel playerCountSuggestionLabel;
    private final List<NationCheckBoxPanel> nationOptions;

    public ChooseNationsPanel() {
        nationOptions = new ArrayList<NationCheckBoxPanel>();
        iconLabel = new JLabel();
        contentPanel = getContentPanel();
        contentPanel.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
        setLayout(new BorderLayout());
        icon = new ImageIcon(FreeMarsImageManager.getImage("NEW_GAME_WIZARD_3"));
        if (icon != null) {
            iconLabel.setIcon(icon);
        }
        iconLabel.setBorder(new EtchedBorder(0));
        add(iconLabel, BorderLayout.LINE_START);
        JPanel secondaryPanel = new JPanel();
        secondaryPanel.add(contentPanel, BorderLayout.PAGE_START);
        add(secondaryPanel, BorderLayout.CENTER);
    }

    public void populatePlayerOptionsPanel(NationManager nationManager, List<Nation> selectedNations) {
        playerOptionsPanel.removeAll();
        getNationOptions().clear();
        Iterator<Nation> iterator = nationManager.getNationsIterator();
        while (iterator.hasNext()) {
            Nation nation = iterator.next();
            NationCheckBoxPanel nationCheckBoxPanel = new NationCheckBoxPanel(nation);
            if (selectedNations.contains(nation)) {
                nationCheckBoxPanel.setCheckBoxSelected(true);
            }
            nationCheckBoxPanel.setToolTipRecursively(nationCheckBoxPanel, new NationToolTipTextBuilder().getNationToolTipText(nation));
            playerOptionsPanel.add(nationCheckBoxPanel);
            getNationOptions().add(nationCheckBoxPanel);
        }
    }

    public void setPlayerCountSuggestionLabelIcon(Icon icon) {
        getPlayerCountSuggestionLabel().setIcon(icon);
    }

    public void setPlayerCountSuggestionLabelText(String text) {
        getPlayerCountSuggestionLabel().setText(text);
    }

    private JPanel getContentPanel() {
        if (contentPanel == null) {
            contentPanel = new JPanel();
            contentPanel.setLayout(new BorderLayout());
            contentPanel.add(getInnerPanel(), "Center");
        }
        return contentPanel;
    }

    private JPanel getInnerPanel() {
        if (innerPanel == null) {
            innerPanel = new JPanel();
            innerPanel.setLayout(new BorderLayout(10, 10));
            innerPanel.add(getExplanationsPanel(), BorderLayout.PAGE_START);
            innerPanel.add(getPlayerOptionsPanel(), BorderLayout.CENTER);
            innerPanel.add(getPageEndPanel(), BorderLayout.PAGE_END);

        }
        return innerPanel;
    }

    private JPanel getExplanationsPanel() {
        if (explanationsPanel == null) {
            explanationsPanel = new JPanel(new GridLayout(0, 1, 10, 10));
            explanationsPanel.add(getExplanationLabel1());
            explanationsPanel.add(getExplanationLabel2());
            explanationsPanel.add(new JLabel());
        }
        return explanationsPanel;
    }

    private JLabel getExplanationLabel1() {
        if (explanationLabel1 == null) {
            explanationLabel1 = new JLabel("Choose nations to participate in the colonization of the Red Planet.");
        }
        return explanationLabel1;
    }

    private JLabel getExplanationLabel2() {
        if (explanationLabel2 == null) {
            explanationLabel2 = new JLabel("You need at least two players with different colors to start a new game.");
        }
        return explanationLabel2;
    }

    private JPanel getPlayerOptionsPanel() {
        if (playerOptionsPanel == null) {
            playerOptionsPanel = new JPanel(new GridLayout(0, 2, 50, 10));
        }
        return playerOptionsPanel;
    }

    private JPanel getPageEndPanel() {
        if (pageEndPanel == null) {
            pageEndPanel = new JPanel(new GridLayout(0, 1));
            pageEndPanel.add(new JLabel());
            pageEndPanel.add(getHumanPlayerInformationPanel());
            pageEndPanel.add(new JLabel());
            pageEndPanel.add(getPlayerCountSuggestionLabel());
        }
        return pageEndPanel;
    }

    private JPanel getHumanPlayerInformationPanel() {
        if (humanPlayerInformationPanel == null) {
            humanPlayerInformationPanel = new JPanel(new GridLayout(1, 0));
            humanPlayerInformationPanel.add(getHumanPlayerLabel());
            humanPlayerInformationPanel.add(getHumanPlayerComboBox());
        }
        return humanPlayerInformationPanel;
    }

    private JLabel getHumanPlayerLabel() {
        if (humanPlayerLabel == null) {
            humanPlayerLabel = new JLabel("Human player :");
        }
        return humanPlayerLabel;
    }

    protected JComboBox getHumanPlayerComboBox() {
        if (humanPlayerComboBox == null) {
            humanPlayerComboBox = new JComboBox();
        }
        return humanPlayerComboBox;
    }

    private JLabel getPlayerCountSuggestionLabel() {
        if (playerCountSuggestionLabel == null) {
            playerCountSuggestionLabel = new JLabel("AA");
        }
        return playerCountSuggestionLabel;
    }

    public void addNationOptionsCheckBoxListener(ItemListener itemListener) {
        Iterator<NationCheckBoxPanel> nationOptionsIterator = getNationOptions().iterator();
        while (nationOptionsIterator.hasNext()) {
            NationCheckBoxPanel nationCheckBoxPanel = nationOptionsIterator.next();
            nationCheckBoxPanel.addItemListener(itemListener);
        }
    }

    public void addNationOptionsColorChooserListener(ActionListener actionListener) {
        Iterator<NationCheckBoxPanel> nationOptionsIterator = getNationOptions().iterator();
        while (nationOptionsIterator.hasNext()) {
            NationCheckBoxPanel nationCheckBoxPanel = nationOptionsIterator.next();
            nationCheckBoxPanel.setActionListenerFromParent(actionListener);
        }
    }

    public List<NationCheckBoxPanel> getNationOptions() {
        return nationOptions;
    }

    public Nation getHumanPlayerNation() {
        return (Nation) getHumanPlayerComboBox().getSelectedItem();
    }
}
