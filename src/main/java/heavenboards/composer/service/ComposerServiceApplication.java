package heavenboards.composer.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/**
 * Главный класс для запуска сервиса.
 */
@SpringBootApplication(scanBasePackages = {
    "heavenboards.composer.service",
    "transfer.contract"
}, exclude = SecurityAutoConfiguration.class)
public class ComposerServiceApplication {
    /**
     * Главный метод для запуска сервиса.
     *
     * @param args - аргументы запуска
     */
    public static void main(final String[] args) {
        SpringApplication.run(ComposerServiceApplication.class, args);
    }
}
