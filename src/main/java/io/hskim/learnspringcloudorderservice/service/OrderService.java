package io.hskim.learnspringcloudorderservice.service;

import io.hskim.learnspringcloudorderservice.dto.OrderDto.OrderRequestDto;
import io.hskim.learnspringcloudorderservice.dto.OrderDto.OrderResponseDto;
import io.hskim.learnspringcloudorderservice.entity.OrderEntity;
import io.hskim.learnspringcloudorderservice.repo.OrderRepo;
import jakarta.ws.rs.NotFoundException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class OrderService {

  private final OrderRepo orderRepo;

  private final OrderProducerService orderProducerService;

  public OrderResponseDto postOrder(OrderRequestDto orderRequestDto) {
    OrderResponseDto orderResponseDto = orderRepo
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

    // CatalogService 연계
    orderProducerService.postProducer(
      "example-catalog-topic",
      orderResponseDto
    );
    // Connector 연계
    orderProducerService.postProducerConnector("orders", orderResponseDto);

    return orderResponseDto;
  }

  public Page<OrderResponseDto> getOrderListByUserId(
    String userId,
    Pageable pageable
  ) {
    log.info("Order service getOrderListByUserId");
    return orderRepo.findByUserId(userId, pageable).map(OrderEntity::toDto);
  }

  public OrderResponseDto getOrderByOrderId(String orderId) {
    return orderRepo
      .findById(UUID.fromString(orderId))
      .orElseThrow(() -> new NotFoundException())
      .toDto();
  }
}
