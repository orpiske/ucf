package net.orpiske.ucf.render;

import net.orpiske.ucf.render.ConfigurationRender;
import net.orpiske.ucf.types.ConfigurationSource;
import net.orpiske.ucf.types.RenderedData;
import org.apache.commons.cli.Options;

/**
 * Created by otavio on 4/18/16.
 */
public class Jinja2Render implements ConfigurationRender {
    public void addOptions(Options options) {

    }

    @Override
    public RenderedData render(ConfigurationSource source) {
        System.out.println("Hey, I am rendering :)");
        return null;
    }
}
