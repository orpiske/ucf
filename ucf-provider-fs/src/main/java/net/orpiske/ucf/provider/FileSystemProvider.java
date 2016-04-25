package net.orpiske.ucf.provider;

import net.orpiske.ucf.types.ConfigurationSource;
import net.orpiske.ucf.types.UnitId;
import org.apache.commons.cli.Options;

/**
 * Created by otavio on 4/19/16.
 */
public class FileSystemProvider implements Provider {
    private Options optionsRef;

    public void addOptions(Options options) {
        options.addOption("l", "location", true, "the output path for the configuration");
        options.addOption("o", "output", true, "the output path for the configuration");

        optionsRef = options;
    }

    @Override
    public boolean contains(UnitId unitId) {
        return false;
    }

    @Override
    public ConfigurationSource acquire(UnitId unitId) {
        return null;
    }
}
