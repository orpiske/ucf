package net.orpiske.ucf.driver;

import net.orpiske.ucf.types.ConfigurationUnit;
import net.orpiske.ucf.types.Target;
import net.orpiske.ucf.types.UnitId;

import net.orpiske.ucf.utils.io.Committer;
import org.apache.commons.cli.*;
import java.util.ArrayList;


/**
 * Created by otavio on 4/18/16.
 */
public class ArtemisDriver implements Driver {
    private static ArrayList<Target> units = new ArrayList<>();

    private int current = 0;
    private String destination;

    static {
        units.add(Target.build("etc", "artemis.profile"));
        units.add(Target.build("etc", "artemis-roles.properties"));
        units.add(Target.build("etc", "artemis-users.properties"));
        units.add(Target.build("etc", "bootstrap.xml"));
        units.add(Target.build("etc", "broker.xml"));
        units.add(Target.build("etc", "logging.properties"));
        units.add(Target.build("etc", "login.config"));
    }


    @Override
    public boolean hasNext() {
        return current < units.size();
    }


    public void addOptions(Options options) {
        options.addOption("version", true, "the Artemis version");
        options.addOption("d", "destination", true, "the destination path where to save the rendered data");
    }

    public void eval(CommandLine commandLine) {
        destination = commandLine.getOptionValue('d');
        if (destination == null || destination.isEmpty()) {
            throw new RuntimeException("The destination must not be null");
        }
    }


    @Override
    public ConfigurationUnit next() {
        if (hasNext()) {
            ConfigurationUnit ret = new ConfigurationUnit();

            UnitId unitId = new UnitId();
            Target target = units.get(current);

            unitId.setName(target.getName());
            unitId.setTarget(target);

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
        Committer.commit(unit, destination);
    }

    @Override
    public int run() {
        return 0;
    }
}
