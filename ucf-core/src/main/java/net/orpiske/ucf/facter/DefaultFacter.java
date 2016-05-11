package net.orpiske.ucf.facter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.*;
import java.lang.management.OperatingSystemMXBean;

import static java.net.NetworkInterface.getNetworkInterfaces;

/**
 * Created by opiske on 5/5/16.
 */
public class DefaultFacter implements Facter {
    private static final Logger logger = LoggerFactory.getLogger(DefaultFacter.class);
    private static Map<String, Object> facts = null;

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

        try {
            Enumeration<NetworkInterface> ifaces = getNetworkInterfaces();
            int i = 0;
            while (ifaces.hasMoreElements()) {
                NetworkInterface iface = ifaces.nextElement();
                String ifaceDisplayName = iface.getDisplayName();

                String keyName = "net_dev_name_" + i;
                facts.put(keyName, ifaceDisplayName);


                Enumeration<InetAddress>  inetAddresses = iface.getInetAddresses();
                List<String> addresses = new ArrayList<String>();
                List<String> hostnames = new ArrayList<String>();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = inetAddresses.nextElement();

                    String address = inetAddress.getHostAddress();
                    logger.trace("Address = {}", address);
                    addresses.add(address);

                    String hostname = inetAddress.getHostName();
                    hostnames.add(hostname);
                }

                String addrKeyName = "net_dev_addr_" + i;
                facts.put(addrKeyName, addresses);

                String addrHostName = "net_dev_hostname_" + i;
                facts.put(addrHostName, hostnames);

                i++;
            }
            facts.put("net_dev_count", String.valueOf(i));
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public synchronized Map<String, Object> getFacts() {
        logger.debug("Getting the facts");

        if (facts == null) {
            facts = new HashMap<String, Object>();

            getSystemFacts();
            getHardwareInfo();
        }


        return facts;
    }
}
