package io.github.eduardoconceicao90.order_service_api.controller.impl;

import io.github.eduardoconceicao90.order_service_api.controller.OrderController;
import io.github.eduardoconceicao90.order_service_api.mapper.OrderMapper;
import io.github.eduardoconceicao90.order_service_api.service.OrderService;
import lombok.RequiredArgsConstructor;
import models.requests.CreateOrderRequest;
import models.requests.UpdateOrderRequest;
import models.responses.OrderResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
public class OrderControllerImpl implements OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @Override
    public ResponseEntity<OrderResponse> findById(final Long id) {
        return ResponseEntity.ok().body(orderMapper.fromEntity(orderService.findById(id)));
    }

    @Override
    public ResponseEntity<Void> save(CreateOrderRequest createOrderRequest) {
        orderService.save(createOrderRequest);
        return ResponseEntity.status(CREATED.value()).build();
    }

    @Override
    public ResponseEntity<OrderResponse> update(Long id, UpdateOrderRequest updateOrderRequest) {
        return ResponseEntity.ok().body(orderService.update(id, updateOrderRequest));
    }

}
