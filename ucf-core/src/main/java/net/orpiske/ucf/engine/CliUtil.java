package net.orpiske.ucf.engine;

import net.orpiske.ucf.utils.Constants;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

/**
 * Created by otavio on 4/18/16.
 */
public class CliUtil {

    public static void help(final Options options, int code) {
        HelpFormatter formatter = new HelpFormatter();

        formatter.printHelp(Constants.BIN_NAME + " <action name> <driver name>", options);
        System.exit(code);
    }
}
