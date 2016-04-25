package net.orpiske.ucf.engine;

import net.orpiske.ucf.types.ConfigurationSource;
import net.orpiske.ucf.types.ConfigurationUnit;
import net.orpiske.ucf.driver.Driver;
import net.orpiske.ucf.provider.Provider;
import net.orpiske.ucf.render.ConfigurationRender;
import net.orpiske.ucf.types.RenderedData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.cli.*;

/**
 * Created by otavio on 4/18/16.
 */
public class DefaultEngine implements ConfigurationEngine {
    private static final Logger logger = LoggerFactory.getLogger(DefaultEngine.class);
    private Driver driver;
    private ConfigurationRender configurationRender;
    private Provider provider;

    private boolean isHelp;
    private CommandLine cmdLine;
    private Options options;

    public DefaultEngine(Driver driver, ConfigurationRender configurationRender, Provider provider) {
        this.driver = driver;
        this.configurationRender = configurationRender;
        this.provider = provider;
    }

    public void processOptions(String[] args) {
        CommandLineParser parser = new PosixParser();

        options = new Options();
        // Global options
        options.addOption("h", "help", false, "prints the help");

        // Sub-component-specific options
        driver.addOptions(options);
        provider.addOptions(options);
        configurationRender.addOptions(options);

        if (args.length == 0) {
            CliUtil.help(options, -1);
        }

        try {
            cmdLine = parser.parse(options, args);
        } catch (ParseException e) {
            CliUtil.help(options, -1);
        }

        isHelp = cmdLine.hasOption("help");
    }

    public void configure() {
        if (isHelp) {
            CliUtil.help(options, 0);
        }

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
