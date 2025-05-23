package io.gitbhub.eduardoconceicao90.helpdesk_bff.client;

import models.requests.CreateOrderRequest;
import models.requests.UpdateOrderRequest;
import models.responses.OrderResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(
        name = "order-service-api",
        path = "/api/orders"
)
public interface OrderFeignClient {

    @GetMapping("/{id}")
    ResponseEntity<OrderResponse> findById(@PathVariable(name = "id") final Long id);

    @PostMapping
    ResponseEntity<Void> save(@RequestBody CreateOrderRequest createOrderRequest);

    @PutMapping("/{id}")
    ResponseEntity<OrderResponse> update(@PathVariable(name = "id") final Long id, @RequestBody UpdateOrderRequest updateOrderRequest);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteById(@PathVariable(name = "id") final Long id);

    @GetMapping
    ResponseEntity<List<OrderResponse>> findAll();

    @GetMapping(params = {"page", "size", "sort", "direction"})
    ResponseEntity<Page<OrderResponse>> findAllPaginated(
                @RequestParam(name = "page", defaultValue = "0") final Integer page,
                @RequestParam(name = "size", defaultValue = "10") final Integer size,
                @RequestParam(name = "direction", defaultValue = "ASC") final String direction,
                @RequestParam(name = "sort", defaultValue = "id") final String sort
    );

}