package net.orpiske.ucf.state;

/**
 * Created by otavio on 10/23/16.
 */
public class RestorePointer {
    private String path;
    private String id;

    public RestorePointer(String path, String id) {
        this.path = path;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
