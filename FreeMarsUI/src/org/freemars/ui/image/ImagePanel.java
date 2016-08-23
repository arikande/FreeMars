package org.freemars.ui.image;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {

    private float imageHorizontalAlignment;
    private float imageVerticalAlignment;
    private Image image;

    public ImagePanel() {
        this(null);
    }

    public ImagePanel(Image image) {
        super();
        this.image = image;
        setImageHorizontalAlignment(CENTER_ALIGNMENT);
        setImageVerticalAlignment(TOP_ALIGNMENT);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, getImagePaintX(), getImagePaintY(), this);
        }
    }

    private int getImagePaintX() {
        int imagePaintX = 0;
        if (getImageHorizontalAlignment() == CENTER_ALIGNMENT) {
            int imageWidth = image.getWidth(this);
            if (imageWidth != -1) {
                imagePaintX = (getWidth() - imageWidth) / 2;
            }
        }
        return imagePaintX;
    }

    private int getImagePaintY() {
        int imagePaintY = 0;
        if (getImageVerticalAlignment() == CENTER_ALIGNMENT) {
            int imageHeight = image.getHeight(this);
            if (imageHeight != -1) {
                imagePaintY = (getHeight() - imageHeight) / 2;
            }
        }
        return imagePaintY;
    }

    private float getImageHorizontalAlignment() {
        return imageHorizontalAlignment;
    }

    public void setImageHorizontalAlignment(float imageHorizontalAlignment) {
        this.imageHorizontalAlignment = imageHorizontalAlignment;
    }

    private float getImageVerticalAlignment() {
        return imageVerticalAlignment;
    }

    public void setImageVerticalAlignment(float imageVerticalAlignment) {
        this.imageVerticalAlignment = imageVerticalAlignment;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}

