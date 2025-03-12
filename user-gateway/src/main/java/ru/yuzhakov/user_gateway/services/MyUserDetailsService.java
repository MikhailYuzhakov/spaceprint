package ru.yuzhakov.user_gateway.services;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.yuzhakov.user_gateway.models.Role;
import ru.yuzhakov.user_gateway.models.User;
import ru.yuzhakov.user_gateway.models.UserPrincipal;
import ru.yuzhakov.user_gateway.models.UserRole;
import ru.yuzhakov.user_gateway.repositories.RoleRepository;
import ru.yuzhakov.user_gateway.repositories.UserRepository;
import ru.yuzhakov.user_gateway.repositories.UserRoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class MyUserDetailsService implements ReactiveUserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    @Getter
    private final BCryptPasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(MyUserDetailsService.class);

    public Flux<User> getUsers() {
        return mapUserRoles(userRepository.findAll());
    }

    public Flux<User> getUser(String username) {
        return mapUserRoles(userRepository.findByUsername(username));
    }

    /**
     * Метод сопоставляет для заданного пользователя все доступные роли.
     * @param userFlux поток пользователей без ролей.
     * @return поток пользователей с ролями.
     */
    private Flux<User> mapUserRoles(Flux<User> userFlux) {
        Flux<UserRole> userRoles = getUserRoles();
        Flux<Role> roleList = getRoles();
        logger.info(String.valueOf(userFlux));
        return userFlux.flatMap(user ->
                userRoles.collectList()
                        .zipWith(roleList.collectList(), (userRoleList, roles) -> {
                            if (userRoleList != null) {
                                for (UserRole userRole : userRoleList) {
                                    if (Objects.equals(userRole.getUserId(), user.getId())) {
                                        user.getRoles().add(matchRole(roles, userRole));
                                    }
                                }
                            } else {
                                user.getRoles().add(new Role()); // или обработка по умолчанию
                            }
                            return user;
                        })
        );
    }

    /**
     * Сопоставляет роль пользователя в соответствии с промежуточной таблице user_roles
     * @param roleList список ролей из таблицы roles
     * @param userRole список сопоставлений их таблицы user_roles(id, user_id, role_id)
     * @return роль для пользователя
     */
    private Role matchRole(List<Role> roleList, UserRole userRole) {
        for (Role role : roleList) {
            if (Objects.equals(userRole.getRoleId(), role.getId())) {
                return role;
            }
        }
        return new Role();
    }

    public Flux<Role> getRoles() {
        return roleRepository.findAll();
    }

    public Flux<UserRole> getUserRoles() {
        return userRoleRepository.findAll();
    }

    public Mono<User> addUserWithoutRole(User user) {
        user.setIsEnabled(true);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user)
                .doOnSuccess(savedUser -> logger.info("User saved: {}", savedUser))
                .doOnError(error -> logger.error("Error saving user: {}", error.getMessage()));
    }

    public Mono<Void> setStandardRole(Mono<User> userMono) {
        return userMono
                .flatMap(user -> userRepository.findByUsername(user.getUsername()).singleOrEmpty() // Загружаем пользователя
                        .flatMap(userFromDB -> getRoles()
                                .collectList()
                                .flatMap(roleList -> {
                                    Long userId = userFromDB.getId();
                                    Long roleId = roleList.stream()
                                            .filter(role -> "CUSTOMER".equals(role.getName()))
                                            .map(Role::getId)
                                            .findFirst()
                                            .orElseThrow(() -> new NotFoundException("Role USER not found!"));
                                    UserRole userRole = new UserRole(null, userId, roleId);
                                    return userRoleRepository.save(userRole); // Сохраняем связь
                                })))
                .then();
    }

    public Mono<Void> delete(Long userId) {
        return userRepository.deleteById(userId)
                .then(userRoleRepository.deleteByUserId(userId));
    }

    public Mono<User> findUserById(Long id) {
        return mapUserRoles(userRepository.findById(id).flux()).singleOrEmpty();
    }

    /**
     * Обновляет данные о пользователе и роли. Для исключения дублирования записей в таблице user_roles - читаем все текущие роли пользователя,
     * сравниваем с вновь присвоенными ролями пользователя, удаляем повторы и записываем в БД.
     * @param newUser пользователя обновляемый
     * @param roleIds роли, которые вновь присвоенные
     */
    public Mono<User> update(User newUser, List<Long> roleIds) {
        // Удаляем все текущие роли пользователя по идентификатору
        return userRoleRepository.deleteByUserId(newUser.getId())
                .then(Mono.defer(() -> {
                    // Сохраняем все новые роли, полученные из веб-интерфейса
                    List<Mono<UserRole>> saveRoles = roleIds.stream()
                            .map(roleId -> userRoleRepository.save(new UserRole(null, newUser.getId(), roleId)))
                            .toList();

                    return Mono.when(saveRoles)
                            .then(Mono.just(newUser));
                }))
                .doOnNext(user -> newUser.setUpdatedAt(LocalDateTime.now()))
                .flatMap(userRepository::save);
    }

    public Mono<Void> deleteRole(Long roleId) {
        return roleRepository.deleteById(roleId)
                .then();
    }

    public Mono<Void> createRole(Role role) {
        return roleRepository.save(role).then();
    }

    public Mono<Role> findRole(Long id) {
        return roleRepository.findById(id);
    }

    public Mono<Role> updateRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        Flux<User> userFlux = getUser(username);
        return userFlux.singleOrEmpty()
                .map(UserPrincipal::new);
    }
}
