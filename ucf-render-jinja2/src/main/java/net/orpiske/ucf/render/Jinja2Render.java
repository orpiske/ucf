package net.orpiske.ucf.render;

import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import com.google.common.io.Resources;
import com.hubspot.jinjava.Jinjava;
import net.orpiske.ucf.render.ConfigurationRender;
import net.orpiske.ucf.types.ConfigurationSource;
import net.orpiske.ucf.types.RenderedData;
import org.apache.commons.cli.Options;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * Created by otavio on 4/18/16.
 */
public class Jinja2Render implements ConfigurationRender {
    public void addOptions(Options options) {

    }

    @Override
    public RenderedData render(ConfigurationSource source) {
        Jinjava jinjava = new Jinjava();
        Map<String, Object> context = Maps.newHashMap();
        context.put("queuename", "myqyeye");

        String path = source.getFile().getAbsolutePath();
        String template = null;
        try {
            template = FileUtils.readFileToString(source.getFile(), Charsets.UTF_8.name());
        } catch (IOException e) {
            e.printStackTrace();
        }

        String renderedTemplate = jinjava.render(template, context);
        RenderedData<String> ret = new RenderedData<>();

        ret.setConfigurationData(renderedTemplate);

        return ret;
    }
}
