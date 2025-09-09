package com.jetnotifier.notification.kafkaConfig;

import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers:localhost:9092}")
    private String bootstrapServers;

    //    @Value("${kafka.topic.email}")
    @Value("notification-email")
    private String emailTopic;

    @Value("${kafka.topic.push}")
    private String pushTopic;

    @Value("${kafka.topic.sms}")
    private String smsTopic;

    @Value("${kafka.topic.webhook}")
    private String webhookTopic;

    @Value("${kafka.topic.in_app}")
    private String inAppTopic;

    @Value("${kafka.topic.dlq}")
    private String dlqTopic;

    @PostConstruct
    public void init() {
        System.out.println("KafkaConfig initialized:");
        System.out.println("  bootstrapServers: " + bootstrapServers);
        System.out.println("  emailTopic: " + emailTopic);
        System.out.println("  pushTopic: " + pushTopic);
        System.out.println("  smsTopic: " + smsTopic);
        System.out.println("  webhookTopic: " + webhookTopic);
        System.out.println("  inAppTopic: " + inAppTopic);
        System.out.println("  dlqTopic: " + dlqTopic);
    }

    @Bean
    public NewTopic emailTopic() {
        return TopicBuilder.name(emailTopic).build();
    }

    @Bean
    public NewTopic pushTopic() {
        return TopicBuilder.name(pushTopic).build();
    }

    @Bean
    public NewTopic smsTopic() {
        return TopicBuilder.name(smsTopic).build();
    }

    @Bean
    public NewTopic webhookTopic() {
        return TopicBuilder.name(webhookTopic).build();
    }

    @Bean
    public NewTopic inAppTopic() {
        return TopicBuilder.name(inAppTopic).build();
    }

    @Bean
    public NewTopic dlNewTopic() {
        return TopicBuilder.name(dlqTopic).build();
    }

    @Bean
    public ProducerFactory<String, Object> producerFactory() {

        Map<String, Object> configProps = new HashMap<>();

        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        configProps.put(ProducerConfig.ACKS_CONFIG, "all");
        configProps.put(ProducerConfig.RETRIES_CONFIG, 3);

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public ConsumerFactory<String, Object> consumerFactory() {

        Map<String, Object> props = new HashMap<>();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "notification-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory(
            DefaultErrorHandler errorHandler) {

        ConcurrentKafkaListenerContainerFactory<String, Object> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setCommonErrorHandler(errorHandler);
        return factory;
    }

    @Bean
    public DefaultErrorHandler errorHandler(KafkaTemplate<String, Object> kafkaTemplate) {

        DeadLetterPublishingRecoverer recoverer =
                new DeadLetterPublishingRecoverer(
                        kafkaTemplate,
                        (record, ex) -> {
                            System.out.println("Recovering failed message to DLQ");
                            return new TopicPartition("notification-dlq", record.partition());
                        });

        //  Use FixedBackOff
        FixedBackOff fixedBackOff = new FixedBackOff(1000L, 3); // 1 sec delay, max 3 retries

        DefaultErrorHandler errorHandler = new DefaultErrorHandler(recoverer, fixedBackOff);

        return errorHandler;
    }
}
