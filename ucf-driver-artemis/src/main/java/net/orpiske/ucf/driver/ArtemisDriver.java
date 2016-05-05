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
public class ArtemisDriver extends AbstractDriver {
    private static final ArrayList<Target> units = new ArrayList<>();

    static {
        units.add(Target.build("etc", "artemis.profile"));
        units.add(Target.build("etc", "artemis-roles.properties"));
        units.add(Target.build("etc", "artemis-users.properties"));
        units.add(Target.build("etc", "bootstrap.xml"));
        units.add(Target.build("etc", "broker.xml"));
        units.add(Target.build("etc", "logging.properties"));
        units.add(Target.build("etc", "login.config"));
    }

    public ArtemisDriver() {
        super(units);
    }
}
