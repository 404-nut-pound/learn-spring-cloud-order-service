package io.hskim.learnspringcloudorderservice.controller;

import io.hskim.learnspringcloudorderservice.dto.OrderDto.OrderRequestDto;
import io.hskim.learnspringcloudorderservice.dto.OrderDto.OrderResponseDto;
import io.hskim.learnspringcloudorderservice.dto.OrderDto.ValidatedPostGroup;
import io.hskim.learnspringcloudorderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/order")
@RequiredArgsConstructor
public class OrderController {

  private final OrderService orderService;

  @PostMapping(value = "/{userId}")
  public OrderResponseDto postOrder(
    @PathVariable String userId,
    @Validated(
      value = { ValidatedPostGroup.class }
    ) @RequestBody OrderRequestDto orderRequestDto
  ) {
    return orderService.postOrder(orderRequestDto);
  }

  @GetMapping(value = "/{userId}/orders")
  public Page<OrderResponseDto> getOrderListByUserId(
    @PathVariable String userId,
    Pageable pageable
  ) {
    return orderService.getOrderListByUserId(userId, pageable);
  }

  @GetMapping(value = "/{orderId}")
  public OrderResponseDto getOrderByOrderId(
    @PathVariable String serviceType,
    @PathVariable String orderId
  ) {
    return orderService.getOrderByOrderId(orderId);
  }
}
