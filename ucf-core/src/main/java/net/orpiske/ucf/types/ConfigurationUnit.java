package net.orpiske.ucf.types;

import java.io.File;

/**
 * Created by otavio on 4/18/16.
 */
public class ConfigurationUnit {
    private UnitId unitId;
    private ConfigurationSource source;
    private RenderedData<?> renderedData;

    public UnitId getUnitId() {
        return unitId;
    }

    public void setUnitId(UnitId unitId) {
        this.unitId = unitId;
    }

    public ConfigurationSource getSource() {
        return source;
    }

    public void setSource(ConfigurationSource source) {
        this.source = source;
    }

    public RenderedData<?> getRenderedData() {
        return renderedData;
    }

    public void setRenderedData(RenderedData<?> renderedData) {
        this.renderedData = renderedData;
    }

    @Override
    public String toString() {
        return "ConfigurationUnit{" +
                "unitId=" + unitId +
                ", source=" + source +
                ", renderedData=" + renderedData +
                '}';
    }

    public File resolveDestination(String destination) {
        Target target = unitId.getTarget();

        return new File(destination + File.separator + target.getPath() + File.separator
                + target.getName());
    }
}
