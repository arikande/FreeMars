package org.freerealm.xmlwrapper;

/**
 *
 * @author Deniz ARIKAN
 */
public class TagData {

    private final String id;
    private final String className;
    private final String xMLConverterName;

    public TagData(String id, String className, String xMLConverterName) {
        this.id = id;
        this.className = className;
        this.xMLConverterName = xMLConverterName;
    }

    public String getId() {
        return id;
    }

    public String getClassName() {
        return className;
    }

    public String getXMLConverterName() {
        return xMLConverterName;
    }
}
