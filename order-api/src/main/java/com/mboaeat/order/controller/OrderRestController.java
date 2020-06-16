package com.mboaeat.order.controller;

import com.mboaeat.order.domain.Order;
import com.mboaeat.order.domain.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.mboaeat.order.controller.OrderConverter.dtoToModel;

@RestController
@RequestMapping("/api/v1.0/orders")
@Tag(name = "Order API")
@Schema(description = "Provides a list of methods that retrieve orders and their data")
@Validated
public class OrderRestController {

    private final OrderService orderService;

    public OrderRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(summary = "Create new order")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@Parameter(name = "New order", required = true)
                                @RequestBody @Valid OrderRequest orderRequest){

        Order order = dtoToModel(orderRequest);
        orderService.createOrder(orderRequest);
    }
}
