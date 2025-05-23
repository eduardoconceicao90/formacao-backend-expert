package io.github.eduardoconceicao90.order_service_api.service.impl;

import io.github.eduardoconceicao90.order_service_api.client.UserServiceFeignClient;
import io.github.eduardoconceicao90.order_service_api.entity.Order;
import io.github.eduardoconceicao90.order_service_api.mapper.OrderMapper;
import io.github.eduardoconceicao90.order_service_api.repository.OrderRepository;
import io.github.eduardoconceicao90.order_service_api.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import models.dtos.OrderCreatedMessage;
import models.exceptions.ResourceNotFoundException;
import models.requests.CreateOrderRequest;
import models.requests.UpdateOrderRequest;
import models.responses.OrderResponse;
import models.responses.UserResponse;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static models.enums.OrderStatusEnum.CLOSED;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final UserServiceFeignClient userServiceFeignClient;
    private final RabbitTemplate rabbitTemplate;

    @Override
    public Order findById(final Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "Object not found. Id: " + id + ", Type: " + OrderResponse.class.getSimpleName()
        ));
    }

    @Override
    public void save(CreateOrderRequest createOrderRequest) {
        final var customer = validateUser(createOrderRequest.customerId());
        final var requester = validateUser(createOrderRequest.requesterId());
        final var entity = orderRepository.save(orderMapper.fromRequest(createOrderRequest));

        log.info("Order created: {}", entity);

        rabbitTemplate.convertAndSend(
                "helpdesk",
                "rk.orders.create",
                new OrderCreatedMessage(orderMapper.fromEntity(entity), customer, requester)
        );
    }

    @Override
    public OrderResponse update(final Long id, UpdateOrderRequest updateOrderRequest) {
        validateUsers(updateOrderRequest);
        Order entity = findById(id);
        closedOrder(updateOrderRequest, entity);
        return orderMapper.fromEntity(orderRepository.save(orderMapper.update(updateOrderRequest, entity)));
    }

    @Override
    public void deleteById(final Long id) {
        orderRepository.delete(findById(id));
    }

    @Override
    public List<OrderResponse> findAll() {
        return orderRepository.findAll().stream().map(orderMapper::fromEntity).toList();
    }

    @Override
    public Page<OrderResponse> findAllPaginated(Integer page, Integer size, String direction, String sort) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.valueOf(direction), sort);
        return orderRepository.findAll(pageRequest).map(orderMapper::fromEntity);
    }

    @Override
    public void closedOrder(UpdateOrderRequest updateOrderRequest, Order entity) {
        if(updateOrderRequest.status() != null && updateOrderRequest.status().equals(CLOSED.getDescription())) {
            entity.setClosedAt(LocalDateTime.now());
        }
    }

    UserResponse validateUser(final String userId) {
        return userServiceFeignClient.findById(userId).getBody();
    }

    private void validateUsers(UpdateOrderRequest updateOrderRequest) {
        if (updateOrderRequest.customerId() != null) validateUser(updateOrderRequest.customerId());
        if (updateOrderRequest.requesterId() != null) validateUser(updateOrderRequest.requesterId());
    }

}
