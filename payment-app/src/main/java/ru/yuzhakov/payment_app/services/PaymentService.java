package ru.yuzhakov.payment_app.services;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yuzhakov.payment_app.models.Account;
import ru.yuzhakov.payment_app.models.Transaction;
import ru.yuzhakov.payment_app.models.exceptions.AccountNotFoundException;
import ru.yuzhakov.payment_app.models.exceptions.ExcessAmountException;
import ru.yuzhakov.payment_app.repositories.AccountsRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class PaymentService {
    private final AccountsRepository repository;

    /**
     * Проведение транзакции со счетами.
     * @param transaction объект с данными транзакции.
     */
    @Transactional
    public void transaction(Transaction transaction) {
        Account creditAccount = repository.findById(transaction.getCreditNumber())
                .orElseThrow(AccountNotFoundException::new);
        Account debitAccount = repository.findById(transaction.getDebitNumber())
                        .orElseThrow(AccountNotFoundException::new);
        System.out.println(creditAccount);
        System.out.println(debitAccount);

        //проверяем хватит ли средств на балансе для покупки товара/услуги
        assert creditAccount != null;
        if (creditAccount.getBalance().compareTo(transaction.getSum()) < 0)
            throw new ExcessAmountException("Недостаточно средств на счете!");

        //уменьшаем остаток средств на аккаунте покупателя
        repository.changeAmount(
                creditAccount.getId(),
                creditAccount.getBalance().subtract(transaction.getSum()));

        //увеличиваем остаток средств на аккаунте продавца
        repository.changeAmount(
                debitAccount.getId(),
                debitAccount.getBalance().add(transaction.getSum())
        );
    }

    @Transactional
    public void rollbackTransaction(Transaction transaction) {
        Account creditAccount = repository.findById(transaction.getCreditNumber())
                .orElseThrow(AccountNotFoundException::new);
        Account debitAccount = repository.findById(transaction.getDebitNumber())
                .orElseThrow(AccountNotFoundException::new);

        //возвращаем средства на аккаунте покупателя
        repository.changeAmount(
                creditAccount.getId(),
                creditAccount.getBalance().add(transaction.getSum()));

        //забираем средства с аккаунта продавца
        repository.changeAmount(
                debitAccount.getId(),
                debitAccount.getBalance().subtract(transaction.getSum())
        );

        //откатываем сумму с аккаунта продавца
        debitAccount.setBalance(debitAccount.getBalance().subtract(transaction.getSum()));

        //возвращаем сумму покупателю на счет
        creditAccount.setBalance(creditAccount.getBalance().add(transaction.getSum()));

        repository.save(debitAccount);
        repository.save(creditAccount);
    }

    public List<Account> getAll() {
        return repository.findAll();
    }
}
