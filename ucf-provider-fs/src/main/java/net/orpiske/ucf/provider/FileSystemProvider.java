package net.orpiske.ucf.provider;

import net.orpiske.ucf.provider.net.orpiske.ucf.provider.walker.RepositoryWalker;
import net.orpiske.ucf.types.ConfigurationSource;
import net.orpiske.ucf.types.UnitId;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

/**
 * Created by otavio on 4/19/16.
 */
public class FileSystemProvider implements Provider {
    private static final Logger logger = LoggerFactory.getLogger(FileSystemProvider.class);
    private String location;

    private List<ConfigurationSource> knownFiles = null;

    public void addOptions(Options options) {
        options.addOption("l", "location", true, "the output path for the configuration");
        options.addOption("o", "output", true, "the output path for the configuration");

    }

    public void eval(CommandLine commandLine) {
        location = commandLine.getOptionValue("location");

        if (location == null || location.isEmpty()) {
            throw new RuntimeException("The location must not be null");
        }

        if (knownFiles == null) {
            RepositoryWalker walker = new RepositoryWalker("default");
            File library = new File(location);

            knownFiles = walker.load(library);
        }

    }

    @Override
    public boolean contains(UnitId unitId) {
        if (acquire(unitId) != null) {
            return true;
        }

        return false;
    }

    @Override
    public ConfigurationSource acquire(UnitId unitId) {

        // TODO: bad bad behavior (will iterate over the array for all the files in the library)
        for (ConfigurationSource source : knownFiles) {
            logger.trace("Checking whether file {} matches the requested file {}",
                   source.getFile().getName(), unitId.getName());
            if (source.getFile().getName().equals(unitId.getName())) {
                return source;
            }
        }

        return null;
    }
}
