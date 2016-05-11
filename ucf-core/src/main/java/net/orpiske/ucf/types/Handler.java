package net.orpiske.ucf.types;

import net.orpiske.ucf.types.exceptions.HandlerException;

import java.io.File;
import java.util.Map;

/**
 * Created by opiske on 5/10/16.
 */
public interface Handler {

    void setContext(Map<String, Object> context);
    void setInitialPath(final File file) throws HandlerException;

    void beforeConfigure()throws HandlerException;
    void afterConfigure()throws HandlerException;

    void beforeCommit()throws HandlerException;
    void afterCommit()throws HandlerException;

}
