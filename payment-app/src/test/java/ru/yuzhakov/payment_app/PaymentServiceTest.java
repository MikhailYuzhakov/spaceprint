package ru.yuzhakov.payment_app;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Assertions;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.yuzhakov.payment_app.models.Account;
import ru.yuzhakov.payment_app.models.Transaction;
import ru.yuzhakov.payment_app.models.exceptions.AccountNotFoundException;
import ru.yuzhakov.payment_app.models.exceptions.ExcessAmountException;
import ru.yuzhakov.payment_app.repositories.AccountsRepository;
import ru.yuzhakov.payment_app.services.PaymentService;

import java.math.BigDecimal;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {
    /**
     * Блокируем входные данные AccountRepository
     * и заменяем их на свои данные.
     */
    @Mock
    public AccountsRepository accountsRepository;

    /**
     * Инжектируем в контейнер сервис с блокировками.
     */
    @InjectMocks
    public PaymentService paymentService;

    @Test
    public void moneyTransferTest() {
        //предпосылки для теста
        Account account1 = new Account();
        account1.setId(1L);
        account1.setBalance(new BigDecimal(1000));

        Account account2 = new Account();
        account2.setId(2L);
        account2.setBalance(new BigDecimal(2000));

        //получаем аккаунты из репозитория (точнее говорим, что при вызове методов, вернем просто аккаунты)
        BDDMockito.given(accountsRepository.findById(account1.getId())).willReturn(Optional.of(account1));
        BDDMockito.given(accountsRepository.findById(account2.getId())).willReturn(Optional.of(account2));

        Transaction transaction = new Transaction();
        transaction.setCreditNumber(account1.getId());
        transaction.setDebitNumber(account2.getId());
        transaction.setSum(new BigDecimal(50));

        //тестирование, вызов метода
        paymentService.transaction(transaction);

        //проверка
        BDDMockito.verify(accountsRepository).changeAmount(account1.getId(), new BigDecimal(950));
        BDDMockito.verify(accountsRepository).changeAmount(account2.getId(), new BigDecimal(2050));
    }

    @Test
    public void moneyTransferDestinationAccountNotFoundFlow() {
        Account sender = new Account();
        sender.setId(1L);
        sender.setBalance(new BigDecimal(1000));

        BDDMockito.given(accountsRepository.findById(1L)).willReturn(Optional.of(sender));
        BDDMockito.given(accountsRepository.findById(2L)).willReturn(Optional.empty());

        Transaction transaction = new Transaction();
        transaction.setCreditNumber(1L);
        transaction.setDebitNumber(2L);
        transaction.setSum(new BigDecimal(50));

        Assertions.assertThrows(
            AccountNotFoundException.class, () -> paymentService.transaction(transaction)
        );

        BDDMockito.verify(accountsRepository, Mockito.never()).changeAmount(Mockito.anyLong(), Mockito.any());
    }

    @Test
    public void moneyTransferExcessAmountExceptionFlow() {
        Account sender = new Account();
        sender.setId(1L);
        sender.setBalance(new BigDecimal(40));

        Account reciever = new Account();
        reciever.setId(2L);
        reciever.setBalance(new BigDecimal(1000));

        BDDMockito.given(accountsRepository.findById(sender.getId())).willReturn(Optional.of(sender));
        BDDMockito.given(accountsRepository.findById(reciever.getId())).willReturn(Optional.of(reciever));

        Transaction transaction = new Transaction();
        transaction.setCreditNumber(sender.getId());
        transaction.setDebitNumber(reciever.getId());
        transaction.setSum(new BigDecimal(50));

        Assertions.assertThrows(
                ExcessAmountException.class, () -> paymentService.transaction(transaction)
        );

        BDDMockito.verify(accountsRepository, Mockito.never()).changeAmount(Mockito.anyLong(), Mockito.any());

    }
}
