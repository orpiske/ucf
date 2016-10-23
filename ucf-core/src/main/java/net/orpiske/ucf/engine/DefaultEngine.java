package net.orpiske.ucf.engine;

import net.orpiske.ucf.driver.Driver;
import net.orpiske.ucf.provider.Provider;
import net.orpiske.ucf.render.ConfigurationRender;
import net.orpiske.ucf.state.StateControl;
import net.orpiske.ucf.types.ConfigurationSource;
import net.orpiske.ucf.types.ConfigurationUnit;
import net.orpiske.ucf.types.Handler;
import net.orpiske.ucf.types.RenderedData;
import net.orpiske.ucf.types.exceptions.HandlerException;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.File;
import java.util.Map;

/**
 * Created by otavio on 4/18/16.
 */
public class DefaultEngine implements ConfigurationEngine {
    private static final Logger logger = LoggerFactory.getLogger(DefaultEngine.class);

    @Inject private Driver driver;
    @Inject private ConfigurationRender configurationRender;
    @Inject private Provider provider;
    @Inject private StateControl stateControl;
    @Inject private Handler handler;

    private CommandLine cmdLine;
    private Options options;

    private boolean isHelp;
    private String comment;

    public DefaultEngine() {}

    public DefaultEngine(Driver driver, ConfigurationRender configurationRender, Provider provider, Handler handler,
                         StateControl stateControl) {
        this.driver = driver;
        this.configurationRender = configurationRender;
        this.provider = provider;
        this.handler = handler;
        this.stateControl = stateControl;
    }

    public void processOptions(String[] args) {
        CommandLineParser parser = new PosixParser();

        options = new Options();
        // Global options
        options.addOption("h", "help", false, "prints the help");
        options.addOption("c", "comment", true, "the comment to use when saving the configuration state");

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
        comment = cmdLine.getOptionValue("comment");

        if (!isHelp) {
            provider.eval(cmdLine);
            driver.eval(cmdLine);

            configurationRender.eval(cmdLine);
        }

        Map<String, Object> context = configurationRender.getContext();
        handler.setContext(context);
    }

    public void configure() {
        if (isHelp) {
            CliUtil.help(options, 0);
        }

        File destination = driver.getDestination();
        try {
            stateControl.initTracking(destination);
        } catch (Exception e) {
            e.printStackTrace();
        }


        while (driver.hasNext()) {
            ConfigurationUnit unit = driver.next();

            logger.trace("Processing {} ", unit);
            if (!provider.contains(unit.getUnitId())) {
                logger.trace("Provider does not contain {}", unit.getUnitId().getName());
                continue;
            }

            ConfigurationSource configurationSource = provider.acquire(unit.getUnitId());
            unit.setSource(configurationSource);
            try {
                File cbDir = unit.getSource().getCbDir();

                handler.setInitialPath(cbDir);
            } catch (HandlerException e) {
                e.printStackTrace();
            }

            RenderedData<?> renderedData = configurationRender.render(configurationSource);

            logger.trace("Rendered template: \n{}\n", renderedData.getConfigurationData());
            unit.setRenderedData(renderedData);

            try {
                handler.beforeCommit();
            } catch (HandlerException e) {
                e.printStackTrace();
            }

            driver.commit(unit);
            try {
                String path = destination.getPath();
                File dest = unit.resolveDestination(path);

                stateControl.track(dest, unit);
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                handler.afterCommit();
            } catch (HandlerException e) {
                e.printStackTrace();
            }
        }

        try {
            if (comment == null) {
                stateControl.save("Updated configuration");
            }
            else {
                stateControl.save(comment);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
