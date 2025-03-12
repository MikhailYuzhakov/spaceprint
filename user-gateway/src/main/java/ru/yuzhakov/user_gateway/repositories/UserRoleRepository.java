package ru.yuzhakov.user_gateway.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.yuzhakov.user_gateway.models.UserRole;

import java.util.List;

public interface UserRoleRepository extends ReactiveCrudRepository<UserRole, Long> {
    Mono<Void> deleteByUserId(Long userId);
    Flux<UserRole> findByUserId(Long userId);
}
