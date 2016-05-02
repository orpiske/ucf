package net.orpiske.ucf.types;

import net.orpiske.ucf.contrib.version.Version;

/**
 * Created by otavio on 4/19/16.
 */
public class UnitId {
    private String name;
    private Version version;
    private Target target;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Target getTarget() {
        return target;
    }

    public void setTarget(Target target) {
        this.target = target;
    }

    @Override
    public String toString() {
        return "UnitId{" +
                "name='" + name + '\'' +
                '}';
    }
}
