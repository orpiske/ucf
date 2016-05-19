package net.orpiske.ucf.actions;

import com.google.inject.Guice;
import com.google.inject.Injector;
import net.orpiske.ucf.engine.ConfigurationEngine;
import net.orpiske.ucf.engine.DefaultEngine;
import net.orpiske.ucf.modules.DefaultEngineModule;

import static java.util.Arrays.copyOfRange;

/**
 * Created by otavio on 4/18/16.
 */
public class ConfigureAction extends Action {
    private ConfigurationEngine engine;

    public ConfigureAction(String[] args) throws Exception {
        processCommand(args);
    }


    protected void processCommand(String[] args) throws Exception {
        if (args.length == 0) {
            System.err.println("Not enough parameters");

            return;
        }

        String driverName = args[0];

        Injector injector = Guice.createInjector(new DefaultEngineModule(driverName));
        engine = injector.getInstance(DefaultEngine.class);

        String[] progArgs = copyOfRange(args, 1, args.length);
        engine.processOptions(progArgs);
    }

    public int run() {
        engine.configure();

        return 0;
    }
}
