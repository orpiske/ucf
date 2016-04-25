package net.orpiske.ucf.engine;

import net.orpiske.ucf.types.ConfigurationSource;
import net.orpiske.ucf.types.ConfigurationUnit;
import net.orpiske.ucf.driver.Driver;
import net.orpiske.ucf.provider.Provider;
import net.orpiske.ucf.render.ConfigurationRender;
import net.orpiske.ucf.types.RenderedData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by otavio on 4/18/16.
 */
public class DefaultEngine implements ConfigurationEngine {
    private static final Logger logger = LoggerFactory.getLogger(DefaultEngine.class);
    private Driver driver;
    private ConfigurationRender configurationRender;
    private Provider provider;

    public DefaultEngine(Driver driver, ConfigurationRender configurationRender, Provider provider) {
        this.driver = driver;
        this.configurationRender = configurationRender;
        this.provider = provider;
    }

    public void configure() {
        while (driver.hasNext()) {
            ConfigurationUnit unit = driver.next();

            logger.debug("Processing {} ", unit);
            if (!provider.contains(unit.getUnitId())) {
                logger.debug("Provider does not contain {}", unit.getUnitId().getName());
                 continue;
            }

            ConfigurationSource configurationSource = provider.acquire(unit.getUnitId());
            unit.setSource(configurationSource);

            RenderedData<?> renderedData = configurationRender.render(configurationSource);
            unit.setRenderedData(renderedData);

            driver.commit(unit);
        }
    }
}
