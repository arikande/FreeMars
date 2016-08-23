package org.freemars.colonydialog.controller;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragGestureRecognizer;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceContext;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.TransferHandler;

public class PatchedTransferHandler extends TransferHandler implements Serializable {

    private static SwingDragGestureRecognizer recognizer = null;

    public PatchedTransferHandler() {
    }

    public PatchedTransferHandler(String property) {
        super(property);
    }

    private static class SwingDragGestureRecognizer extends DragGestureRecognizer {

        SwingDragGestureRecognizer(DragGestureListener dgl) {
            super(DragSource.getDefaultDragSource(), null, NONE, dgl);
        }

        void gestured(JComponent c, MouseEvent e, int srcActions, int action) {
            setComponent(c);
            setSourceActions(srcActions);
            appendEvent(e);
            fireDragGestureRecognized(action, e.getPoint());
        }

        /**
         * register this DragGestureRecognizer's Listeners with the Component
         */
        protected void registerListeners() {
        }

        /**
         * unregister this DragGestureRecognizer's Listeners with the Component
         * <p/>
         * subclasses must override this method
         */
        protected void unregisterListeners() {
        }
    }

    @Override
    public void exportAsDrag(JComponent comp, InputEvent e, int action) {
        int srcActions = getSourceActions(comp);

        // only mouse events supported for drag operations
        if (!(e instanceof MouseEvent)
                // only support known actions
                || !(action == COPY || action == MOVE || action == DnDConstants.ACTION_LINK)
                // only support valid source actions
                || (srcActions & action) == 0) {

            action = NONE;
        }

        if (action != NONE && !GraphicsEnvironment.isHeadless()) {
            if (recognizer == null) {
                recognizer = new SwingDragGestureRecognizer(new DragHandler());
            }
            recognizer.gestured(comp, (MouseEvent) e, srcActions, action);
        } else {
            exportDone(comp, null, NONE);
        }
    }

    /**
     * This is the default drag handler for drag and drop operations that
     * use the <code>TransferHandler</code>.
     */
    private static class DragHandler implements DragGestureListener, DragSourceListener {

        private boolean scrolls;

        // --- DragGestureListener methods -----------------------------------
        /**
         * a Drag gesture has been recognized
         */
        public void dragGestureRecognized(DragGestureEvent dge) {
            JComponent c = (JComponent) dge.getComponent();
            PatchedTransferHandler th = (PatchedTransferHandler) c.getTransferHandler();
            Transferable t = th.createTransferable(c);
            if (t != null) {
                scrolls = c.getAutoscrolls();
                c.setAutoscrolls(false);
                try {
                    //dge.startDrag(null, t, this);

                    Image img = null;
                    Icon icn = th.getVisualRepresentation(t);
                    if (icn != null) {
                        if (icn instanceof ImageIcon) {
                            img = ((ImageIcon) icn).getImage();
                        } else {
                            img = new BufferedImage(icn.getIconWidth(),
                                    icn.getIconWidth(), BufferedImage.TYPE_4BYTE_ABGR);
                            Graphics g = img.getGraphics();
                            icn.paintIcon(c, g, 0, 0);
                        }
                    }
                    if (img == null) {
                        dge.startDrag(null, t, this);
                    } else {
//                        dge.startDrag(null, img, new Point(0, -1 * img.getHeight(null)), t, this);

                        Cursor cursor = c.getToolkit().createCustomCursor(img, new Point(0, 0), "usr");
                        dge.startDrag(cursor, t, this);
                    }


                    return;
                } catch (RuntimeException re) {
                    c.setAutoscrolls(scrolls);
                }
            }

            th.exportDone(c, t, NONE);
        }

        // --- DragSourceListener methods -----------------------------------
        /**
         * as the hotspot enters a platform dependent drop site
         */
        public void dragEnter(DragSourceDragEvent dsde) {
        }

        /**
         * as the hotspot moves over a platform dependent drop site
         */
        public void dragOver(DragSourceDragEvent dsde) {
        }

        /**
         * as the hotspot exits a platform dependent drop site
         */
        public void dragExit(DragSourceEvent dsde) {
        }

        /**
         * as the operation completes
         */
        public void dragDropEnd(DragSourceDropEvent dsde) {
            DragSourceContext dsc = dsde.getDragSourceContext();
            JComponent c = (JComponent) dsc.getComponent();
            if (dsde.getDropSuccess()) {
                ((PatchedTransferHandler) c.getTransferHandler()).exportDone(c, dsc.getTransferable(), dsde.getDropAction());
            } else {
                ((PatchedTransferHandler) c.getTransferHandler()).exportDone(c, dsc.getTransferable(), NONE);
            }
            c.setAutoscrolls(scrolls);
        }

        public void dropActionChanged(DragSourceDragEvent dsde) {
        }
    }
}


