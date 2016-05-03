package net.orpiske.ucf.actions;

import net.orpiske.ucf.contrib.configuration.ConfigurationWrapper;
import net.orpiske.ucf.driver.Driver;
import net.orpiske.ucf.engine.ConfigurationEngine;
import net.orpiske.ucf.engine.DefaultEngine;
import net.orpiske.ucf.provider.Provider;
import net.orpiske.ucf.render.ConfigurationRender;
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
    private ConfigurationEngine engine;

    public ConfigureAction(String[] args) throws Exception {
        processCommand(args);
    }


    private Driver createDriverByName(String driverName, String className) throws Exception {
        try {
            return (Driver) Class.forName(className).newInstance();
        } catch (InstantiationException e) {
            System.err.println("Driver for " + driverName + " not found in the classpath");
            throw e;
        } catch (IllegalAccessException e) {
            System.err.println("Illegal driver " + driverName);
            throw e;
        } catch (ClassNotFoundException e) {
            System.err.println("Invalid driver " + driverName);
            throw e;
        }
    }

    private ConfigurationRender createRenderByName(String renderName, String className) throws Exception {
        try {
            return (ConfigurationRender) Class.forName(className).newInstance();
        } catch (InstantiationException e) {
            System.err.println("Driver for " + renderName + " not found in the classpath");
            throw e;
        } catch (IllegalAccessException e) {
            System.err.println("Illegal driver " + renderName);
            throw e;
        } catch (ClassNotFoundException e) {
            System.err.println("Invalid driver " + renderName);
            throw e;
        }
    }

    private Provider createProviderByName(String providerName, String className) throws Exception {
        try {
            return (Provider) Class.forName(className).newInstance();
        } catch (InstantiationException e) {
            System.err.println("Provider for " + providerName + " not found in the classpath");
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
        System.out.println("Processing arguments");
        if (args.length == 0) {
            System.err.println("Not enough parameters");

            return;
        }

        String first = args[0];
        if (first.equals("artemis")) {
            String driverName = config.getString("driver");
            String driverClass = config.getString("driver." + driverName);

            driver = createDriverByName("artemis", driverClass);
        }

        String renderName = config.getString("render");
        String renderClass = config.getString("render." + renderName);
        render = createRenderByName(renderName, renderClass);

        String providerName = config.getString("provider");
        String providerClass = config.getString("provider." + providerName);
        provider = createProviderByName(providerName, providerClass);

        engine = new DefaultEngine(driver, render, provider);

        String[] driverArgs = copyOfRange(args, 1, args.length);
        engine.processOptions(driverArgs);
    }

    public int run() {
        System.out.println("Doing deeds");
        engine.configure();

        return 0;
    }
}
