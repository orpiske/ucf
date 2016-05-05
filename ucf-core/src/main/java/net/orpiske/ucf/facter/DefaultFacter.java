package net.orpiske.ucf.facter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;
import java.lang.management.OperatingSystemMXBean;

/**
 * Created by opiske on 5/5/16.
 */
public class DefaultFacter implements Facter {
    private static final Logger logger = LoggerFactory.getLogger(DefaultFacter.class);
    private Map<String, String> facts = new HashMap<String, String>();

    public DefaultFacter() {

    }

    private void getSystemProperty(String propertyName) {
        String propertyValue = System.getProperty(propertyName);

        facts.put(propertyName.replaceAll("\\.", "_"), propertyValue);
    }

    private void getSystemFacts() {
        String[] properties = {
                "file.separator",
                "java.home",
                "java.vendor",
                "java.vendor.url",
                "java.version",
                "line.separator",
                "os.arch",
                "os.name",
                "os.version",
                "path.separator",
                "user.dir",
                "user.home",
                "user.name"
        };

        for (String property : properties) {
            getSystemProperty(property);
        }
    }

    private void getHardwareInfo() {
        int cpus = ManagementFactory.getOperatingSystemMXBean().getAvailableProcessors();
        facts.put("sys_cpus", String.valueOf(cpus));

        long maxMemory = Runtime.getRuntime().maxMemory();
        facts.put("sys_max_memory", String.valueOf(maxMemory));
    }

    public Map<String, String> getFacts() {
        logger.debug("Getting the facts");

        getSystemFacts();
        getHardwareInfo();
        return facts;
    }
}
