package net.orpiske.ucf.driver;

import net.orpiske.ucf.engine.CliUtil;
import net.orpiske.ucf.types.ConfigurationUnit;
import net.orpiske.ucf.types.UnitId;
import org.apache.commons.cli.*;

import java.util.ArrayList;

import static java.util.Arrays.copyOfRange;

/**
 * Created by otavio on 4/18/16.
 */
public class ArtemisDriver implements Driver {
    private static ArrayList<String> units = new ArrayList<>();

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
    public boolean hasNext() {
        return current < units.size();
    }


    public void addOptions(Options options) {
        options.addOption("version", true, "the Artemis version");
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
