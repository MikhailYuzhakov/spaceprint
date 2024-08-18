package ru.yuzhakov.services_handler.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yuzhakov.services_handler.model.api.PaymentApi;

@Service
@RequiredArgsConstructor
@Getter
public class PaymentService {
    private final PaymentApi paymentApi;
}
