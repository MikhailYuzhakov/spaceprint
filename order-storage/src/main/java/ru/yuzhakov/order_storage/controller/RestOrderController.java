package ru.yuzhakov.order_storage.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yuzhakov.order_storage.model.Order;
import ru.yuzhakov.order_storage.service.OrderService;

import java.util.List;

@RestController
public class RestOrderController {
    private final OrderService service;

    public RestOrderController(OrderService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok().body(service.getAll());
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(service.getById(id));
    }

    @PostMapping("/order-create")
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        service.add(order);
        return ResponseEntity.ok().body(order);
    }

    @PostMapping("/order-update")
    public void updateOrder(@RequestBody Order order) {
        service.update(order, order.getId());
    }

    @GetMapping("/order-delete/{id}")
    public void deleteOrder(@PathVariable("id") Long id) {
        service.delete(id);
    }

    @GetMapping("/order-image-update/{id}/{imageUri}")
    public void updateImageUri(@PathVariable("imageUri") String imageUri, @PathVariable("id") Long id) {
        service.updateImageUri(imageUri, id);
    }
}
