package net.orpiske.ucf.driver;

import net.orpiske.ucf.types.ConfigurationUnit;
import net.orpiske.ucf.types.Target;
import net.orpiske.ucf.types.UnitId;
import net.orpiske.ucf.utils.io.Committer;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

import java.util.ArrayList;

/**
 * Created by opiske on 5/5/16.
 */
public abstract class AbstractDriver  implements Driver {
    private ArrayList<Target> units;

    private int current = 0;
    private String destination;

    protected AbstractDriver(ArrayList<Target> units) {
        this.units = units;
    }


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
}
