package com.seb.mobilebankapi.util;

import java.util.Optional;

@FunctionalInterface
public interface EntityFinder<T, ID> {
    Optional<T> findBy(ID searchIdentifier);
}
