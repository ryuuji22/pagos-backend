package com.banco.pagos.application.service;

import java.util.Optional;
import java.util.function.Function;

@FunctionalInterface
public interface ValidationRule<T> {
    Optional<String> validate(T value);

    static <T> ValidationRule<T> of(Function<T, Optional<String>> fn) {
        return fn::apply;
    }
}