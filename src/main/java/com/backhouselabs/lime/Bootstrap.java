package com.backhouselabs.lime;

import com.backhouselabs.lime.configuration.Configuration;
import com.backhouselabs.lime.kafka.KafkaEnvironmentInitializer;

public class Bootstrap {
    public static void main(String args[]) {
        Configuration configuration = new Configuration();
        new KafkaEnvironmentInitializer().initializeKafkaEnvironment();
    }
}
