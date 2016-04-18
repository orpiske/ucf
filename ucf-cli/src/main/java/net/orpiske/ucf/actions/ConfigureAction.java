package net.orpiske.ucf.actions;

import net.orpiske.ucf.engine.ConfigurationEngine;
import org.apache.commons.cli.*;

import static java.util.Arrays.copyOfRange;

/**
 * Created by otavio on 4/18/16.
 */
public class ConfigureAction extends Action {
    private CommandLine cmdLine;
    private Options options;

    private boolean isHelp;

    private ConfigurationEngine engine;

    public ConfigureAction(String[] args) {
        processCommand(args);
    }

    protected void processCommand(String[] args) {
        CommandLineParser parser = new PosixParser();

        options = new Options();

        options.addOption("h", "help", false, "prints the help");
        options.addOption("n", "name", true, "repository name");
        options.addOption("u", "url", true, "repository url");

        try {
            cmdLine = parser.parse(options, args);
        } catch (ParseException e) {
            help(options, -1);
        }

        isHelp = cmdLine.hasOption("help");

        String name = cmdLine.getOptionValue('n');
        if (name == null) {
            help(options, -1);
        }

    }

    public int run() {

        try {
            engine = (ConfigurationEngine) Class.forName("net.orpiske.ucf.engine.Jinja2Engine").newInstance();

            engine.configure();
        } catch (ClassNotFoundException e) {
            System.out.println("Driver not found");
        } catch (InstantiationException e) {
            e.printStackTrace();
            System.out.println("Bad driver");
            return -1;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            System.out.println("Bad driver");

            return -1;
        }

        return 0;
    }
}
