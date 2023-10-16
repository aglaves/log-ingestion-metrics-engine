package com.luckstro.lime.kafka;

import com.luckstro.lime.configuration.Environment;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class KafkaEnvironmentInitializerTest {
    @Test
    public void testInitializeEnvironmentCreatesTopics() throws ExecutionException, InterruptedException {
        Environment.initialize();
        KafkaEnvironmentInitializer kafkaEnvironmentInitializer = new KafkaEnvironmentInitializer();
        kafkaEnvironmentInitializer.initializeKafkaEnvironment();

        try (AdminClient admin = AdminClient.create(new KafkaConfiguration().kafkaConfiguration())) {

            Assertions.assertTrue(admin.listTopics().names().get().contains("unit_test_topic"));
            Assertions.assertTrue(admin.listTopics().names().get().contains("unit_test_topic2"));

        } catch (ExecutionException e) {
            throw e;
        } catch (InterruptedException e) {
            throw e;
        }
    }
}
