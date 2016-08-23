package org.freemars.ui.image;

import java.awt.Image;
import java.awt.MediaTracker;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.JLabel;

/**
 *
 * @author Deniz ARIKAN
 */
public class KeyedImageStore {

    private static final MediaTracker mediaTracker = new MediaTracker(new JLabel());
    private static int imageId;
    private final Map<ImageStoreKey, Image> images;

    public KeyedImageStore() {
        images = new TreeMap<ImageStoreKey, Image>();
    }

    public static void loadImage(Image image) {
        mediaTracker.addImage(image, imageId);
        imageId = imageId + 1;
        try {
            mediaTracker.waitForAll();
        } catch (InterruptedException e) {
        }
    }

    public int getImageCount() {
        return images.size();
    }

    public void add(ImageStoreKey imageStoreKey, Image image) {
        images.put(imageStoreKey, image);
    }

    public boolean containsImage(ImageStoreKey imageStoreKey) {
        return images.containsKey(imageStoreKey);
    }

    public Image get(ImageStoreKey imageStoreKey) {
        return images.get(imageStoreKey);
    }
}
