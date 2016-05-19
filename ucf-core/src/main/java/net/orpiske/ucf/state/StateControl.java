package net.orpiske.ucf.state;

import net.orpiske.ucf.types.ConfigurationUnit;

import java.io.File;

/**
 * Created by opiske on 5/16/16.
 */
public interface StateControl {

    void initTracking(final File repository) throws Exception;

    void track(final File file, final ConfigurationUnit unit) throws Exception;
    void save(final String comment) throws Exception;
    void restore(final File file) throws Exception;
}
