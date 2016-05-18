package net.orpiske.ucf.driver;

import net.orpiske.ucf.types.ConfigurationUnit;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

import java.io.File;

/**
 * Created by otavio on 4/18/16.
 */
public interface Driver {
    File getDestination();

    void addOptions(Options options);
    void eval(CommandLine commandLine);

    boolean hasNext();
    ConfigurationUnit next();

    void commit(ConfigurationUnit unit);
}
