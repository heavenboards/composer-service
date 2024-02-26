package heavenboards.composer.service.graphql.configuration;

import com.netflix.graphql.dgs.DgsScalar;
import graphql.language.BooleanValue;
import graphql.language.FloatValue;
import graphql.language.IntValue;
import graphql.language.NullValue;
import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Абстрактный класс для упрощенного создания кастомных скалярных типов.
 *
 * @param <I> Тип внутри программы.
 * @param <O> Тип в который происходит сериализация.
 *            Возможны следующие 4 типа: Float, String, Integer или Boolean.
 */
public abstract class AbstractValueObjectScalar<I, O> implements Coercing<I, O> {

    /**
     * Класс типа внутри программы.
     */
    private final Class<I> inputType;
    /**
     * Класс типа, в который происходит сериализация.
     */
    private final Class<O> outputType;
    /**
     * Наименование.
     */
    private final String name;

    /**
     * Конструктор по умолчанию.
     */
    public AbstractValueObjectScalar() {
        this(null);
    }

    /**
     * Основной конструктор.
     *
     * @param scalarName - имя скалярного типа, используемого в GraphQL-схемах.
     */
    @SuppressWarnings("unchecked")
    public AbstractValueObjectScalar(final @Nullable String scalarName) {
        Type[] actualTypeArguments = getActualTypeArguments();
        if (actualTypeArguments[0] instanceof Class) {
            this.inputType = (Class<I>) actualTypeArguments[0];
        } else if (actualTypeArguments[0] instanceof ParameterizedType) {
            this.inputType = (Class<I>) ((ParameterizedType) actualTypeArguments[0]).getRawType();
        } else {
            throw new IllegalStateException("cannot determine input type");
        }
        this.outputType = (Class<O>) actualTypeArguments[1];
        this.name = Optional.ofNullable(scalarName)
            .orElseGet(() -> Objects
                .requireNonNull(
                    AnnotationUtils.findAnnotation(getClass(), DgsScalar.class)
                )
                .name()
            );

        Assert.isTrue(
            Stream.of(Float.class, String.class, Integer.class, Boolean.class)
                .anyMatch(this.outputType::isAssignableFrom),
            () -> "Expected Float, String, Integer or Boolean, but it is not: "
                + this.outputType
        );
    }

    /**
     * Метод, осуществляющий десериализацию.
     *
     * @param value Значение полученное от клиента.
     * @return Десериализованное представление.
     */
    protected abstract I deserializeHelper(O value);

    /**
     * Метод, осуществляющий сериализацию.
     *
     * @param value Value-объект из программы.
     * @return Сериализованное представление объекта.
     */
    protected abstract O serializeHelper(I value);

    /**
     * Сериализация.
     *
     * @param value is never null
     * @return значение OutputType
     * @throws CoercingSerializeException - при ошибке
     */
    @Override
    @SuppressWarnings("deprecation")
    public O serialize(final @NotNull Object value) throws CoercingSerializeException {
        if (outputType.isAssignableFrom(value.getClass())) {
            return outputType.cast(value);
        }
        if (inputType.isAssignableFrom(value.getClass())) {
            return serializeHelper(inputType.cast(value));
        }
        throw new CoercingSerializeException(
            String.format("type %s can be serialized only from %s or %s but %s was given",
                this.name, outputType.getSimpleName(),
                inputType.getName(), value.getClass().getName())
        );
    }

    /**
     * Парсинг значения.
     *
     * @param value is never null
     * @return значение InputType
     * @throws CoercingParseValueException - при ошибке
     */
    @Override
    @SuppressWarnings("deprecation")
    public I parseValue(final Object value) throws CoercingParseValueException {
        if (outputType.isAssignableFrom(value.getClass())) {
            try {
                return deserializeHelper(outputType.cast(value));
            } catch (Exception exception) {
                throw new CoercingParseValueException(exception.getMessage(), exception);
            }
        }
        throw new CoercingParseValueException(
            String.format("type %s accepts only %s values", this.name, outputType.getSimpleName())
        );
    }

    /**
     * Парсинг литерала.
     *
     * @param node is never null
     * @return значение типа InputType
     * @throws CoercingParseLiteralException - при некорректном типе node
     */
    @Override
    @SuppressWarnings("deprecation")
    public I parseLiteral(final @NotNull Object node) throws CoercingParseLiteralException {
        try {
            if (node instanceof NullValue) {
                return null;
            }
            if (node instanceof FloatValue && outputType.isAssignableFrom(Float.class)) {
                return deserializeHelper(outputType.cast(((FloatValue) node)
                    .getValue().floatValue()));
            }
            if (node instanceof StringValue && outputType.isAssignableFrom(String.class)) {
                return deserializeHelper(outputType.cast(((StringValue) node).getValue()));
            }
            if (node instanceof IntValue && outputType.isAssignableFrom(Integer.class)) {
                return deserializeHelper(outputType.cast(((IntValue) node).getValue().intValue()));
            }
            if (node instanceof BooleanValue && outputType.isAssignableFrom(Boolean.class)) {
                return deserializeHelper(outputType.cast(((BooleanValue) node).isValue()));
            }
        } catch (Exception exception) {
            throw new CoercingParseValueException(
                String.format("an error occurred while trying to parse GraphQL scalar type %s: %s",
                    this.name, exception.getMessage()
                ),
                exception
            );
        }
        throw new CoercingParseValueException(
            String.format("type %s accepts only %s values", this.name, outputType.getSimpleName())
        );
    }

    private Type[] getActualTypeArguments() {
        Class<?> type = getClass();
        while (type.getSuperclass() != AbstractValueObjectScalar.class) {
            type = type.getSuperclass();
        }
        ParameterizedType generic = (ParameterizedType) type.getGenericSuperclass();
        return generic.getActualTypeArguments();
    }
}
