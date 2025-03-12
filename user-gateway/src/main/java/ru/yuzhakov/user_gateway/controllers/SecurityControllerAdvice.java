package ru.yuzhakov.user_gateway.controllers;

import lombok.AllArgsConstructor;
import org.springframework.security.web.reactive.result.view.CsrfRequestDataValueProcessor;
import org.springframework.security.web.server.csrf.CsrfToken;
import org.springframework.security.web.server.csrf.ServerCsrfTokenRepository;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Аннотация @ControllerAdvice означает, что этот контроллер может быть вызван любой сущностью Spring и в любой момент.
 */
@ControllerAdvice
@AllArgsConstructor
public class SecurityControllerAdvice {

    // Репозиторий инжектируется с помощью @Bean в классе SecurityConfig
    private final ServerCsrfTokenRepository csrfTokenRepository;

    /**
     * В шаблоне thymeleaf есть скрытое поле, содержащее csrf токен, чтобы его туда вставить необходим этот метод.
     * @param exchange что-то вроде сервлета, только для асинхронного программирования.
     * @return csrf токен
     */
    @ModelAttribute
    Mono<CsrfToken> csrfToken(ServerWebExchange exchange) {
        // пробует получить токен из запроса
        Mono<CsrfToken> csrfToken = exchange.getAttribute(CsrfToken.class.getName());
        if (csrfToken != null) {
            // возвращаем токен и он вставляется в шаблон
            return csrfToken.doOnSuccess(token -> exchange.getAttributes()
                    .put(CsrfRequestDataValueProcessor.DEFAULT_CSRF_ATTR_NAME, token));
        } else {
            // создаем новый токен, сохраняем в репозиторий и возвращаем его
            return Mono.defer(() -> {
                CsrfToken token = (CsrfToken) csrfTokenRepository.generateToken(exchange);
                csrfTokenRepository.saveToken(exchange, token);
                return Mono.just(token);
            });
        }
    }
}
