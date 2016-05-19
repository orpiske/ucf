package net.orpiske.ucf.modules;

import com.google.inject.AbstractModule;
import net.orpiske.ucf.contrib.configuration.ConfigurationWrapper;
import net.orpiske.ucf.driver.Driver;
import net.orpiske.ucf.provider.Provider;
import net.orpiske.ucf.render.ConfigurationRender;
import net.orpiske.ucf.state.StateControl;
import net.orpiske.ucf.types.Handler;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by opiske on 5/19/16.
 */
public class DefaultEngineModule extends AbstractModule {
    private static final Logger logger = LoggerFactory.getLogger(DefaultEngineModule.class);
    private static final PropertiesConfiguration config = ConfigurationWrapper.getConfig();
    private String driverName;

    public DefaultEngineModule(final String driverName) {
        this.driverName = driverName;
    }

    public <T> void dynamicBind(final Class<T> clazz, final String providerName, final String className) {
        try {
            Class<? extends T> binded = (Class<? extends T>) Class.forName(className);

            bind(clazz).to(binded);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Invalid bindable class for " + providerName);
        }
    }


    @Override
    protected void configure() {
        logger.debug("Configuring dynamic bindings");

        String renderName = config.getString("render");
        String renderClass = config.getString("render." + renderName);
        dynamicBind(ConfigurationRender.class, renderName, renderClass);

        String providerName = config.getString("provider");
        String providerClass = config.getString("provider." + providerName);
        dynamicBind(Provider.class, providerName, providerClass);

        String handlerName = config.getString("handler");
        String handlerClass = config.getString("handler." + handlerName);
        dynamicBind(Handler.class, handlerName, handlerClass);

        String scName = config.getString("state-control");
        String scClass = config.getString("state-control." + scName);
        dynamicBind(StateControl.class, scName, scClass);

        String driverClass = config.getString("driver." + driverName);
        dynamicBind(Driver.class, driverName, driverClass);
    }
}
