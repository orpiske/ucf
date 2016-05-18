package net.orpiske.ucf.state;

import net.orpiske.ucf.types.ConfigurationUnit;

import java.io.File;

/**
 * Created by opiske on 5/16/16.
 */
public interface StateControl {

    void track(final File repository) throws Exception;
    void save(final File file, final ConfigurationUnit unit) throws Exception;
    void restore(final File file) throws Exception;
}
