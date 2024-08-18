package ru.yuzhakov.service_list.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.yuzhakov.service_list.model.MyService;

@Repository
public interface ServiceListRepository extends JpaRepository<MyService, Long> {
    MyService findServiceByUri(String uri);

    @Transactional
    @Modifying
    @Query("update MyService s set s.imageUri = :imageUri where s.id = :id")
    void updateImageUriById(@Param("imageUri") String imageUri, @Param("id") Long id);
}
