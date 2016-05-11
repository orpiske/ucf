package net.orpiske.ucf.actions;

import net.orpiske.ucf.contrib.configuration.ConfigurationWrapper;
import net.orpiske.ucf.driver.Driver;
import net.orpiske.ucf.engine.ConfigurationEngine;
import net.orpiske.ucf.engine.DefaultEngine;
import net.orpiske.ucf.provider.Provider;
import net.orpiske.ucf.render.ConfigurationRender;
import net.orpiske.ucf.types.Handler;
import org.apache.commons.cli.*;
import org.apache.commons.configuration.PropertiesConfiguration;

import static java.util.Arrays.copyOfRange;

/**
 * Created by otavio on 4/18/16.
 */
public class ConfigureAction extends Action {
    private static final PropertiesConfiguration config = ConfigurationWrapper.getConfig();

    private CommandLine cmdLine;
    private Options options;

    private boolean isHelp;

    private ConfigurationRender render;
    private Driver driver;
    private Provider provider;
    private Handler handler;
    private ConfigurationEngine engine;

    public ConfigureAction(String[] args) throws Exception {
        processCommand(args);
    }


    private <T> T createByName(Class<T> clazz, String providerName, String className) throws Exception {
        try {
            return (T) Class.forName(className).newInstance();
        } catch (InstantiationException e) {
            System.err.println("Class for " + providerName + " not found in the classpath");
            throw e;
        } catch (IllegalAccessException e) {
            System.err.println("Illegal provider " + providerName);
            throw e;
        } catch (ClassNotFoundException e) {
            System.err.println("Invalid provider " + providerName);
            throw e;
        }
    }

    protected void processCommand(String[] args) throws Exception {
        if (args.length == 0) {
            System.err.println("Not enough parameters");

            return;
        }

        String first = args[0];
        if (first.equals("artemis")) {
            String driverName = config.getString("driver");
            String driverClass = config.getString("driver." + driverName);

            driver = createByName(Driver.class, "artemis", driverClass);
        }

        String renderName = config.getString("render");
        String renderClass = config.getString("render." + renderName);
        render = createByName(ConfigurationRender.class, renderName, renderClass);

        String providerName = config.getString("provider");
        String providerClass = config.getString("provider." + providerName);
        provider = createByName(Provider.class, providerName, providerClass);

        String handlerName = config.getString("handler");
        String handlerClass = config.getString("handler." + handlerName);
        handler = createByName(Handler.class, handlerName, handlerClass);

        engine = new DefaultEngine(driver, render, provider, handler);

        String[] driverArgs = copyOfRange(args, 1, args.length);
        engine.processOptions(driverArgs);
    }

    public int run() {
        engine.configure();

        return 0;
    }
}
