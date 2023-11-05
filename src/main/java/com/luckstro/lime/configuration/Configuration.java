package com.luckstro.lime.configuration;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Configuration {
    private static String CONFIGURATION_FILE_PATH = "configuration.yaml";
    private Map<String, Object> configuration = null;
    private Pattern systemPropertyPattern = Pattern.compile("\\$\\{(.*)\\}");

    public Configuration() {
        loadConfigurationFile(CONFIGURATION_FILE_PATH);
    }

    public Configuration(String configurationFilePath) {
        loadConfigurationFile(configurationFilePath);
    }

    public String getConfigurationString(String property) {
        return (String) configuration.get(property);
    }

    public Integer getConfigurationInteger(String property) {
        return (Integer) configuration.get(property);
    }

    public Short getConfigurationShort(String property) {
        return ((Integer)configuration.get(property)).shortValue();
    }

    public List<String> getConfigurationList(String property) {
        return (List<String>) configuration.get(property);
    }

    private void loadConfigurationFile(String configurationFilePath) {
        Yaml yaml = new Yaml();
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(configurationFilePath);
        Map<String, Object> yamlConfiguration = yaml.load(inputStream);
        configuration = new HashMap<>();
        loadConfigurationFromYaml(yamlConfiguration, "");
    }

    private void loadConfigurationFromYaml(Map<String, Object> yamlConfiguration, String base) {
        yamlConfiguration.forEach((k, v) -> {
            if (v instanceof Map) {
                loadConfigurationFromYaml((Map) v, base + k + "." );
            } else if (v instanceof List)
                loadArrayOfStrings(k, (List) v, base + k);
            else {
                if (v instanceof String) {
                    Matcher systemPropertyMatcher = systemPropertyPattern.matcher((String) v);
                    if (systemPropertyMatcher.find()) {
                        String systemProperty = systemPropertyMatcher.group(1);
                        if (System.getenv(systemProperty) != null)
                            configuration.put(base + k, System.getenv(systemProperty));
                        else
                            configuration.put(base + k, System.getProperty(systemProperty));
                    } else
                        configuration.put(base + k, v);
                } else
                    configuration.put(base + k, v);
            }
        });
    }

    private void loadArrayOfStrings(String key, List<String> values, String base) {
        int i=0;
        configuration.put(base, values);
        for (String value : values)
            configuration.put(base + "[" + i++ +"]", value);
    }
}
