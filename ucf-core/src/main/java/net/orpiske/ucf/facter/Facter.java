package net.orpiske.ucf.facter;

import java.util.Map;

/**
 * Created by opiske on 5/5/16.
 */
public interface Facter {
    Map<String,Object> getFacts();
}
