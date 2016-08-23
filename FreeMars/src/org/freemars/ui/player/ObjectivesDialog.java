package org.freemars.ui.player;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Iterator;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.freemars.model.FreeMarsModel;
import org.freemars.model.objective.DeclareAndDefendIndependenceObjective;
import org.freemars.model.objective.DefeatOtherPlayersObjective;
import org.freemars.model.objective.Objective;
import org.freemars.model.objective.ReachPopulationObjective;
import org.freemars.model.objective.ReachTreasuryObjective;
import org.freemars.ui.util.FreeMarsDialog;
import org.freerealm.Realm;
import org.freerealm.player.Player;

/**
 *
 * @author Deniz ARIKAN
 */
public class ObjectivesDialog extends FreeMarsDialog {

    private final int FRAME_WIDTH = 400;
    private final int FRAME_HEIGHT = 300;
    private final FreeMarsModel model;
    private JPanel objectivesPanel;
    private JPanel pageEndPanel;
    private JButton closeButton;

    public ObjectivesDialog(JFrame parent, FreeMarsModel model) {
        super(parent);
        setModal(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setTitle("Objectives");
        this.model = model;
    }

    public void display() {
        getContentPane().setLayout(new BorderLayout(4, 4));
        initializeWidgets();
        pack();
        final java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width - FRAME_WIDTH) / 2, (screenSize.height - FRAME_HEIGHT) / 2, FRAME_WIDTH, FRAME_HEIGHT);
        setVisible(true);
        toFront();
    }

    private void initializeWidgets() {
        add(getObjectivesPanel(), BorderLayout.CENTER);
        add(getPageEndPanel(), BorderLayout.PAGE_END);
    }

    private JPanel getObjectivesPanel() {
        NumberFormat formatter = new DecimalFormat();
        if (objectivesPanel == null) {
            objectivesPanel = new JPanel(new BorderLayout(10, 0));
            JPanel objectivesCheckBoxPanel = new JPanel(new GridLayout(0, 1));
            JPanel objectivesTextPanel = new JPanel(new GridLayout(0, 1));
            Iterator<Objective> iterator = model.getObjectivesIterator();
            while (iterator.hasNext()) {
                Objective objective = iterator.next();
                if (objective instanceof DeclareAndDefendIndependenceObjective) {
                    objectivesCheckBoxPanel.add(new ObjectiveCheckBox(objective, model.getRealm(), model.getActivePlayer()));
                    objectivesTextPanel.add(new JLabel("Declare & defend independence"));
                }
                if (objective instanceof DefeatOtherPlayersObjective) {
                    objectivesCheckBoxPanel.add(new ObjectiveCheckBox(objective, model.getRealm(), model.getActivePlayer()));
                    objectivesTextPanel.add(new JLabel("Defeat other players"));
                }
                if (objective instanceof ReachPopulationObjective) {
                    ReachPopulationObjective reachPopulationObjective = (ReachPopulationObjective) objective;
                    objectivesCheckBoxPanel.add(new ObjectiveCheckBox(objective, model.getRealm(), model.getActivePlayer()));
                    int targetPopulation = reachPopulationObjective.getTargetPopulation();
                    String targetPopulationString = formatter.format(targetPopulation);
                    objectivesTextPanel.add(new JLabel("Reach population of " + targetPopulationString));
                }
                if (objective instanceof ReachTreasuryObjective) {
                    ReachTreasuryObjective reachTreasuryObjective = (ReachTreasuryObjective) objective;
                    objectivesCheckBoxPanel.add(new ObjectiveCheckBox(objective, model.getRealm(), model.getActivePlayer()));
                    int targetTreasury = reachTreasuryObjective.getTargetTreasury();
                    String targetTreasuryString = formatter.format(targetTreasury);
                    objectivesTextPanel.add(new JLabel("Reach treasury of " + targetTreasuryString));
                }
            }
            objectivesPanel.add(objectivesCheckBoxPanel, BorderLayout.LINE_START);
            objectivesPanel.add(objectivesTextPanel, BorderLayout.CENTER);
        }
        return objectivesPanel;
    }

    private JPanel getPageEndPanel() {
        if (pageEndPanel == null) {
            pageEndPanel = new JPanel();
            pageEndPanel.add(getCloseButton());
        }
        return pageEndPanel;
    }

    private JButton getCloseButton() {
        if (closeButton == null) {
            closeButton = new JButton("Close");
            closeButton.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    dispose();
                }
            });
        }
        return closeButton;
    }

    class ObjectiveCheckBox extends JCheckBox {

        protected ObjectiveCheckBox(Objective objective, Realm realm, Player player) {
            setEnabled(false);
            setSelected(objective.isReached(realm, player));
        }
    }
}
