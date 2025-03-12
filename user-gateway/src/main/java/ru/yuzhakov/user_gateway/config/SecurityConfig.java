package ru.yuzhakov.user_gateway.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.server.csrf.ServerCsrfTokenRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.csrf.CookieServerCsrfTokenRepository;
import reactor.core.publisher.Mono;
import ru.yuzhakov.user_gateway.models.User;
import ru.yuzhakov.user_gateway.services.MyUserDetailsService;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final ApplicationContext context;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/homepage",
                                "/register/**",
                                "/static/**",
                                "/static-res-services/**",
                                "/img-res-services/**",
                                "/img-res-orders/**",
                                "/static-res-orders").permitAll()
                        .pathMatchers("/users/**").hasAuthority("ADMIN")
                        .pathMatchers("/api/**").hasAuthority("ADMIN")
                        .pathMatchers("/services/**").hasAuthority("CUSTOMER")
                        .pathMatchers("/orders/**").hasAuthority("EXECUTOR")
                        .pathMatchers("/services/create/**").access((authentication, context) -> authentication.cast(Authentication.class)
                                .flatMap(auth -> {
                                    boolean hasCustomerRole = auth.getAuthorities().stream()
                                            .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("CUSTOMER"));
                                    return Mono.just(new AuthorizationDecision(!hasCustomerRole));
                                }))
                        .anyExchange().authenticated()
                ).csrf(csrf -> csrf.csrfTokenRepository(CookieServerCsrfTokenRepository.withHttpOnlyFalse()))
                .httpBasic(withDefaults()) //чтобы получать ответ от любого другого клиента, кроме браузера
                .formLogin(withDefaults())
                .authenticationManager(customAuthenticationManager());
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ReactiveAuthenticationManager customAuthenticationManager() {
        ReactiveAuthenticationManager manager = new CustomAuthenticationProvider(context.getBean(MyUserDetailsService.class));
        return manager;
    }

    /**
     * Сервер с репозиторием CSRF-токенов, внедряем его как Bean в Spring Context
     * @return
     */
    @Bean
    public ServerCsrfTokenRepository cookieServerCsrfTokenRepository() {
        return CookieServerCsrfTokenRepository.withHttpOnlyFalse();
    }
}