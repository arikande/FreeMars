package org.freemars.ui.image;

/**
 *
 * @author Deniz ARIKAN
 */
public class ImageStoreKey implements Comparable<ImageStoreKey> {

    private final String name;
    private final boolean grayScale;
    private final int width;
    private final int height;

    public ImageStoreKey(String name, boolean grayScale, int width, int height) {
        this.name = name;
        this.grayScale = grayScale;
        this.width = width;
        this.height = height;
    }

    @Override
    public String toString() {
        return getName() + " " + isGrayScale() + " " + getWidth() + "x" + getHeight();
    }

    public int compareTo(ImageStoreKey otherKey) {
        if (!getName().equals(otherKey.getName())) {
            return getName().compareTo(otherKey.getName());
        }
        if (!isGrayScale() && otherKey.isGrayScale()) {
            return -1;
        }
        if (isGrayScale() && !otherKey.isGrayScale()) {
            return 1;
        }

        if (getWidth() != otherKey.getWidth() && getWidth() != -1 && otherKey.getWidth() != -1) {
            return getWidth() < otherKey.getWidth() ? -1 : 1;
        }
        if (getHeight() != otherKey.getHeight() && getHeight() != -1 && otherKey.getHeight() != -1) {
            return getHeight() < otherKey.getHeight() ? -1 : 1;
        }

        return 0;
    }

    public String getName() {
        return name;
    }

    public boolean isGrayScale() {
        return grayScale;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
