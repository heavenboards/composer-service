package heavenboards.composer.service.controller.task;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import transfer.contract.api.GroupApi;
import transfer.contract.domain.group.GroupOperationResultTo;
import transfer.contract.domain.group.GroupTo;

/**
 * Контроллер для взаимодействия с группами задач.
 */
@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/v1/group")
@Tag(name = "GroupController", description = "Контроллер для взаимодействия с группами задач")
public class GroupController {
    /**
     * Api-клиент для взаимодействия с группами задач.
     */
    private final GroupApi groupApi;

    /**
     * Создать группу задач.
     *
     * @param group - to-модель группы задач
     * @return результат операции создания группы задач
     */
    @PostMapping
    @Operation(summary = "Создание группы задач")
    public GroupOperationResultTo createGroup(final @Valid @RequestBody GroupTo group) {
        return groupApi.createGroup(group);
    }
}
