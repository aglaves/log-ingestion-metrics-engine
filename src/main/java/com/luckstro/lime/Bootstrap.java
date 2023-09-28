package com.luckstro.lime;

import com.luckstro.lime.configuration.Configuration;
import com.luckstro.lime.kafka.KafkaEnvironmentInitializer;

public class Bootstrap {
    public static void main(String args[]) {
        Configuration configuration = new Configuration();
        new KafkaEnvironmentInitializer().initializeKafkaEnvironment();
    }
}
