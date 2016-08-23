package org.freemars.colonydialog.workforce;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TreeMap;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import org.freerealm.resource.Resource;

/**
 *
 * @author Deniz ARIKAN
 */
public class ResourceSelectorPanel extends JPanel {

    private final ButtonGroup resourceButtonGroup;
    private Resource selectedResource;

    public ResourceSelectorPanel(TreeMap<Resource, Integer> selectableResources, Resource selectedResource) {
        setLayout(new GridLayout(0, 1));
        this.selectedResource = selectedResource;
        resourceButtonGroup = new ButtonGroup();
        Iterator<Entry<Resource, Integer>> entryIterator = selectableResources.entrySet().iterator();
        while (entryIterator.hasNext()) {
            Entry<Resource, Integer> entry = entryIterator.next();
            if (entry.getKey().equals(getSelectedResource())) {
                addResourceRadioButton(entry.getKey(), entry.getValue(), true);
            } else {
                addResourceRadioButton(entry.getKey(), entry.getValue(), false);
            }
        }
    }

    public Resource getSelectedResource() {
        return selectedResource;
    }

    private void setSelectedResource(Resource selectedResource) {
        this.selectedResource = selectedResource;
    }

    private void addResourceRadioButton(Resource resource, int productionPerWorker, boolean isSelected) {
        ResourceRadioButton resourceRadio = new ResourceRadioButton(resource, productionPerWorker);
        resourceButtonGroup.add(resourceRadio);
        resourceRadio.setSelected(isSelected);
        resourceRadio.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                ResourceRadioButton sourceRadio = (ResourceRadioButton) e.getSource();
                if (sourceRadio.isSelected()) {
                    setSelectedResource(sourceRadio.getResource());
                }
            }
        });
        add(resourceRadio);
    }
}
