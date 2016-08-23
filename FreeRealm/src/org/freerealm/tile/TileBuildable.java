package org.freerealm.tile;

import java.util.Iterator;
import org.freerealm.Buildable;

/**
 *
 * @author Deniz ARIKAN
 */
public interface TileBuildable extends Buildable {

    public Iterator<TileBuildablePrerequisite> getPrerequisitesIterator();

    public void addPrerequisite(TileBuildablePrerequisite prerequisite);
}
