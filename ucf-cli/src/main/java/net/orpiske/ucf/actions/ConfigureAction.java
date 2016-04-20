package net.orpiske.ucf.actions;

import net.orpiske.ucf.driver.Driver;
import net.orpiske.ucf.engine.ConfigurationEngine;
import net.orpiske.ucf.engine.DefaultEngine;
import net.orpiske.ucf.provider.Provider;
import net.orpiske.ucf.render.ConfigurationRender;
import org.apache.commons.cli.*;

import static java.util.Arrays.copyOfRange;

/**
 * Created by otavio on 4/18/16.
 */
public class ConfigureAction extends Action {
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
            driver = createDriverByName("artemis", "net.orpiske.ucf.driver.ArtemisDriver");
        }

        String[] driverArgs = copyOfRange(args, 1, args.length);
        driver.processOptions(driverArgs);

        render = createRenderByName("Jinja2", "net.orpiske.ucf.render.Jinja2Render");
        provider = createProviderByName("Default", "net.orpiske.ucf.provider.FileSystemProvider");

        engine = new DefaultEngine(driver, render, provider);
    }

    public int run() {
        System.out.println("Doing deeds");
        engine.configure();

        return 0;
    }
}
