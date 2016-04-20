package net.orpiske.ucf.driver;

import net.orpiske.ucf.types.ConfigurationUnit;
import net.orpiske.ucf.types.UnitId;
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

    private int current = 0;

    static {
        units.add("etc/artemis.profile");
        units.add("etc/artemis-roles.properties");
        units.add("etc/artemis-users.properties");
        units.add("etc/bootstrap.xml");
        units.add("etc/broker.xml");
        units.add("etc/logging.properties");
        units.add("etc/login.config");
    }

    @Override
    public void processOptions(String[] args) {
        CommandLineParser parser = new PosixParser();

        options = new Options();

        options.addOption("h", "help", false, "prints the help");
        options.addOption("o", "output", true, "the output path for the configuration");

        if (args.length == 0) {
            CliUtil.help(options, -1);
        }

        try {
            cmdLine = parser.parse(options, args);
        } catch (ParseException e) {
            CliUtil.help(options, -1);
        }

        isHelp = cmdLine.hasOption("help");
    }

    @Override
    public boolean hasNext() {
        return current < units.size();
    }

    @Override
    public ConfigurationUnit next() {
        if (hasNext()) {
            ConfigurationUnit ret = new ConfigurationUnit();

            UnitId unitId = new UnitId();

            unitId.setName(units.get(current));
            ret.setUnitId(unitId);

            current++;
            return ret;
        }

        // Hopefully, will never happen :)
        // TODO: throw an exception in the future
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
