package io.hskim.learnspringcloudorderservice.entity;

import io.hskim.learnspringcloudorderservice.dto.OrderDto.OrderResponseDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "ca_order")
@DynamicInsert
@DynamicUpdate
@Getter
@Setter(value = AccessLevel.PROTECTED)
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false, exclude = {})
@ToString(callSuper = false, exclude = {})
public class OrderEntity {

  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(columnDefinition = "BINARY(16)")
  @Comment(value = "상품 ID")
  private UUID orderId;

  @Column(nullable = false)
  @Comment(value = "주문 수량")
  private int qty;

  @Column(nullable = false)
  @Comment(value = "주문 단가")
  private int unitPrice;

  @Column(nullable = false)
  @Comment(value = "주문 총액")
  private int totalPrice;

  @Column(length = 40, nullable = false)
  @Comment(value = "주문자 ID")
  private String userId;

  @Column(length = 40, nullable = false)
  @Comment(value = "주문 상품 ID")
  private String catalogId;

  @LastModifiedDate
  @Column(nullable = false)
  @ColumnDefault(value = "now()")
  @Comment(value = "수정일시")
  private LocalDateTime updatedAt;

  public OrderResponseDto toDto() {
    return OrderResponseDto
      .builder()
      .orderId(this.orderId.toString())
      .qty(this.qty)
      .unitPrice(this.unitPrice)
      .totalPrice(this.totalPrice)
      .userId(this.userId)
      .catalogId(this.catalogId)
      .updatedAt(
        this.updatedAt != null
          ? this.updatedAt.format(
              DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            )
          : ""
      )
      .build();
  }
}
