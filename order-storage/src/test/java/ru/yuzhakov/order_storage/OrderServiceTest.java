package ru.yuzhakov.order_storage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.yuzhakov.order_storage.model.Order;
import ru.yuzhakov.order_storage.repository.OrdersRepository;
import ru.yuzhakov.order_storage.service.OrderService;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    public OrdersRepository repository;

    @InjectMocks
    public OrderService service;

    @Test
    public void orderSingleRequestTest() {
          service.getById(1L);
        BDDMockito.verify(repository).findById(1L);
    }

    @Test
    public void orderDeleteTest() {
        Order order1 = new Order();
        order1.setId(1L);
        order1.setDescription("Экзепляр класса Order для теста orderRequestTest");
        order1.setPrintingTime(100);

        service.delete(order1.getId());
        BDDMockito.verify(repository).deleteById(order1.getId());
    }

    @Test
    public void orderAddTest() {
        Order order1 = new Order();
        order1.setId(1L);
        order1.setDescription("Экзепляр класса Order для теста orderRequestTest");
        order1.setPrintingTime(100);

        service.add(order1);
        BDDMockito.verify(repository).save(order1);
    }

    @Test
    public void orderMultiplyRequest() {
        service.getAll();
        BDDMockito.verify(repository).findAll();
    }

    @Test
    public void updateImageUriTest() {
        String uri = "/test_uri.png";
        Long id = 1L;

        service.updateImageUri(uri, id);
        BDDMockito.verify(repository).updateImageUriById(uri, id);
    }

    @Test
    public void updateOrderTest() {
        Order order1 = new Order();
        Long updatingOrderId = 1L;

        BDDMockito.given(repository.existsById(updatingOrderId)).willReturn(true);

        service.update(order1, updatingOrderId);
        BDDMockito.verify(repository).save(order1);
    }
}
