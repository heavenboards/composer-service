package heavenboards.composer.service.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import transfer.contract.api.AuthenticationApi;
import transfer.contract.domain.authentication.AuthenticationOperationResultTo;
import transfer.contract.domain.user.UserTo;

/**
 * Контроллер для регистрации и аутентификации.
 */
@RestController
@CrossOrigin
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    /**
     * Api-клиент для регистрации / аутентификации.
     */
    private final AuthenticationApi authenticationApi;

    /**
     * Запрос на регистрацию пользователя.
     *
     * @param user - данные пользователя для регистрации
     * @return результат операции с токеном
     */
    @PostMapping("/register")
    @Operation(summary = "Запрос на регистрацию пользователя")
    public AuthenticationOperationResultTo register(final @Valid @RequestBody UserTo user) {
        return authenticationApi.register(user);
    }

    /**
     * Запрос на аутентификацию пользователя.
     *
     * @param user - данные пользователя для аутентификации
     * @return результат операции с токеном
     */
    @PostMapping("/authenticate")
    @Operation(summary = "Запрос на аутентификацию пользователя")
    public AuthenticationOperationResultTo authenticate(final @Valid @RequestBody UserTo user) {
        return authenticationApi.authenticate(user);
    }
}
