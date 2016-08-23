package org.freemars.ui.image;

import java.awt.Image;
import java.awt.MediaTracker;
import java.util.HashMap;
import javax.swing.JLabel;

/**
 *
 * @author Deniz ARIKAN
 */
public class ImageStore {

    private final boolean preloadImages;
    private final HashMap<String, Image> images;
    private final MediaTracker mediaTracker;
    private int imageId;

    public ImageStore(boolean preloadImages) {
        this.preloadImages = preloadImages;
        images = new HashMap<String, Image>();
        mediaTracker = new MediaTracker(new JLabel());
        imageId = 0;
    }

    public Image get(String key) {
        return images.get(key);
    }

    public boolean containsImage(String key) {
        return images.containsKey(key);
    }

    public void add(String key, Image image) {
        images.put(key, image);
        if (preloadImages) {
            mediaTracker.addImage(image, imageId);
            imageId = imageId + 1;
            try {
                mediaTracker.waitForAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
