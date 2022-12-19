package config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Properties;

public class Config {

    private String configFilePath = "config.properties";
    private Properties prop = new Properties();

    public Config() throws IOException {
        URL resource = getClass().getClassLoader().getResource(configFilePath);
        if (Objects.isNull(resource)) {
            throw new FileNotFoundException("config file not found in resources");
        }
        FileInputStream propsInput = new FileInputStream(new File(resource.getFile()).getAbsolutePath());
        prop.load(propsInput);
    }

    public Properties getProp() {
        return prop;
    }

    public Boolean getIsHeadless() {
        return Boolean.parseBoolean(prop.getProperty("IS_HEADLESS"));
    }

    public String getBaseUrl() {
        return prop.getProperty("BASE_URL");
    }
}
