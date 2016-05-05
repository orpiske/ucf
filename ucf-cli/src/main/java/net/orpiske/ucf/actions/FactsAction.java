package net.orpiske.ucf.actions;

import net.orpiske.ucf.facter.DefaultFacter;
import net.orpiske.ucf.facter.Facter;
import org.apache.commons.cli.*;

import java.util.Map;

/**
 * Created by opiske on 5/5/16.
 */
public class FactsAction extends Action {
    private CommandLine cmdLine;
    private Options options;

    private boolean isHelp;

    public FactsAction(String[] args) throws Exception {
        processCommand(args);
    }

    @Override
    protected void processCommand(String[] args) throws Exception {
        CommandLineParser parser = new PosixParser();

        options = new Options();

        options.addOption("h", "help", false, "prints the help");

        try {
            cmdLine = parser.parse(options, args);
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            help(options, -1);
        }

        isHelp = cmdLine.hasOption("help");
    }

    @Override
    public int run() {
        Facter facter = new DefaultFacter();

        Map<String, String> facts = facter.getFacts();

        for (Map.Entry<String, String> entry : facts.entrySet()) {
            System.out.println(entry.getKey() + ": " +  entry.getValue());
        }


        return 0;
    }
}
