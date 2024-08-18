package ru.yuzhakov.payment_app.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yuzhakov.payment_app.models.Account;
import ru.yuzhakov.payment_app.models.Transaction;
import ru.yuzhakov.payment_app.services.PaymentService;

import java.util.List;

@RestController
@RequestMapping("/payment")
@AllArgsConstructor
public class PaymentController {
    private final PaymentService service;

    @GetMapping
    public ResponseEntity<List<Account>> getAccounts() {
        return ResponseEntity.ok().body(service.getAll());
    }

    /**
     * Проведение оплаты товара/услуги.
     * @param transaction объект с данными для транзакции
     * @return ответ с подтверждением
     */
    @PostMapping
    public ResponseEntity<Void> transaction(@RequestBody Transaction transaction) {
        service.transaction(transaction);
        return ResponseEntity.ok().body(null);
    }

    @PostMapping("/rollback")
    public ResponseEntity<Void> rollbackTransaction(@RequestBody Transaction transaction) {
        service.rollbackTransaction(transaction);
        return ResponseEntity.ok().body(null);
    }
}
