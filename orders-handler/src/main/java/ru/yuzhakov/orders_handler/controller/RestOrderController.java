package ru.yuzhakov.orders_handler.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yuzhakov.orders_handler.model.Order;
import ru.yuzhakov.orders_handler.service.OrderService;

@RestController
@RequestMapping("/rest-order/")
@RequiredArgsConstructor
public class RestOrderController {
    private final OrderService service;

    @PostMapping("/order-create")
    public void createOrderRestMethod(@RequestBody Order order) {
        service.editOrder(order, false);
    }
}
