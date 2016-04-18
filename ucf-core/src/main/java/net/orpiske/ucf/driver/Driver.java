package net.orpiske.ucf.driver;

/**
 * Created by otavio on 4/18/16.
 */
public interface Driver {

    void processOptions(String[] args);
    int run();
}
