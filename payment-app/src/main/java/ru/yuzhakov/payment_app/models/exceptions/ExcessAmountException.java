package ru.yuzhakov.payment_app.models.exceptions;

public class ExcessAmountException extends RuntimeException {
    public ExcessAmountException(String message) {
        super(message);
    }
}
