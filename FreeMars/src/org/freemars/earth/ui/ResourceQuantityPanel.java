package org.freemars.earth.ui;

import java.awt.BorderLayout;
import java.awt.Image;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.freemars.ui.image.FreeMarsImageManager;
import org.freemars.ui.image.ImagePanel;
import org.freerealm.resource.Resource;

/**
 *
 * @author Deniz ARIKAN
 */
public class ResourceQuantityPanel extends JPanel {

    private ImagePanel resourceImagePanel;
    private JPanel resourceQuantityPanel;
    private JLabel resourceQuantityLabel;
    private Resource resource;
    private int quantity;

    public ResourceQuantityPanel() {
        this(null, 0);
    }

    public ResourceQuantityPanel(Resource resource, int quantity) {
        super(new BorderLayout());
        initializeWidgets();
        this.resource = resource;
        this.quantity = quantity;
        setToolTipText(resource.getName());
        refresh();
    }

    public int getQuantity() {
        return quantity;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
        refresh();
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        refresh();
    }

    private void refresh() {
        if (resource != null) {
            Image resourceImage = FreeMarsImageManager.getImage(resource, quantity == 0);
            resourceImage = FreeMarsImageManager.createResizedCopy(resourceImage, 22, 22, false, this);
            getResourceImagePanel().setImage(resourceImage);
        }
        getresourceQuantityLabel().setText(String.valueOf(quantity));
    }

    private void initializeWidgets() {
        add(Box.createVerticalStrut(2), BorderLayout.PAGE_START);
        add(getResourceImagePanel(), BorderLayout.CENTER);
        add(getResourceQuantityPanel(), BorderLayout.PAGE_END);
    }

    private ImagePanel getResourceImagePanel() {
        if (resourceImagePanel == null) {
            resourceImagePanel = new ImagePanel();
        }
        return resourceImagePanel;
    }

    private JPanel getResourceQuantityPanel() {
        if (resourceQuantityPanel == null) {
            resourceQuantityPanel = new JPanel();
            resourceQuantityPanel.add(getresourceQuantityLabel());
        }
        return resourceQuantityPanel;
    }

    private JLabel getresourceQuantityLabel() {
        if (resourceQuantityLabel == null) {
            resourceQuantityLabel = new JLabel();
        }
        return resourceQuantityLabel;
    }
}
