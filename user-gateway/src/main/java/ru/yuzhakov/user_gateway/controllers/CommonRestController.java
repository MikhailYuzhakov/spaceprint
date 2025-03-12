package ru.yuzhakov.user_gateway.controllers;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.yuzhakov.user_gateway.models.Role;
import ru.yuzhakov.user_gateway.models.User;
import ru.yuzhakov.user_gateway.models.UserRole;
import ru.yuzhakov.user_gateway.services.MyUserDetailsService;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class CommonRestController {

    private final MyUserDetailsService userDetailsService;
    private static final Logger logger = LoggerFactory.getLogger(CommonRestController.class);

    @GetMapping(value = "/get-users", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<User> getUsers() {
        return userDetailsService.getUsers();
    }

    @GetMapping(value = "/get-user/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<User> getUser(@PathVariable("username") String username) {
        return userDetailsService.getUser(username);
    }

    @GetMapping(value = "/get-user-roles", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<UserRole> getUserRoles() {
        return userDetailsService.getUserRoles();
    }

    @GetMapping(value = "/get-roles", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Role> getRoles() {
        return userDetailsService.getRoles();
    }

    @GetMapping
    public Mono<String> helloWorld() {
        return Mono.just("Hello world");
    }
}
