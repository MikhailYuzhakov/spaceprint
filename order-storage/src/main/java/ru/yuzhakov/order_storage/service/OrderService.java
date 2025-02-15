package ru.yuzhakov.order_storage.service;

import org.springframework.stereotype.Service;
import ru.yuzhakov.order_storage.model.Order;
import ru.yuzhakov.order_storage.repository.OrdersRepository;

import java.util.Comparator;
import java.util.List;

@Service
public class OrderService {
    private final OrdersRepository repository;

    public OrderService(OrdersRepository repository) {
        this.repository = repository;
    }

    public List<Order> getAll() {
        List<Order> orders = repository.findAll();
        orders.sort(Comparator.comparingLong(Order::getId));
        return orders;
    }

    public Order getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void add(Order order) {
        repository.save(order);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public void update(Order order, Long id) {
        if (repository.existsById(id)) {
            order.setId(id);
            repository.save(order);
        }
    }

    public void updateImageUri(String imageUri, Long id) {
        System.out.println("ImageUri=" + imageUri + ", orderId=" + id);
        repository.updateImageUriById(imageUri, id);
    }
}
