package net.orpiske.ucf.types;

import java.io.File;

/**
 * Created by otavio on 5/2/16.
 */
public class Target {
    private String path;
    private String name;

    public Target(String path, String name) {
        this.path = path;
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getFullPath() {
        return path + File.separator + name;
    }

    public static Target build(String path, String name) {
        return new Target(path, name);
    }
}
