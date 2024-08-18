package ru.yuzhakov.payment_app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.yuzhakov.payment_app.models.Account;

import java.math.BigDecimal;

@Repository
public interface AccountsRepository extends JpaRepository<Account, Long> {
    @Modifying
    @Query("UPDATE Account SET balance = :balance WHERE id = :id")
    void changeAmount(Long id, BigDecimal balance);
}
