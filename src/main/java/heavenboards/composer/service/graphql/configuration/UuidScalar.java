package heavenboards.composer.service.graphql.configuration;

import com.netflix.graphql.dgs.DgsScalar;

import java.util.UUID;

/**
 * Скалярный тип для UUID.
 */
@DgsScalar(name = "UUID")
public class UuidScalar extends AbstractValueObjectScalar<UUID, String> {
    /**
     * Метод, осуществляющий десериализацию UUID.
     *
     * @param value - значение String, полученное от клиента
     * @return значение типа UUID
     */
    @Override
    protected UUID deserializeHelper(String value) {
        return UUID.fromString(value);
    }

    /**
     * Метод, осуществляющий сериализацию UUID в String.
     *
     * @param value - значение UUID из программы.
     * @return строковое отображение объекта
     */
    @Override
    protected String serializeHelper(UUID value) {
        return value.toString();
    }
}
