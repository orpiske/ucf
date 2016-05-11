package net.orpiske.ucf.render;

import net.orpiske.ucf.types.RenderedData;
import net.orpiske.ucf.types.ConfigurationSource;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

import java.util.Map;

/**
 * Created by otavio on 4/18/16.
 */
public interface ConfigurationRender {
    void addOptions(Options options);
    void eval(CommandLine commandLine);

    Map<String, Object> getContext();
    RenderedData render(ConfigurationSource source);
}
