package ru.yuzhakov.user_gateway.config;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import reactor.core.publisher.Mono;
import ru.yuzhakov.user_gateway.services.MyUserDetailsService;

public class CustomAuthenticationProvider implements ReactiveAuthenticationManager {

    private final MyUserDetailsService userDetailsService;

    public CustomAuthenticationProvider(MyUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * Метод для аутентификации пользователя и проверки пароля, но нужно проверять хэш
     * @param authentication объект аутентификации (не аутентифицированный)
     * @return объект аутентификации (аутентифицированный)
     * @throws AuthenticationException
     */
    @Override
    public Mono<Authentication> authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        return userDetailsService.findByUsername(username)
                .flatMap(userDetails -> userDetailsService.getUser(username)
                        .singleOrEmpty()
                        .map(user -> {
                            String currentPass = user.getPassword();
                            // Логика проверки пароля
                            if (userDetailsService.getPasswordEncoder().matches(currentPass, password)) {
                                throw new BadCredentialsException("Invalid credentials");
                            }
                            return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
                        }));
    }

}