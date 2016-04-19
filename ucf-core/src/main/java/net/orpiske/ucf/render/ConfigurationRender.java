package net.orpiske.ucf.render;

import net.orpiske.ucf.types.RenderedData;
import net.orpiske.ucf.types.ConfigurationSource;

/**
 * Created by otavio on 4/18/16.
 */
public interface ConfigurationRender {
    RenderedData render(ConfigurationSource source);
}
