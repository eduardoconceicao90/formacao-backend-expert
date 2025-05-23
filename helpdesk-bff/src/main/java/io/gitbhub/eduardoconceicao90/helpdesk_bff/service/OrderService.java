package io.gitbhub.eduardoconceicao90.helpdesk_bff.service;

import io.gitbhub.eduardoconceicao90.helpdesk_bff.client.OrderFeignClient;
import models.requests.CreateOrderRequest;
import models.requests.UpdateOrderRequest;
import models.responses.OrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderFeignClient orderFeignClient;

    public OrderResponse findById(final Long id) {
        return orderFeignClient.findById(id).getBody();
    }

    public void save(CreateOrderRequest createOrderRequest) {
        orderFeignClient.save(createOrderRequest);
    }

    public OrderResponse update(final Long id, UpdateOrderRequest updateOrderRequest) {
        return orderFeignClient.update(id, updateOrderRequest).getBody();
    }

    public void deleteById(final Long id) {
        orderFeignClient.deleteById(id);
    }

    public List<OrderResponse> findAll() {
        return orderFeignClient.findAll().getBody();
    }

    public Page<OrderResponse> findAllPaginated(Integer page, Integer size, String direction, String sort) {
        return orderFeignClient.findAllPaginated(page, size, direction, sort).getBody();
    }

}
