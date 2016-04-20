package net.orpiske.ucf.driver;

import net.orpiske.ucf.types.ConfigurationUnit;
import net.orpiske.ucf.utils.Constants;
import org.apache.commons.cli.*;

import java.util.ArrayList;

import static java.util.Arrays.copyOfRange;

/**
 * Created by otavio on 4/18/16.
 */
public class ArtemisDriver implements Driver {
    private static ArrayList<String> units = new ArrayList<>();

    private CommandLine cmdLine;
    private Options options;

    private boolean isHelp;

    static {
        units.add("etc/broker.xml");
    }

    @Override
    public void processOptions(String[] args) {
        CommandLineParser parser = new PosixParser();

        if (args.length == 0) {
            CliUtil.help(options, -1);
        }

        options = new Options();

        options.addOption("h", "help", false, "prints the help");
        options.addOption("o", "output", true, "the output path for the configuration");

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
    public boolean hasNext() {
        return false;
    }

    @Override
    public ConfigurationUnit next() {
        return null;
    }

    @Override
    public void commit(ConfigurationUnit unit) {

    }

    @Override
    public int run() {
        return 0;
    }
}
