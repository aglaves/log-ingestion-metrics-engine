package com.luckstro.lime.configuration;

public class Environment {
    private static Configuration configuration;

    public static void initialize() {
        configuration = new Configuration();
    }

    public static void initialize(String configurationPath) {
        configuration = new Configuration(configurationPath);
    }

    public static Configuration getConfiguration() {
        return configuration;
    }
}
