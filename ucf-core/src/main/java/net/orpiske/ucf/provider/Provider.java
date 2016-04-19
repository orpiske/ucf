package net.orpiske.ucf.provider;

import net.orpiske.ucf.types.ConfigurationSource;
import net.orpiske.ucf.types.ConfigurationUnit;
import net.orpiske.ucf.types.UnitId;

/**
 * Created by otavio on 4/19/16.
 */
public interface Provider {

    boolean contains(final UnitId unitId);
    ConfigurationSource acquire(final UnitId unitId);

}
