package heavenboards.composer.service.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment;
import com.netflix.graphql.dgs.DgsQuery;
import lombok.RequiredArgsConstructor;
import transfer.contract.api.AuthenticationApi;
import transfer.contract.domain.authentication.AuthenticationOperationResultTo;
import transfer.contract.domain.authentication.AuthenticationRequestTo;
import transfer.contract.domain.authentication.RegistrationRequestTo;

/**
 * Компонент для graphql-запросов на user-service.
 */
@DgsComponent
@RequiredArgsConstructor
public class AuthenticationFetcher {
    /**
     * Api-клиент для регистрации / аутентификации.
     */
    private final AuthenticationApi authenticationApi;

    /**
     * Класс для конвертации параметров.
     */
    private final ObjectMapper objectMapper;

    /**
     * Запрос на регистрацию.
     *
     * @param dfe - параметры
     * @return результат операции с токеном
     */
    @DgsQuery
    @SuppressWarnings("unused")
    public AuthenticationOperationResultTo register(DgsDataFetchingEnvironment dfe) {
        Object rawRequest = dfe.getArgument("request");
        RegistrationRequestTo request = objectMapper
            .convertValue(rawRequest, RegistrationRequestTo.class);
        return authenticationApi.register(request);
    }

    /**
     * Запрос на аутентификацию.
     *
     * @param dfe - параметры
     * @return результат операции с токеном
     */
    @DgsQuery
    @SuppressWarnings("unused")
    public AuthenticationOperationResultTo authenticate(DgsDataFetchingEnvironment dfe) {
        Object rawRequest = dfe.getArgument("request");
        AuthenticationRequestTo request = objectMapper
            .convertValue(rawRequest, AuthenticationRequestTo.class);
        return authenticationApi.authenticate(request);
    }
}
