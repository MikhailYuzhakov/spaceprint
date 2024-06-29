package ru.yuzhakov.web_client.service;

import org.springframework.stereotype.Service;
import ru.yuzhakov.web_client.model.api.PaymentApi;

@Service
public class PaymentService {
    private final PaymentApi paymentApi;

    public PaymentService(PaymentApi paymentApi) {
        this.paymentApi = paymentApi;
    }
}
