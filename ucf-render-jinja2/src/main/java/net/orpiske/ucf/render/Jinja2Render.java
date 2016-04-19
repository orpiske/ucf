package net.orpiske.ucf.render;

import net.orpiske.ucf.render.ConfigurationRender;
import net.orpiske.ucf.types.ConfigurationSource;
import net.orpiske.ucf.types.RenderedData;

/**
 * Created by otavio on 4/18/16.
 */
public class Jinja2Render implements ConfigurationRender {

    @Override
    public RenderedData render(ConfigurationSource source) {
        System.out.println("Hey, I am rendering :)");
        return null;
    }
}
