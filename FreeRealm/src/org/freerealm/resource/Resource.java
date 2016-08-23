package org.freerealm.resource;

/**
 *
 * @author Deniz ARIKAN
 */
public class Resource implements Comparable<Resource> {

    private int id;
    private String name;
    public static final String FOOD = "Food";

    public Resource(String name) {
        setName(name);
    }

    @Override
    public String toString() {
        return name;
    }

    public int compareTo(Resource resource) {
        if (getId() < resource.getId()) {
            return -1;
        } else if (getId() > resource.getId()) {
            return 1;
        } else {
            return 0;
        }
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
