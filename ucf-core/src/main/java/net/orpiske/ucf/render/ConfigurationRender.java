package net.orpiske.ucf.render;

import net.orpiske.ucf.types.RenderedData;
import net.orpiske.ucf.types.ConfigurationSource;
import org.apache.commons.cli.Options;

/**
 * Created by otavio on 4/18/16.
 */
public interface ConfigurationRender {
    void addOptions(Options options);
    RenderedData render(ConfigurationSource source);
}
