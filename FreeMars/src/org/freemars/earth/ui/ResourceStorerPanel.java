package org.freemars.earth.ui;

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

    public void setResourceStorer(Container container) {
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
        }
        return progressBar;
    }

    private void setMouseMovedResource(Resource resource) {
        this.mouseMovedResource = resource;
    }

    public void refresh() {
        if (container != null) {
            getResourcesPanel().removeAll();
            Iterator<Resource> iterator = container.getStorableResourcesIterator();
            while (iterator.hasNext()) {
                Resource resource = iterator.next();
                ResourceQuantityPanel resourceQuantityPanel = new ResourceQuantityPanel(resource, container.getResourceQuantity(resource));
                resourceQuantityPanel.addMouseListener(new ResourceQuantityPanelMouseListener(this, resourceQuantityPanel));
                resourceQuantityPanel.addMouseMotionListener(new ResourceQuantityPanelMouseListener(this, resourceQuantityPanel));
                getResourcesPanel().add(resourceQuantityPanel);
            }
            getProgressBar().setMaximum(container.getTotalCapacity(null));
            getProgressBar().setValue(container.getTotalContainedWeight());
            getProgressBar().setStringPainted(true);
            getProgressBar().setString(container.getTotalContainedWeight() + "/" + container.getTotalCapacity());
            revalidate();
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
