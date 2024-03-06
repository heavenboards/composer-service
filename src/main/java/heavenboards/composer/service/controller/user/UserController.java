package heavenboards.composer.service.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import transfer.contract.api.UserApi;
import transfer.contract.domain.user.UserTo;

import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Контроллер для взаимодействия с пользователями.
 */
@RestController
@CrossOrigin
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    /**
     * Api-клиент для сервиса пользователей.
     */
    private final UserApi userApi;

    /**
     * Получение пользователя по username.
     *
     * @param username - username
     * @return найденный пользователь
     */
    @GetMapping("/{username}")
    @Operation(summary = "Получение пользователя по username")
    public UserTo findUserByUsername(final @PathVariable String username) {
        return userApi.findUserByUsername(username);
    }

    /**
     * Получение списка пользователей по идентификаторам.
     *
     * @param ids - идентификаторы пользователей, которых мы ищем
     * @return to-модели найденных пользователей
     */
    @GetMapping
    @Operation(summary = "Получение списка пользователей по идентификаторам")
    public List<UserTo> findUsersByIds(final @RequestBody Set<UUID> ids) {
        return userApi.findUsersByIds(ids);
    }
}
