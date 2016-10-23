package net.orpiske.ucf.state;

import net.orpiske.ucf.contrib.configuration.ConfigurationWrapper;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 * Created by otavio on 10/23/16.
 */
public final class StateControlFactory {
    private static final PropertiesConfiguration config = ConfigurationWrapper.getConfig();

    public static StateControl newInstance() throws Exception {
        String scName = config.getString("state-control");
        String scClass = config.getString("state-control." + scName);


        return (StateControl) Class.forName(scClass).newInstance();
    }
}
