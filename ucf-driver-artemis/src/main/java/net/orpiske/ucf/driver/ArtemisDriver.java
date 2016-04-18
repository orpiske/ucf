package net.orpiske.ucf.driver;

import net.orpiske.ucf.utils.Constants;
import org.apache.commons.cli.*;

import static java.util.Arrays.copyOfRange;

/**
 * Created by otavio on 4/18/16.
 */
public class ArtemisDriver implements Driver {
    private CommandLine cmdLine;
    private Options options;

    private boolean isHelp;

    @Override
    public void processOptions(String[] args) {
        CommandLineParser parser = new PosixParser();

        if (args.length == 0) {
            CliUtil.help(options, -1);
        }

        options = new Options();

        try {
            cmdLine = parser.parse(options, args);
        } catch (ParseException e) {
            CliUtil.help(options, -1);
        }

        isHelp = cmdLine.hasOption("help");

        String name = cmdLine.getOptionValue('n');
        if (name == null) {
            CliUtil.help(options, -1);
        }

    }

    @Override
    public int run() {
        return 0;
    }
}
