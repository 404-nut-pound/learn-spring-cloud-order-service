package io.hskim.learnspringcloudorderservice.service;

import com.fasterxml.jackson.databind.json.JsonMapper;
import io.hskim.learnspringcloudorderservice.dto.KafkaOrderDto;
import io.hskim.learnspringcloudorderservice.dto.KafkaOrderDto.Field;
import io.hskim.learnspringcloudorderservice.dto.KafkaOrderDto.Payload;
import io.hskim.learnspringcloudorderservice.dto.KafkaOrderDto.Schema;
import io.hskim.learnspringcloudorderservice.dto.OrderDto.OrderResponseDto;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderProducerService {

  private final KafkaTemplate<String, String> kafkaTemplate;

  private final JsonMapper jsonMapper = JsonMapper.builder().build();

  List<Field> fields = Arrays.asList(
    new Field("string", true, "order_id"),
    new Field("string", true, "user_id"),
    new Field("string", true, "product_id"),
    new Field("int32", true, "qty"),
    new Field("int32", true, "unit_price"),
    new Field("int32", true, "total_price")
  );

  Schema schema = Schema
    .builder()
    .type("struct")
    .fields(fields)
    .optional(false)
    .name("orders")
    .build();

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

  public OrderResponseDto postProducerConnector(
    String topic,
    OrderResponseDto orderResponseDto
  ) {
    Payload payload = Payload
      .builder()
      .orderId(orderResponseDto.getOrderId())
      .userId(orderResponseDto.getUserId())
      .catalogId(orderResponseDto.getCatalogId())
      .qty(orderResponseDto.getQty())
      .unitPrice(orderResponseDto.getUnitPrice())
      .totalPrice(orderResponseDto.getTotalPrice())
      .build();

    KafkaOrderDto kafkaOrderDto = new KafkaOrderDto(schema, payload);

    try {
      String jsonString = jsonMapper.writeValueAsString(kafkaOrderDto);

      kafkaTemplate.send(topic, jsonString);

      log.info(
        "Order Producer sent data from the Order microservice: " + kafkaOrderDto
      );
    } catch (Exception e) {
      e.printStackTrace();
    }

    return orderResponseDto;
  }
}
