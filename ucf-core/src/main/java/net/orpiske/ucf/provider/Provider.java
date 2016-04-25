package net.orpiske.ucf.provider;

import net.orpiske.ucf.types.ConfigurationSource;
import net.orpiske.ucf.types.ConfigurationUnit;
import net.orpiske.ucf.types.UnitId;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

/**
 * Created by otavio on 4/19/16.
 */
public interface Provider {

    void addOptions(Options options);
    void eval(CommandLine commandLine);

    boolean contains(final UnitId unitId);
    ConfigurationSource acquire(final UnitId unitId);

}
