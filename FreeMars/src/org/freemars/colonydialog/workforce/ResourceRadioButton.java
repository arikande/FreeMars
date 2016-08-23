package org.freemars.colonydialog.workforce;

import javax.swing.JRadioButton;
import org.freerealm.resource.Resource;

/**
 *
 * @author Deniz ARIKAN
 */
public class ResourceRadioButton extends JRadioButton {

    private final Resource resource;

    public ResourceRadioButton(Resource resource, int productionPerWorker) {
        this.resource = resource;
        setText(resource.getName() + " (" + productionPerWorker + ")");
    }

    public Resource getResource() {
        return resource;
    }
}
