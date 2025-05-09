package io.github.eduardoconceicao90.order_service_api.service.impl;

import io.github.eduardoconceicao90.order_service_api.mapper.OrderMapper;
import io.github.eduardoconceicao90.order_service_api.repository.OrderRepository;
import io.github.eduardoconceicao90.order_service_api.service.OrderService;
import lombok.RequiredArgsConstructor;
import models.requests.CreateOrderRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public void save(CreateOrderRequest createOrderRequest) {
        orderRepository.save(orderMapper.fromRequest(createOrderRequest));
    }

}
