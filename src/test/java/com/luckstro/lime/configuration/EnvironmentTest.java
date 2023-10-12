package com.luckstro.lime.configuration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EnvironmentTest {
    @Test
    public void verifyInitializationLoadsConfig() {
        Environment.initialize("configuration_junit.yaml");
        Assertions.assertEquals("first level value", Environment.getConfiguration().getConfigurationString("first_level_property"));
    }
}
