package org.freemars.ui.player;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.event.MouseInputAdapter;
import org.freemars.ui.image.FreeMarsImageManager;
import org.freemars.ui.util.TurnToDateConverter;

/**
 *
 * @author Deniz ARIKAN
 */
public class PlayerStatusInfoPanel extends JPanel {

    public static final int POPULATION_INCREASED = 1;
    public static final int POPULATION_EQUAL = 0;
    public static final int POPULATION_DECREASED = -1;
    public static final int TREASURY_INCREASED = 1;
    public static final int TREASURY_EQUAL = 0;
    public static final int TREASURY_DECREASED = -1;
    private InfoLabel nationLabel;
    private InfoLabel populationLabel;
    private InfoLabel treasuryLabel;
    private InfoLabel earthTaxRateLabel;
    private InfoLabel turnsLabel;

    public PlayerStatusInfoPanel() {
        setLayout(new FlowLayout(FlowLayout.TRAILING, 10, 0));
        add(getNationLabel());
        add(getPopulationLabel());
        add(getTreasuryLabel());
        add(getEarthTaxRateLabel());
        add(getTurnsLabel());
    }

    public void addNationLabelMouseListener(MouseListener mouseListener) {
        getNationLabel().addMouseListener(mouseListener);
    }

    public void addPopulationLabelMouseListener(MouseListener mouseListener) {
        getPopulationLabel().addMouseListener(mouseListener);
    }

    public void addEarthTaxRateLabelMouseListener(MouseListener mouseListener) {
        getEarthTaxRateLabel().addMouseListener(mouseListener);
    }

    public void addTreasuryLabelLabelMouseListener(MouseListener mouseListener) {
        getTreasuryLabel().addMouseListener(mouseListener);
    }

    public void setNationName(String nationName) {
        getNationLabel().setText("  " + nationName);
    }

    public void setPopulation(int population) {
        getPopulationLabel().setText(population + " people");
    }

    public void setPopulationChangeStatus(int populationChangeStatus) {
        Icon icon = null;
        switch (populationChangeStatus) {
            case POPULATION_INCREASED:
                icon = new ImageIcon(FreeMarsImageManager.getImage("GREEN_UP_ARROW", 15, 15));
                break;
            case POPULATION_DECREASED:
                icon = new ImageIcon(FreeMarsImageManager.getImage("RED_DOWN_ARROW"));
                break;
            case POPULATION_EQUAL:
            default:
                icon = new ImageIcon(FreeMarsImageManager.getImage("BLUE_EQUAL_ARROW"));
                break;
        }
        getPopulationLabel().getLabel().setHorizontalTextPosition(JLabel.LEFT);
        getPopulationLabel().getLabel().setIcon(icon);
    }

    public void setTreasuryChangeStatus(int treasuryChangeStatus) {
        Icon icon = null;
        switch (treasuryChangeStatus) {
            case TREASURY_INCREASED:
                icon = new ImageIcon(FreeMarsImageManager.getImage("GREEN_UP_ARROW", 15, 15));
                break;
            case TREASURY_DECREASED:
                icon = new ImageIcon(FreeMarsImageManager.getImage("RED_DOWN_ARROW"));
                break;
            case TREASURY_EQUAL:
            default:
                icon = new ImageIcon(FreeMarsImageManager.getImage("BLUE_EQUAL_ARROW"));
                break;
        }
        getTreasuryLabel().getLabel().setHorizontalTextPosition(JLabel.LEFT);
        getTreasuryLabel().getLabel().setIcon(icon);
    }

    public void setTreasury(int wealth) {
        NumberFormat formatter = new DecimalFormat();
        String wealthString = formatter.format(wealth);
        getTreasuryLabel().setText(wealthString + " credits");
    }

    public void setEarthTaxRate(byte colonialTax) {
        getEarthTaxRateLabel().setText("Earth tax " + colonialTax + "%");
        getEarthTaxRateLabel().setToolTipText("Earth tax " + colonialTax + "%");
    }

    public void setTurns(int turns) {
        getTurnsLabel().setText(new TurnToDateConverter(turns).gateDateString());
        getTurnsLabel().setToolTipText("Turn " + turns);
    }

    private InfoLabel getNationLabel() {
        if (nationLabel == null) {
            nationLabel = new InfoLabel();
            nationLabel.addMouseMotionListener(new MouseInputAdapter() {

                @Override
                public void mouseMoved(MouseEvent e) {
                    nationLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
            });
        }
        return nationLabel;
    }

    private InfoLabel getPopulationLabel() {
        if (populationLabel == null) {
            populationLabel = new InfoLabel();
            populationLabel.addMouseMotionListener(new MouseInputAdapter() {

                @Override
                public void mouseMoved(MouseEvent e) {
                    populationLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
            });
        }
        return populationLabel;
    }

    private InfoLabel getTreasuryLabel() {
        if (treasuryLabel == null) {
            treasuryLabel = new InfoLabel();
            treasuryLabel.setPreferredSize(new Dimension(140, 20));
            treasuryLabel.addMouseMotionListener(new MouseInputAdapter() {

                @Override
                public void mouseMoved(MouseEvent e) {
                    treasuryLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
            });
        }
        return treasuryLabel;
    }

    private InfoLabel getEarthTaxRateLabel() {
        if (earthTaxRateLabel == null) {
            earthTaxRateLabel = new InfoLabel();
            earthTaxRateLabel.setPreferredSize(new Dimension(110, 20));
            earthTaxRateLabel.addMouseMotionListener(new MouseInputAdapter() {

                @Override
                public void mouseMoved(MouseEvent e) {
                    earthTaxRateLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
            });
        }
        return earthTaxRateLabel;
    }

    private InfoLabel getTurnsLabel() {
        if (turnsLabel == null) {
            turnsLabel = new InfoLabel();
            turnsLabel.setPreferredSize(new Dimension(140, 20));
        }
        return turnsLabel;
    }

    public void setNationFlag(Image image) {
        getNationLabel().setImage(image);
    }

    class InfoLabel extends JPanel {

        private JLabel label;

        public InfoLabel() {
            setLayout(new BorderLayout(10, 0));
            add(new JSeparator(SwingConstants.VERTICAL), BorderLayout.LINE_START);
            add(getLabel(), BorderLayout.CENTER);
        }

        private JLabel getLabel() {
            if (label == null) {
                label = new JLabel();
            }
            return label;
        }

        public void setText(String text) {
            getLabel().setText(text);
        }

        public void setImage(Image image) {
            getLabel().setIcon(new ImageIcon(image));
        }
    }
}
