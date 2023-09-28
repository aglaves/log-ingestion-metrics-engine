package com.luckstro.lime;

import com.luckstro.lime.configuration.Environment;
import com.luckstro.lime.kafka.KafkaEnvironmentInitializer;

public class Bootstrap {
    public static void main(String args[]) {
        Environment.initialize();
        new KafkaEnvironmentInitializer().initializeKafkaEnvironment();
    }
}
