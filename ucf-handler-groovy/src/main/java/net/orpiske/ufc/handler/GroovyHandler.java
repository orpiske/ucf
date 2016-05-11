package net.orpiske.ufc.handler;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import net.orpiske.ucf.types.Handler;
import net.orpiske.ucf.types.exceptions.HandlerException;
import net.orpiske.ufc.handler.contrib.GroovyCallbackWalker;
import net.orpiske.ufc.handler.contrib.GroovyClasspathHelper;
import org.codehaus.groovy.control.CompilationFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by opiske on 5/10/16.
 */
public class GroovyHandler implements Handler {
    private static final Logger logger = LoggerFactory.getLogger(GroovyHandler.class);

    private Map<String, Object> context;
    private List<File> fileList;

    private GroovyObject getObject(final File file) throws HandlerException {
        GroovyClasspathHelper classpathHelper = GroovyClasspathHelper.getInstance();
        GroovyClassLoader loader = classpathHelper.getLoader();

        // Parses the class
        Class<?> groovyClass;
        try {
            groovyClass = loader.parseClass(file);
        } catch (CompilationFailedException e) {
            throw new HandlerException("The script has errors: " + e.getMessage(),
                    e);
        } catch (IOException e) {
            throw new HandlerException("Input/output error: " + e.getMessage(),
                    e);
        }

        // Instantiate the object
        GroovyObject groovyObject;
        try {
            groovyObject = (GroovyObject) groovyClass.newInstance();
        } catch (InstantiationException e) {
            throw new HandlerException("Unable to instantiate object: "
                    + e.getMessage(), e);
        } catch (IllegalAccessException e) {
            throw new HandlerException("Illegal access: " + e.getMessage(),
                    e);
        }

        return groovyObject;
    }

    public void setContext(Map<String, Object> context) {
        this.context = context;
    }

    private void runCallback(final File file, String callbackName) throws HandlerException {
        GroovyObject groovyObject = getObject(file);

        groovyObject.invokeMethod(callbackName, context);
    }


    public void beforeCommit() throws HandlerException {
        for (File file : fileList) {
            runCallback(file, "beforeCommit");
        }

    }

    public void afterCommit() throws HandlerException {
        for (File file : fileList) {
            runCallback(file, "afterCommit");
        }
    }

    public void beforeConfigure() throws HandlerException {
        for (File file : fileList) {
            runCallback(file, "beforeConfigure");
        }

    }

    public void afterConfigure() throws HandlerException {
        for (File file : fileList) {
            runCallback(file, "afterConfigure");
        }
    }


    public void setInitialPath(final File initialPath) throws HandlerException {
        GroovyCallbackWalker walker = new GroovyCallbackWalker();
        logger.debug("Processing {}", initialPath.getAbsolutePath());

         fileList = walker.load(initialPath);
    }
}
