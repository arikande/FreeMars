package org.freerealm.tile;

import org.freerealm.Realm;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public interface Collectable {

    public String getName();

    public void collected(Realm realm, Unit unit);
}
