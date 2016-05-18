package net.orpiske.ucf.utils.io;

import net.orpiske.ucf.types.ConfigurationUnit;
import net.orpiske.ucf.types.Target;
import net.orpiske.ucf.types.UnitId;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by otavio on 5/2/16.
 */
public final class Committer {
    public static void commit(ConfigurationUnit unit, String destination) {
        File outFile = unit.resolveDestination(destination);

        try {
            FileUtils.writeStringToFile(outFile, (String) unit.getRenderedData().getConfigurationData());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
