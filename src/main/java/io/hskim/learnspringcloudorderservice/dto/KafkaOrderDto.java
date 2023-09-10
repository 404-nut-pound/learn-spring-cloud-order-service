package io.hskim.learnspringcloudorderservice.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class KafkaOrderDto implements Serializable {

  private Schema schema;

  private Payload payload;

  @Data
  @Builder
  @AllArgsConstructor
  public static class Field {

    private String type;

    private boolean optional;

    private String field;
  }

  @Data
  @Builder
  public static class Schema {

    private String type;

    private List<Field> fields;

    private boolean optional;

    private String name;
  }

  @Data
  @Builder
  @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
  public static class Payload {

    private String orderId;

    private String userId;

    private String catalogId;

    private int qty;

    private int unitPrice;

    private int totalPrice;
  }
}
