package org.freemars.ui.mainmenu;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import org.freemars.model.FreeMarsModel;
import org.freemars.ui.image.ImagePanel;
import org.freerealm.FreeRealmConstants;

/**
 *
 * @author Deniz ARIKAN
 */
public class MainMenuBackgroundImagePanel extends ImagePanel {

    public MainMenuBackgroundImagePanel(Image image) {
        super(image);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        Color originalColor = g2d.getColor();
        Font originalFont = g2d.getFont();
        g2d.setColor(Color.white);
        g2d.setFont(originalFont.deriveFont(10f));
        FontMetrics metrics = g2d.getFontMetrics();
        int fontHeight = metrics.getHeight();
        String freeRealmVersion = "Free Realm  " + FreeRealmConstants.getVersion();
        int freeRealmVersionWidth = metrics.stringWidth(freeRealmVersion);
        int freeRealmVersionX = getWidth() - freeRealmVersionWidth - (freeRealmVersionWidth / 8);
        int freeRealmVersionY = getHeight() - fontHeight - (fontHeight / 8);
        String freeMarsVersion = "Free Mars  " + FreeMarsModel.getVersion();
        int freeMarsVersionWidth = metrics.stringWidth(freeMarsVersion);
        int freeMarsVersionX = getWidth() - freeMarsVersionWidth - (freeMarsVersionWidth / 8);
        int freeMarsVersionY = getHeight() - (2 * (fontHeight + (fontHeight / 8)));
        int beginX = Math.min(freeRealmVersionX, freeMarsVersionX);
        g2d.drawString(freeRealmVersion, beginX, freeRealmVersionY);
        g2d.drawString(freeMarsVersion, beginX, freeMarsVersionY);
        g2d.setColor(originalColor);
        g2d.setFont(originalFont);
    }
}
