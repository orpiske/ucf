package net.orpiske.ucf.provider;

import net.orpiske.ucf.types.ConfigurationSource;
import net.orpiske.ucf.types.UnitId;

/**
 * Created by otavio on 4/19/16.
 */
public class FileSystemProvider implements Provider {
    @Override
    public boolean contains(UnitId unitId) {
        return false;
    }

    @Override
    public ConfigurationSource acquire(UnitId unitId) {
        return null;
    }
}
