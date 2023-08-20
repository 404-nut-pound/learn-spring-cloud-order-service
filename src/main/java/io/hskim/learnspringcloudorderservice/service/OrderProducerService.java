package io.hskim.learnspringcloudorderservice.service;

import com.fasterxml.jackson.databind.json.JsonMapper;
import io.hskim.learnspringcloudorderservice.dto.OrderDto.OrderResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderProducerService {

  private final KafkaTemplate<String, String> kafkaTemplate;

  private final JsonMapper jsonMapper = JsonMapper.builder().build();

  public OrderResponseDto postProducer(
    String topic,
    OrderResponseDto orderResponseDto
  ) {
    try {
      String jsonString = jsonMapper.writeValueAsString(orderResponseDto);

      kafkaTemplate.send(topic, jsonString);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return orderResponseDto;
  }
}
