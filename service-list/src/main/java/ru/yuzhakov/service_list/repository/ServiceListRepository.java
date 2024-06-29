package ru.yuzhakov.service_list.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yuzhakov.service_list.model.MyService;

@Repository
public interface ServiceListRepository extends JpaRepository<MyService, Long> {
    MyService findServiceByUri(String uri);
}
