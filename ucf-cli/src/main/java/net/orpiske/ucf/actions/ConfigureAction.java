package net.orpiske.ucf.actions;

import net.orpiske.ucf.driver.Driver;
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

    public ConfigureAction(String[] args) {
        processCommand(args);
    }

    protected void processCommand(String[] args) {
        if (args.length == 0) {
            System.err.println("Not enough parameters");

            return;
        }

        String first = args[0];
        if (first.equals("artemis")) {
            try {
                driver = (Driver) Class.forName("net.orpiske.ucf.driver.ArtemisDriver").newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }


    }

    public int run() {

        try {
            render = (ConfigurationRender) Class.forName("net.orpiske.ucf.render.Jinja2Render").newInstance();

            render.render();
        } catch (ClassNotFoundException e) {
            System.out.println("Render not found");
        } catch (InstantiationException e) {
            e.printStackTrace();
            System.out.println("Bad render");
            return -1;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            System.out.println("Bad render");

            return -1;
        }

        return 0;
    }
}
