package net.orpiske.ucf.types;

import java.io.File;

/**
 * Created by otavio on 4/19/16.
 */
public class ConfigurationSource {
    private String path;
    private File file;
    private File cbDir;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public File getCbDir() {
        return cbDir;
    }

    public void setCbDir(File cbDir) {
        this.cbDir = cbDir;
    }
}
