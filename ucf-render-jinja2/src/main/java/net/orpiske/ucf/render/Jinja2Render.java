package net.orpiske.ucf.render;

import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import com.google.common.io.Resources;
import com.hubspot.jinjava.Jinjava;
import com.hubspot.jinjava.JinjavaConfig;
import com.hubspot.jinjava.loader.FileLocator;
import net.orpiske.ucf.facter.DefaultFacter;
import net.orpiske.ucf.facter.Facter;
import net.orpiske.ucf.render.ConfigurationRender;
import net.orpiske.ucf.types.ConfigurationSource;
import net.orpiske.ucf.types.RenderedData;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by otavio on 4/18/16.
 */
public class Jinja2Render implements ConfigurationRender {
    private static final Logger logger = LoggerFactory.getLogger(Jinja2Render.class);

    private Map<String, Object> context = null;

    public Jinja2Render() {
        context = Maps.newHashMap();
    }

    public void addOptions(Options options) {
        Option define  = Option.builder("D").argName("property=value")
                .hasArgs()
                .valueSeparator()
                .desc("declares a property and assign a value")
                .build();

        options.addOption(define);
    }

    public void eval(CommandLine commandLine) {

        String[] tmp = commandLine.getOptionValues("D");

        if (tmp == null) {
            logger.debug("No variable has been declared");
            return;
        }

        for (int i = 0; i < tmp.length; i+=2) {
            String key=tmp[i];
            String value=tmp[i+1];


            if (!context.containsKey(key)) {
                logger.debug("Declaring: {}={}", tmp[i], tmp[i+1]);
                context.put(key, value);
            }
            else {
                Object oldValue = context.get(key);

                if (oldValue instanceof LinkedList) {
                    logger.debug("Appending: {}={}", tmp[i], tmp[i+1]);
                    LinkedList<String> list = (LinkedList<String>) oldValue;

                    list.add(value);
                }
                else {
                    logger.debug("Declaring new iterable: {}={}", tmp[i], tmp[i+1]);
                    LinkedList<String> list = new LinkedList<>();

                    list.add((String) oldValue);
                    list.add(value);
                    context.put(key, list);
                }
            }

            Facter facter = new DefaultFacter();

            Map<String, Object> facts = facter.getFacts();
            context.putAll(facts);
        }
    }

    public Map<String, Object> getContext() {
        return context;
    }

    @Override
    public RenderedData render(ConfigurationSource source) {
        JinjavaConfig jinjavaConfig = new JinjavaConfig();
        FileLocator fileLocator = null;
        try {
            File includeDir = new File(source.getFile().getParentFile(), "ucf-include");

            fileLocator = new FileLocator(includeDir);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Jinjava jinjava = new Jinjava(jinjavaConfig);
        jinjava.setResourceLocator(fileLocator);


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
