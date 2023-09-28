package com.backhouselabs.lime.configuration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ConfigurationTest {
    @Test
    public void testFirstLevelProperty() {
        Configuration configuration = new Configuration("configuration_junit.yaml");
        Assertions.assertEquals("first level value", configuration.getConfigurationString("first_level_property"));
    }

    @Test
    public void testSecondLevelProperty() {
        Configuration configuration = new Configuration("configuration_junit.yaml");
        Assertions.assertEquals("second level value", configuration.getConfigurationString("second.level_property"));
    }

    @Test
    public void testArrayProperty() {
        Configuration configuration = new Configuration("configuration_junit.yaml");
        Assertions.assertEquals("third", configuration.getConfigurationString("third.level.array[0]"));
        Assertions.assertEquals("level", configuration.getConfigurationString("third.level.array[1]"));
        Assertions.assertEquals("value", configuration.getConfigurationString("third.level.array[2]"));
    }
}
