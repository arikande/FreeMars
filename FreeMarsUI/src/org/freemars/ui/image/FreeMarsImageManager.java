package org.freemars.ui.image;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorConvertOp;
import java.awt.image.ColorModel;
import java.awt.image.ImageObserver;
import java.awt.image.PixelGrabber;
import java.io.BufferedInputStream;
import java.io.IOException;
import javax.swing.ImageIcon;
import org.apache.log4j.Logger;
import org.apache.xerces.parsers.DOMParser;
import org.freerealm.nation.Nation;
import org.freerealm.resource.Resource;
import org.freerealm.resource.bonus.FreeRealmBonusResource;
import org.freerealm.settlement.Settlement;
import org.freerealm.settlement.improvement.SettlementImprovement;
import org.freerealm.settlement.improvement.SettlementImprovementType;
import org.freerealm.tile.Tile;
import org.freerealm.tile.TileType;
import org.freerealm.tile.improvement.TileImprovementType;
import org.freerealm.unit.FreeRealmUnitType;
import org.freerealm.unit.Unit;
import org.freerealm.vegetation.Vegetation;
import org.freerealm.vegetation.VegetationType;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author Deniz ARIKAN
 */
public class FreeMarsImageManager {

    private static KeyedImageStore keyedImageStore = new KeyedImageStore();
    private static KeyedImageStore resizedImageStore = new KeyedImageStore();
    private static final Logger logger = Logger.getLogger(FreeMarsImageManager.class);

    public static void init() {
        long start = System.currentTimeMillis();
        resizedImageStore = new KeyedImageStore();
        keyedImageStore = new KeyedImageStore();
        parseImagesFiles();
        long duration = System.currentTimeMillis() - start;

        StringBuilder log = new StringBuilder();
        log.append("Free Mars image manager initialized in ");
        log.append(duration);
        log.append(" milliseconds with ");
        log.append(keyedImageStore.getImageCount());
        log.append(" images.");
        logger.info(log);
    }

    public static Image getImage(Object object) {
        return getImage(object, false, -1, -1);
    }

    public static Image getImage(Object object, boolean isGrayScale) {
        return getImage(object, isGrayScale, -1, -1);
    }

    public static Image getImage(Object object, int width, int height) {
        return getImage(object, false, width, height);
    }

    public static Image getImage(Object object, boolean grayScale, int width, int height) {
        String imageStringKey = getImageKey(object);
        if (grayScale) {
            ImageStoreKey grayScaleImageStoreKey = new ImageStoreKey(imageStringKey, true, width, height);
            if (!keyedImageStore.containsImage(grayScaleImageStoreKey)) {
                Image image = getImage(imageStringKey, false, width, height);
                image = convertToGrayscale(image);
                keyedImageStore.add(grayScaleImageStoreKey, image);
            }
        }
        return keyedImageStore.get(new ImageStoreKey(imageStringKey, grayScale, width, height));
    }

