package ru.nsu.fit.directors.businessservice.exceptions;

import ru.nsu.fit.directors.businessservice.model.EntityType;

public class EntityNotFoundException extends BaseException {
    public EntityNotFoundException(EntityType entityType, Long id) {
        super(String.format("Не найден %s с идентификатором %s", entityType.getReadableName(), id));
    }

    public EntityNotFoundException(EntityType entityType, String id) {
        super(String.format("Не найден %s с идентификатором %s", entityType.getReadableName(), id));
    }
}
