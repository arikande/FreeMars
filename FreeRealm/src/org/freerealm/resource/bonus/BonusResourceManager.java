package org.freerealm.resource.bonus;

import java.util.Iterator;
import java.util.TreeMap;

/**
 *
 * @author Deniz ARIKAN
 */
public class BonusResourceManager {

    private final TreeMap<Integer, BonusResource> bonusResource;

    public BonusResourceManager() {
        bonusResource = new TreeMap<Integer, BonusResource>();
    }

    public FreeRealmBonusResource getBonusResource(int id) {
        return (FreeRealmBonusResource) getBonusResources().get(id);
    }

    public void addBonusResource(FreeRealmBonusResource bonusResource) {
        getBonusResources().put(bonusResource.getId(), bonusResource);
    }

    public Iterator<BonusResource> getBonusResourcesIterator() {
        return getBonusResources().values().iterator();
    }

    public int getBonusResourceCount() {
        return getBonusResources().size();
    }

    private TreeMap<Integer, BonusResource> getBonusResources() {
        return bonusResource;
    }
}
