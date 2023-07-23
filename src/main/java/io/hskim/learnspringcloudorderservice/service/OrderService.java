package io.hskim.learnspringcloudorderservice.service;

import io.hskim.learnspringcloudorderservice.dto.OrderDto.OrderRequestDto;
import io.hskim.learnspringcloudorderservice.dto.OrderDto.OrderResponseDto;
import io.hskim.learnspringcloudorderservice.entity.OrderEntity;
import io.hskim.learnspringcloudorderservice.repo.OrderRepo;
import jakarta.ws.rs.NotFoundException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepo orderRepo;

  public OrderResponseDto postOrder(OrderRequestDto orderRequestDto) {
    return orderRepo
      .save(
        OrderEntity
          .builder()
          .qty(orderRequestDto.getQty())
          .unitPrice(orderRequestDto.getUnitPrice())
          .totalPrice(orderRequestDto.getQty() * orderRequestDto.getUnitPrice())
          .userId(orderRequestDto.getUserId())
          .catalogId(orderRequestDto.getCatalogId())
          .build()
      )
      .toDto();
  }

  public Page<OrderResponseDto> getOrderListByUserId(
    String userId,
    Pageable pageable
  ) {
    return orderRepo
      .findByUserIdContains(userId, pageable)
      .map(OrderEntity::toDto);
  }

  public OrderResponseDto getOrderByOrderId(String orderId) {
    return orderRepo
      .findById(UUID.fromString(orderId))
      .orElseThrow(() -> new NotFoundException())
      .toDto();
  }
}
