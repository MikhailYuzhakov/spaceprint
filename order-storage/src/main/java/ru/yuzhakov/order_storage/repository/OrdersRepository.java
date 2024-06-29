package ru.yuzhakov.order_storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.yuzhakov.order_storage.model.Order;

@Repository
public interface OrdersRepository extends JpaRepository<Order, Long> {
    @Transactional
    @Modifying
    @Query("update Order o set o.imageUri = :imageUri where o.id = :id")
    void updateImageUriById(@Param("imageUri") String imageUri, @Param("id") Long id);
}
