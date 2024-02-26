package heavenboards.composer.service.user;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment;
import com.netflix.graphql.dgs.DgsQuery;
import lombok.RequiredArgsConstructor;
import transfer.contract.api.UserApi;
import transfer.contract.domain.user.UserTo;

import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Компонент для graphql-запросов на user-service.
 */
@DgsComponent
@RequiredArgsConstructor
public class UserFetcher {
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
    @DgsQuery
    @SuppressWarnings("unused")
    public UserTo findUserByUsername(final String username) {
        return userApi.findUserByUsername(username);
    }

    /**
     * Найти всех пользователей по идентификаторам.
     *
     * @param dfe - параметры запроса
     * @return пользователи
     */
    @DgsQuery
    @SuppressWarnings("unused")
    public List<UserTo> findUsersByIds(final DgsDataFetchingEnvironment dfe) {
        Set<UUID> ids = Set.of(dfe.getArgument("ids"));
        return userApi.findUsersByIds(ids);
    }
}
