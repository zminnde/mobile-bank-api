package com.seb.mobilebankapi.util;

import jakarta.persistence.EntityNotFoundException;

public record DataAccessUtils() {

    public static <T, ID> T getEntity(EntityFinder<T, ID> entityFinder, ID identifier) {
        return entityFinder
                .findBy(identifier)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found"));
    }
}