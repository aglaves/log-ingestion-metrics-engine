package com.luckstro.lime.kafka;

import com.luckstro.lime.configuration.Configuration;
import com.luckstro.lime.configuration.Environment;
import org.apache.kafka.common.config.SslConfigs;
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
        kafkaConfiguration.put(StreamsConfig.SECURITY_PROTOCOL_CONFIG, Environment.getConfiguration().getConfigurationString("kafka.security.protocol"));
        kafkaConfiguration.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, Environment.getConfiguration().getConfigurationString("kafka.security.truststore.location"));
        kafkaConfiguration.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, Environment.getConfiguration().getConfigurationString("kafka.security.truststore.password"));
        kafkaConfiguration.put(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, Environment.getConfiguration().getConfigurationString("kafka.security.keystore.location"));
        kafkaConfiguration.put(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, Environment.getConfiguration().getConfigurationString("kafka.security.keystore.password"));
        kafkaConfiguration.put(SslConfigs.SSL_KEY_PASSWORD_CONFIG, Environment.getConfiguration().getConfigurationString("kafka.security.key.password"));
        kafkaConfiguration.put(SslConfigs.SSL_ENDPOINT_IDENTIFICATION_ALGORITHM_CONFIG, "");
        return kafkaConfiguration;
    }

    private String createStateDirectoryPath(Configuration configuration) {
        return Path.of(configuration.getConfigurationString("kafka.state_dir")).toAbsolutePath().toString();
    }
}
