package io.hskim.learnspringcloudorderservice.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
@EnableKafka
public class KafkaProducerConfig {

  @Bean
  public ProducerFactory<String, String> producerFactory() {
    Map<String, Object> propertiesMap = new ConcurrentHashMap<>();
    propertiesMap.put(
      ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
      "127.0.0.1:9092"
    );
    propertiesMap.put(
      ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
      StringSerializer.class
    );
    propertiesMap.put(
      ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
      StringSerializer.class
    );

    return new DefaultKafkaProducerFactory<>(propertiesMap);
  }

  @Bean
  public KafkaTemplate<String, String> kafkaListenerContainerFactory() {
    return new KafkaTemplate<>(producerFactory());
  }
}
