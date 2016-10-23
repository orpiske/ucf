package net.orpiske.ucf.actions;

import net.orpiske.ucf.engine.CliUtil;
import net.orpiske.ucf.engine.DefaultEngine;
import net.orpiske.ucf.state.RestorePointer;
import net.orpiske.ucf.state.StateControl;
import net.orpiske.ucf.state.StateControlFactory;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.File;

import static java.util.Arrays.copyOfRange;

/**
 * Created by otavio on 10/22/16.
 */
public class StateAction extends Action {
    private enum SubAction {
        SAVE,
        RESTORE
    };

    private static final Logger logger = LoggerFactory.getLogger(DefaultEngine.class);

    @Inject private StateControl stateControl;

    private CommandLine cmdLine;
    private Options options;

    private boolean isHelp;
    private SubAction subAction;

    private String comment;
    private String location;
    private String id;

    public StateAction(String[] args) throws Exception {
        stateControl = StateControlFactory.newInstance();

        processCommand(args);
    }

    public void processRestoreOptions(String[] args) {
        CommandLineParser parser = new PosixParser();

        options = new Options();
        options.addOption("h", "help", false, "prints the help");

        options.addOption("l", "location", true, "the path to where the rendered files are stored");
        options.addOption("i", "id", true, "the commit ID to restore");

        if (args.length == 0) {
            CliUtil.help(options, -1);
        }

        try {
            cmdLine = parser.parse(options, args);
        } catch (ParseException e) {
            CliUtil.help(options, -1);
        }

        isHelp = cmdLine.hasOption("help");

        location = cmdLine.getOptionValue("location");
        id = cmdLine.getOptionValue("id");
    }

    public void processSaveOptions(String[] args) {
        CommandLineParser parser = new PosixParser();

        options = new Options();
        // Global options
        options.addOption("h", "help", false, "prints the help");

        options.addOption("l", "location", true, "the path to where the rendered files are stored");
        options.addOption("c", "comment", true,
                "the comment to use when saving the configuration state");


        if (args.length == 0) {
            CliUtil.help(options, -1);
        }

        try {
            cmdLine = parser.parse(options, args);
        } catch (ParseException e) {
            CliUtil.help(options, -1);
        }

        isHelp = cmdLine.hasOption("help");

        location = cmdLine.getOptionValue("location");
        comment = cmdLine.getOptionValue("comment");
    }

    @Override
    protected void processCommand(String[] args) {

        if (args.length == 0) {
            System.err.println("Not enough parameters");

            return;
        }

        String first = args[0];
        String[] newArgs = copyOfRange(args, 1, args.length);

        if (first.equals("restore")) {
            subAction = SubAction.RESTORE;
            processRestoreOptions(newArgs);
        }
        else {
            if (first.equals("save")) {
                subAction = SubAction.SAVE;
                processSaveOptions(newArgs);
            }
            else {
                System.err.println("Not enough parameters: please inform either add or update");

                return;
            }
        }
    }

    public int run() {
        try {
            if (isHelp) {
                help(options, 1);
            }
            else {
                stateControl.open(new File(location));

                if (subAction == SubAction.RESTORE) {
                    RestorePointer pointer = new RestorePointer(location, id);

                    stateControl.restore(pointer);
                }
                else {
                    stateControl.save(comment);
                }
            }

            return 0;
        } catch (Exception e) {
            System.err.println("Unable to save/restore state: " + e.getMessage());

            if (logger.isDebugEnabled()) {
                logger.error("Unable to save/restore state: " + e.getMessage(), e);
            }

            return 1;
        }
    }

}
