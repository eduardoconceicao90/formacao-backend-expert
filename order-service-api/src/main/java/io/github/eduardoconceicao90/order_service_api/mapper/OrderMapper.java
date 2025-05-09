package io.github.eduardoconceicao90.order_service_api.mapper;

import io.github.eduardoconceicao90.order_service_api.entity.Order;
import models.enums.OrderStatusEnum;
import models.requests.CreateOrderRequest;
import models.responses.OrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = IGNORE,
        nullValueCheckStrategy = ALWAYS
)
public interface OrderMapper {

    OrderResponse fromEntity(final Order entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", source = "status", qualifiedByName = "mapStatus")
    Order fromRequest(CreateOrderRequest createOrderRequest);

    @Named("mapStatus")
    default OrderStatusEnum mapStatus(final String status) {
        return OrderStatusEnum.toEnum(status);
    }

}
