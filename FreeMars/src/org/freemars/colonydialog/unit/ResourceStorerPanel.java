package org.freemars.colonydialog.unit;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.TransferHandler;
import javax.swing.event.MouseInputAdapter;
import org.freerealm.Container;
import org.freerealm.resource.Resource;

/**
 *
 * @author Deniz ARIKAN
 */
public class ResourceStorerPanel extends JPanel {

    private JProgressBar progressBar;
    private JPanel resourcesPanel;
    private Container container;
    private Resource mouseMovedResource;

    public ResourceStorerPanel() {
        super(new BorderLayout());
        add(getProgressBar(), BorderLayout.PAGE_START);
        add(getResourcesPanel(), BorderLayout.CENTER);
    }

    public void setContainer(Container container) {
        this.container = container;
        refresh();
    }

    public Resource getResource() {
        return mouseMovedResource;
    }

    private JPanel getResourcesPanel() {
        if (resourcesPanel == null) {
            resourcesPanel = new JPanel(new GridLayout(0, 4));
        }
        return resourcesPanel;
    }

    private JProgressBar getProgressBar() {
        if (progressBar == null) {
            progressBar = new JProgressBar();
            progressBar.setStringPainted(true);
        }
        return progressBar;
    }

    private void setMouseMovedResource(Resource resource) {
        this.mouseMovedResource = resource;
    }

    private void refresh() {
        if (container != null) {
            int totalCapacity = container.getTotalCapacity(null);
            getResourcesPanel().removeAll();
            Iterator<Resource> iterator = container.getStorableResourcesIterator();
            int totalAdded = 0;
            while (iterator.hasNext()) {
                Resource resource = iterator.next();
                if (container.getResourceQuantity(resource) > 0) {
                    ResourceQuantityPanel resourceQuantityPanel = new ResourceQuantityPanel(resource, container.getResourceQuantity(resource));
                    resourceQuantityPanel.addMouseListener(new ResourceQuantityPanelMouseListener(this, resourceQuantityPanel));
                    resourceQuantityPanel.addMouseMotionListener(new ResourceQuantityPanelMouseListener(this, resourceQuantityPanel));
                    getResourcesPanel().add(resourceQuantityPanel);
                    totalAdded = totalAdded + 1;
                }
            }
            for (int i = 0; i < 12 - totalAdded; i++) {
                getResourcesPanel().add(new JPanel());
            }
            getProgressBar().setMaximum(totalCapacity);
            getProgressBar().setValue(container.getTotalContainedWeight());
            getProgressBar().setString(container.getTotalContainedWeight() + "/" + totalCapacity);
        }
    }

    private class ResourceQuantityPanelMouseListener extends MouseInputAdapter {

        private final ResourceStorerPanel resourceStorerPanel;
        private final ResourceQuantityPanel resourceQuantityPanel;

        private ResourceQuantityPanelMouseListener(ResourceStorerPanel resourceStorerPanel, ResourceQuantityPanel resourceQuantityPanel) {
            this.resourceStorerPanel = resourceStorerPanel;
            this.resourceQuantityPanel = resourceQuantityPanel;
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            resourceQuantityPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            setMouseMovedResource(resourceQuantityPanel.getResource());
        }

        @Override()
        public void mousePressed(MouseEvent e) {
            TransferHandler handler = resourceStorerPanel.getTransferHandler();
            handler.exportAsDrag(resourceStorerPanel, e, TransferHandler.COPY);
        }
    }
}
