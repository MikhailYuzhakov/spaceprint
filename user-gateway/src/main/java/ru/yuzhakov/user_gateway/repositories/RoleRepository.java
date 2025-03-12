package ru.yuzhakov.user_gateway.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import ru.yuzhakov.user_gateway.models.Role;

@Repository
public interface RoleRepository extends ReactiveCrudRepository<Role, Long> {}
