package com.backhouselabs.lime.kafka;

import com.backhouselabs.lime.configuration.Configuration;
import org.apache.kafka.clients.admin.*;
import org.apache.kafka.common.KafkaFuture;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class KafkaEnvironmentInitializer {
    public void initializeKafkaEnvironment() {
        try (AdminClient admin = AdminClient.create(new KafkaConfiguration().configuration())) {
            ensureTopicsCreated(admin);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void ensureTopicsCreated(Admin admin) throws ExecutionException, InterruptedException {
        Configuration configuration = new Configuration();
        List<String> topicsToCreate = createListOfTopicsToCreate(admin.listTopics().names().get(),
                configuration.getConfigurationList("kafka.topics"));
        for (String topicToCreate : topicsToCreate)
            createTopic(admin, topicToCreate,
                    configuration.getConfigurationInteger("kafka.topic_partitions"),
                    configuration.getConfigurationShort("kafka.topic_replication_factor"));
//      boolean contains = names.contains("TEST_6");
//            if (!contains) {
//                List<NewTopic> topicList = new ArrayList<NewTopic>();
//                Map<String, String> configs = new HashMap<String, String>();
//                int partitions = 5;
//                Short replication = 1;
//                NewTopic newTopic = new NewTopic("TEST_6", partitions, replication).configs(configs);
//                topicList.add(newTopic);
//                admin.createTopics(topicList);
//            }
    }

    private List<String> createListOfTopicsToCreate(Set<String> currentTopicNames, List<String> expectedTopics) {
        ArrayList<String> listOfTopicsToCreate = new ArrayList<>();
        expectedTopics.forEach(topicName -> {
            if (!currentTopicNames.contains(topicName))
                listOfTopicsToCreate.add(topicName);
        });
        return listOfTopicsToCreate;
    }

    private void createTopic(Admin admin, String topicName, int partitions, short replicationFactor)
            throws ExecutionException, InterruptedException {
        NewTopic newTopic = new NewTopic(topicName, partitions, replicationFactor);
        CreateTopicsResult result = admin.createTopics(Collections.singleton(newTopic));
        KafkaFuture<Void> future = result.values().get(topicName);
        future.get();
    }
}
