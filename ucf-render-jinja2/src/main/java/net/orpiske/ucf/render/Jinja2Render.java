package net.orpiske.ucf.render;

import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import com.google.common.io.Resources;
import com.hubspot.jinjava.Jinjava;
import net.orpiske.ucf.render.ConfigurationRender;
import net.orpiske.ucf.types.ConfigurationSource;
import net.orpiske.ucf.types.RenderedData;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by otavio on 4/18/16.
 */
public class Jinja2Render implements ConfigurationRender {
    private static final Logger logger = LoggerFactory.getLogger(Jinja2Render.class);

    private Map<String, Object> context = null;

    public void addOptions(Options options) {
        Option define  = Option.builder("D").argName("property=value")
                .hasArgs()
                .valueSeparator()
                .desc("declares a property and assign a value")
                .build();

        options.addOption(define);
    }

    public void eval(CommandLine commandLine) {
        context = Maps.newHashMap();
        String[] tmp = commandLine.getOptionValues("D");

        for (int i = 0; i < tmp.length; i+=2) {
            if (!context.containsKey(tmp[i])) {
                context.put(tmp[i], tmp[i+1]);
            }
            else {
                logger.warn("Overwriting previous assignment of {}", tmp[i]);
            }
            logger.debug("Declaring: {}={}", tmp[i], tmp[i+1]);


        }
    }

    @Override
    public RenderedData render(ConfigurationSource source) {
        Jinjava jinjava = new Jinjava();

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
