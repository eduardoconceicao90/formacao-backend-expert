package io.github.eduardoconceicao90.order_service_api.repository;

import io.github.eduardoconceicao90.order_service_api.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
