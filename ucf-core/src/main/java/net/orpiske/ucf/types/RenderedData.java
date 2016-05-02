package net.orpiske.ucf.types;

/**
 * Created by otavio on 4/19/16.
 */
public class RenderedData<T> {
    T configurationData;

    public T getConfigurationData() {
        return configurationData;
    }

    public T getConfigurationData(Class<T> tClass) {
        return configurationData;
    }

    public void setConfigurationData(T configurationData) {
        this.configurationData = configurationData;
    }
}
