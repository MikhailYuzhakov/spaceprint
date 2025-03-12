package ru.yuzhakov.user_gateway.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import ru.yuzhakov.user_gateway.models.Role;
import ru.yuzhakov.user_gateway.models.User;
import ru.yuzhakov.user_gateway.services.MyUserDetailsService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class UserController {

    private final MyUserDetailsService userDetailsService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(ApplicationContext applicationContext)
    {
        this.userDetailsService = applicationContext.getBean(MyUserDetailsService.class);
    }

    @GetMapping("/register")
    public Mono<String> showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return Mono.just("registration");
    }

    @PostMapping("/register")
    public Mono<String> registerUser(@ModelAttribute User user) {
        return userDetailsService.setStandardRole(userDetailsService.addUserWithoutRole(user))
                .then(Mono.just("redirect:/homepage"));
    }

    @GetMapping("/users")
    public Mono<String> getUsersList(Model model) {
        return userDetailsService.getUsers()
                .map(user -> {
                    user.setFormattedCreatedAt(formatDate(user.getCreatedAt()));
                    return user;
                })
                .collectList()
                .doOnNext(users -> model.addAttribute("users", users))
                .then(Mono.just("user-list"));
    }

    @GetMapping("/users/user-delete/{id}")
    public Mono<String> deleteUser(@PathVariable Long id) {
        return userDetailsService.delete(id)
                .then(Mono.just("redirect:/users"));
    }

    @GetMapping("/users/user-update/{id}")
    public Mono<String> getUpdateUserForm(@PathVariable Long id, Model model) {
        return userDetailsService.findUserById(id)
                .map(user -> model.addAttribute("user", user))
                .then(userDetailsService.getRoles().collectList()
                        .map(roleList -> model.addAttribute("allRoles", roleList)))
                .then(Mono.just("user-update"));
    }

    @PostMapping("/users/user-update")
    public Mono<String> updateUser(@ModelAttribute("user") User user, ServerWebExchange exchange) {
        return exchange.getFormData()
                .flatMap(formData -> {
                    List<String> roleIdStrings = formData.get("roleIds");
                    if (roleIdStrings != null) {
                        List<Long> roleIds = roleIdStrings.stream()
                                .map(Long::valueOf)
                                .toList();
                        logger.info("{}{}", user.toString(), roleIds);
                        return userDetailsService.update(user, roleIds)
                                .then(Mono.just("redirect:/users"));
                    } else {
                        // Обработка случая, когда roleIds отсутствует
                        logger.warn("roleIds не найдены в форме");
                        return Mono.just("redirect:/users");
                    }
                });
    }

    @GetMapping("/users/edit-roles-list")
    public Mono<String> getUpdateRolesForm(Model model) {
        return userDetailsService.getRoles()
                .collectList()
                .map(roleList -> model.addAttribute("roles", roleList))
                .then(Mono.just("role-list"));
    }

    @GetMapping("/users/edit-roles-list/delete/{id}")
    public Mono<String> deleteRole(@PathVariable("id") Long id) {
        return userDetailsService.deleteRole(id)
                .then(Mono.just("redirect:/users/edit-roles-list"));
    }

    @GetMapping("/users/edit-roles-list/create")
    public Mono<String> getCreateRoleForm(Model model) {
        model.addAttribute("role", new Role());
        return Mono.just("role-create");
    }

    @PostMapping("/users/edit-roles-list/create")
    public Mono<String> createRole(@ModelAttribute Role role) {
        return userDetailsService.createRole(role)
                .then(Mono.just("redirect:/users/edit-roles-list"));
    }

    @GetMapping("/users/edit-roles-list/update/{id}")
    public Mono<String> getUpdateRoleForm(@PathVariable("id") Long id, Model model) {
        return userDetailsService.findRole(id)
                .map(role -> model.addAttribute("role", role))
                .then(Mono.just("role-edit"));
    }

    @PostMapping("/users/edit-roles-list/update")
    public Mono<String> updateRole(@ModelAttribute Role role) {
        return userDetailsService.updateRole(role)
                .then(Mono.just("redirect:/users/edit-roles-list"));
    }

    /**
     * Шаблон thymeleaf не обрабатывает корректно дату и время корректно, поэтому обработка производится здесь
     * и в шаблон передается тип данных String
     * @param dateTime дата и время
     * @return строковое представление даты и времени
     */
    private String formatDate(LocalDateTime dateTime) {
        if (dateTime != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
            return dateTime.format(formatter);
        }
        return "N/A";
    }
}
