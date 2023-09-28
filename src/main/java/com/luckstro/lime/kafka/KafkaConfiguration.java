package com.luckstro.lime.kafka;

import com.luckstro.lime.configuration.Configuration;
import com.luckstro.lime.configuration.Environment;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;

import java.nio.file.Path;
import java.util.Properties;

public class KafkaConfiguration {
    public Properties kafkaConfiguration() {
        Properties kafkaConfiguration = new Properties();
        kafkaConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG,
                Environment.getConfiguration().getConfigurationString("kafka.application_id"));
        kafkaConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG,
                Environment.getConfiguration().getConfigurationString("kafka.url"));
        kafkaConfiguration.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        kafkaConfiguration.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        kafkaConfiguration.put(StreamsConfig.STATE_DIR_CONFIG, createStateDirectoryPath(Environment.getConfiguration()));
        return kafkaConfiguration;
    }

    private String createStateDirectoryPath(Configuration configuration) {
        return Path.of(configuration.getConfigurationString("kafka.state_dir")).toAbsolutePath().toString();
    }
}
