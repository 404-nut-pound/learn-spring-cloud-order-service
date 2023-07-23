package io.hskim.learnspringcloudorderservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

public class OrderDto {

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @ToString
  @EqualsAndHashCode
  public static class OrderRequestDto {

    @NotBlank(groups = { ValidatedPostGroup.class, ValidatedPatchGroup.class })
    private String userId;

    @NotBlank(groups = { ValidatedPostGroup.class, ValidatedPatchGroup.class })
    private String catalogId;

    @PositiveOrZero(
      groups = { ValidatedPostGroup.class, ValidatedPatchGroup.class }
    )
    private int qty;

    @PositiveOrZero(
      groups = { ValidatedPostGroup.class, ValidatedPatchGroup.class }
    )
    private int unitPrice;
  }

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @ToString
  @EqualsAndHashCode
  public static class OrderResponseDto {

    private String orderId;

    private int qty;

    private int unitPrice;

    private int totalPrice;

    private String userId;

    private String catalogId;

    private String updatedAt;
  }

  public interface ValidatedPostGroup {}

  public interface ValidatedPatchGroup {}
}
