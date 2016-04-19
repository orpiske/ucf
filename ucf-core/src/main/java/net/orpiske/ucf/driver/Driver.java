package net.orpiske.ucf.driver;

import net.orpiske.ucf.types.ConfigurationUnit;

/**
 * Created by otavio on 4/18/16.
 */
public interface Driver {

    boolean hasNext();
    ConfigurationUnit next();

    void commit(ConfigurationUnit unit);

    void processOptions(String[] args);
    int run();
}
