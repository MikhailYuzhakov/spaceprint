package ru.yuzhakov.user_gateway.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import ru.yuzhakov.user_gateway.models.User;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User, Long> {
    Flux<User> findByUsername(String username);
}