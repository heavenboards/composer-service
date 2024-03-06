package heavenboards.composer.service.controller.board;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import transfer.contract.api.BoardApi;
import transfer.contract.domain.board.BoardOperationResultTo;
import transfer.contract.domain.board.BoardTo;

import java.util.UUID;

/**
 * Контроллер для взаимодействия с проектами.
 */
@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/v1/board")
@Tag(name = "BoardController", description = "Контроллер для взаимодействия с досками")
public class BoardController {
    /**
     * Api-клиент для сервиса досок.
     */
    private final BoardApi boardApi;

    /**
     * Поиск доски по идентификатору.
     *
     * @param id - идентификатор доски
     * @return данные доски
     */
    @GetMapping("/{id}")
    @Operation(summary = "Поиск доски по идентификатору")
    public BoardTo findBoardById(final @PathVariable UUID id) {
        return boardApi.findBoardById(id);
    }

    /**
     * Создание доски.
     *
     * @param board - to-модель создаваемой доски
     * @return результат операции создания
     */
    @PostMapping
    @Operation(summary = "Создать доску в проекте")
    public BoardOperationResultTo createBoard(final @Valid @RequestBody BoardTo board) {
        return boardApi.createBoard(board);
    }
}
