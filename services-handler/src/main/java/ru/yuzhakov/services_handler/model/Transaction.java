package ru.yuzhakov.services_handler.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Transaction {
    private Long creditNumber;
    private Long debitNumber;
    private BigDecimal sum;
}