    public static BufferedImage createResizedCopy(Image originalImage, int scaledWidth, int scaledHeight, boolean preserveAlpha, ImageObserver imageObserver) {
        if (originalImage == null) {
            return null;
        }
        String resizedImageKey = originalImage.hashCode() + "-" + scaledWidth + "-" + scaledHeight + "-";
        ImageStoreKey resizedImageStoreKey = new ImageStoreKey(resizedImageKey, false, scaledWidth, scaledHeight);
        if (resizedImageStore.containsImage(resizedImageStoreKey)) {
            return (BufferedImage) resizedImageStore.get(resizedImageStoreKey);
        } else {
            BufferedImage scaledBI = null;
            try {
                int imageType = preserveAlpha ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
                if (scaledWidth == -1) {
                    int imageHeight = originalImage.getHeight(imageObserver);
                    int imageWidth = originalImage.getWidth(imageObserver);
                    scaledWidth = imageWidth * scaledHeight / imageHeight;
                }
                if (scaledHeight == -1) {
                    int imageHeight = originalImage.getHeight(imageObserver);
                    int imageWidth = originalImage.getWidth(imageObserver);
                    scaledHeight = imageHeight * scaledWidth / imageWidth;
                }
                scaledBI = new BufferedImage(scaledWidth, scaledHeight, imageType);
                Graphics2D g = scaledBI.createGraphics();
                if (preserveAlpha) {
                    g.setComposite(AlphaComposite.Src);
                }
                g.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null);
                g.dispose();
            } catch (Exception e) {
            }
            resizedImageStore.add(resizedImageStoreKey, scaledBI);
            return scaledBI;
        }
    }

    /*
    
     public static BufferedImage createResizedCopy(Image originalImage, int scaledWidth, int scaledHeight, boolean preserveAlpha, ImageObserver imageObserver) {
     if (originalImage == null) {
     return null;
     }
     String resizedImageKey = originalImage.hashCode() + "-" + scaledWidth + "-" + scaledHeight + "-";
     if (resizedImageStore.containsImage(resizedImageKey)) {
     return (BufferedImage) resizedImageStore.get(resizedImageKey);
     } else {
     BufferedImage scaledBI = null;
     try {
     int imageType = preserveAlpha ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
     if (scaledWidth == -1) {
     int imageHeight = originalImage.getHeight(imageObserver);
     int imageWidth = originalImage.getWidth(imageObserver);
     scaledWidth = imageWidth * scaledHeight / imageHeight;
     }
     if (scaledHeight == -1) {
     int imageHeight = originalImage.getHeight(imageObserver);
     int imageWidth = originalImage.getWidth(imageObserver);
     scaledHeight = imageHeight * scaledWidth / imageWidth;
     }
     scaledBI = new BufferedImage(scaledWidth, scaledHeight, imageType);
     Graphics2D g = scaledBI.createGraphics();
     if (preserveAlpha) {
     g.setComposite(AlphaComposite.Src);
     }
     g.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null);
     g.dispose();
     } catch (Exception e) {
     }
     resizedImageStore.add(resizedImageKey, scaledBI);
     return scaledBI;
     }
     }
    
    
     */
    public static BufferedImage combineImages(Image[] images, ImageObserver imageObserver) {
        int maxWidth = 0;
        int maxHeight = 0;
        for (Image image : images) {
            if (image.getWidth(imageObserver) > maxWidth) {
                maxWidth = image.getWidth(imageObserver);
            }
            if (image.getHeight(imageObserver) > maxHeight) {
                maxHeight = image.getHeight(imageObserver);
            }
        }
        BufferedImage combinedImage = new BufferedImage(maxWidth, maxHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics g = combinedImage.getGraphics();
        for (Image image : images) {
            g.drawImage(image, 0, 0, null);
        }
        return combinedImage;
    }

    private static void parseImagesFiles() {
        logger.info("Parsing image files...");
        parseImagesFile("config/images/images.xml");
        parseImagesFile("config/images/tileImages.xml");
        parseImagesFile("config/images/resourceImages.xml");
        parseImagesFile("config/images/vegetationImages.xml");
        parseImagesFile("config/images/unitImages.xml");
        parseImagesFile("config/images/messageImages.xml");
        parseImagesFile("config/images/colonyImprovementImages.xml");
        parseImagesFile("config/images/tileImprovementImages.xml");
        parseImagesFile("config/images/nationImages.xml");
    }

    private static void parseImagesFile(String path) {
        logger.info("Parsing image file \"" + path + "\" ...");
        DOMParser builder = new DOMParser();
        try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(ClassLoader.getSystemResourceAsStream(path));
            InputSource inputSource = new InputSource(bufferedInputStream);
            try {
                builder.parse(inputSource);
            } catch (SAXException ex) {
            } catch (IOException ex) {
            }
            Node rootNode = findNode(builder.getDocument(), "FreeMarsImageConfiguration");
            Node imagesFolderNode = findNode(rootNode, "imagesFolder");
            String imagesFolder = imagesFolderNode.getFirstChild().getNodeValue();
            Node imagesNode = findNode(rootNode, "images");
            for (Node imageNode = imagesNode.getFirstChild(); imageNode != null; imageNode = imageNode.getNextSibling()) {
                if (imageNode.getNodeType() == Node.ELEMENT_NODE) {
                    Node imageNameNode = findNode(imageNode, "name");
                    Node imagePathNode = findNode(imageNode, "path");
                    String imageName = imageNameNode.getFirstChild().getNodeValue();
                    String imagePath = imagesFolder + imagePathNode.getFirstChild().getNodeValue();
                    Image image = Toolkit.getDefaultToolkit().getImage(FreeMarsImageManager.class.getResource(imagePath));

                    KeyedImageStore.loadImage(image);
                    int width = image.getWidth(null);
                    int height = image.getHeight(null);
                    keyedImageStore.add(new ImageStoreKey(imageName, false, width, height), image);

                    StringBuilder log = new StringBuilder();
                    log.append("Added image \"");
                    log.append(imageName);
                    log.append("\" sized ");
                    log.append(width);
                    log.append("x");
                    log.append(height);
                    log.append(" from path \"");
                    log.append(imagePath);
                    log.append("\".");
                    logger.info(log);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Node findNode(Node node, String nodeName) {
        int length = node.getChildNodes().getLength();
        for (int i = 0; i < length; i++) {
            Node subNode = node.getChildNodes().item(i);
            if (subNode.getNodeType() == Node.ELEMENT_NODE) {
                if (nodeName.equals(subNode.getNodeName())) {
                    return subNode;
                }
            }
        }
        return null;
    }

    private static BufferedImage convertToGrayscale(Image source) {
        BufferedImageOp op = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
        return op.filter(toBufferedImage(source), null);
    }

    private static BufferedImage toBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        }
        image = new ImageIcon(image).getImage();
        boolean hasAlpha = hasAlpha(image);
        BufferedImage bimage = null;
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {
            int transparency = Transparency.OPAQUE;
            if (hasAlpha) {
                transparency = Transparency.BITMASK;
            }
            GraphicsDevice gs = ge.getDefaultScreenDevice();
            GraphicsConfiguration gc = gs.getDefaultConfiguration();
            bimage = gc.createCompatibleImage(
                    image.getWidth(null), image.getHeight(null), transparency);
        } catch (HeadlessException e) {
        }
        if (bimage == null) {
            int type = BufferedImage.TYPE_INT_RGB;
            if (hasAlpha) {
                type = BufferedImage.TYPE_INT_ARGB;
            }
            bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
        }
        Graphics g = bimage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return bimage;
    }

    private static boolean hasAlpha(Image image) {
        if (image instanceof BufferedImage) {
            BufferedImage bimage = (BufferedImage) image;
            return bimage.getColorModel().hasAlpha();
        }
        PixelGrabber pg = new PixelGrabber(image, 0, 0, 1, 1, false);
        try {
            pg.grabPixels();
        } catch (InterruptedException e) {
        }
        ColorModel cm = pg.getColorModel();
        return cm.hasAlpha();
    }

    private static String getImageKey(Object object) {
        String key = "";
        if (object instanceof String) {
            key = object.toString();
        } else if (object instanceof Tile) {
            key = getTileImageKey((Tile) object);
        } else if (object instanceof TileType) {
            key = getTileTypeImageKey((TileType) object);
        } else if (object instanceof TileImprovementType) {
            key = getImprovementImageKey((TileImprovementType) object);
        } else if (object instanceof FreeRealmUnitType) {
            key = getUnitTypeImageKey((FreeRealmUnitType) object);
        } else if (object instanceof Unit) {
            key = getUnitImageKey((Unit) object);
        } else if (object instanceof Settlement) {
            key = getSettlementImageKey((Settlement) object);
        } else if (object instanceof Vegetation) {
            key = getVegetationImageKey((Vegetation) object);
        } else if (object instanceof VegetationType) {
            key = getVegetationTypeImageKey((VegetationType) object);
        } else if (object instanceof Resource) {
            key = getResourceImageKey((Resource) object);
        } else if (object instanceof FreeRealmBonusResource) {
            key = getBonusResourceImageKey((FreeRealmBonusResource) object);
        } else if (object instanceof SettlementImprovementType) {
            key = getSettlementImprovementTypeImageKey((SettlementImprovementType) object);
        } else if (object instanceof SettlementImprovement) {
            key = getSettlementImprovementImageKey((SettlementImprovement) object);
        } else if (object instanceof Nation) {
            key = getNationImageKey((Nation) object);
        }
        return key;
    }

    private static String getTileTypeImageKey(TileType tileType) {
        String key = "TILE_" + tileType.getId() + "_00";
        return key;
    }

    private static String getTileImageKey(Tile tile) {
        String key;
        if (tile.getCustomProperty("imageType") != null) {
            key = "TILE_" + tile.getType().getId() + "_" + tile.getCustomProperty("imageType");
        } else {
            key = "TILE_" + tile.getType().getId() + "_00";
        }
        return key;
    }

    private static String getImprovementImageKey(TileImprovementType improvement) {
        return "IMPROVEMENT_" + improvement.getName();
    }

    private static String getUnitTypeImageKey(FreeRealmUnitType unitType) {
        return "UNIT_" + unitType.getId() + "_SW";
    }

    private static String getUnitImageKey(Unit unit) {
        String directionShortName = (String) unit.getCustomProperty("direction");
        if (directionShortName == null) {
            directionShortName = "SW";
        }
        return "UNIT_" + unit.getType().getId() + "_" + directionShortName;
    }

    private static String getVegetationTypeImageKey(VegetationType vegetationType) {
        String key = "VEGETATION_" + vegetationType.getId() + "_00";
        return key;
    }

    private static String getVegetationImageKey(Vegetation vegetation) {
        String key;
        if (vegetation.getCustomProperty("imageType") != null) {
            key = "VEGETATION_" + vegetation.getType().getId() + "_" + vegetation.getCustomProperty("imageType");
        } else {
            key = "VEGETATION_" + vegetation.getType().getId() + "_00";
        }
        return key;
    }

    private static String getResourceImageKey(Resource resource) {
        return "RESOURCE_" + resource.getId();
    }

    private static String getBonusResourceImageKey(FreeRealmBonusResource bonusResource) {
        return "BONUS_RESOURCE_" + bonusResource.getId();
    }

    private static String getSettlementImprovementTypeImageKey(SettlementImprovementType settlementImprovementType) {
        return "COLONY_IMPROVEMENT_" + settlementImprovementType.getId();
    }

    private static String getSettlementImprovementImageKey(SettlementImprovement settlementImprovement) {
        return "COLONY_IMPROVEMENT_" + settlementImprovement.getType().getId();
    }

    private static String getNationImageKey(Nation nation) {
        return "NATION_" + nation.getId();
    }

    private static String getSettlementImageKey(Settlement settlement) {
        if (settlement.getPopulation() > 1200) {
            return "COLONY_IMAGE_2";
        } else if (settlement.getPopulation() > 600) {
            return "COLONY_IMAGE_1";
        } else {
            return "COLONY_IMAGE_0";
        }
    }
}
