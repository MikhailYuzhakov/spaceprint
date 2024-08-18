package ru.yuzhakov.payment_app;

import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.yuzhakov.payment_app.models.Account;
import ru.yuzhakov.payment_app.models.Transaction;
import ru.yuzhakov.payment_app.repositories.AccountsRepository;
import ru.yuzhakov.payment_app.services.PaymentService;

import java.math.BigDecimal;
import java.util.Optional;

@SpringBootTest
public class PaymentServiceIntegrationTest {

    @MockBean
    public AccountsRepository accountsRepository;

    @Autowired
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
}
