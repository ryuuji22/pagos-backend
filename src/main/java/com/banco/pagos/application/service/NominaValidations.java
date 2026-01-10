package com.banco.pagos.application.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public final class NominaValidations {

    private NominaValidations() {}

    public static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static final Pattern EMAIL_RE = Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");
    private static final Pattern CEL_RE = Pattern.compile("^\\d{10}$");
    private static final Pattern ALNUM_RE = Pattern.compile("^[a-zA-Z0-9]+$");
    private static final Pattern LETTERS_RE = Pattern.compile("^[a-zA-Z]+$");

    public static final List<ValidationRule<String>> TIPO_ID_RULES = List.of(
            ValidationRule.of(v -> isBlank(v) ? Optional.of("Tipo de identificación requerido") : Optional.empty()),
            ValidationRule.of(v -> !LETTERS_RE.matcher(v).matches() ? Optional.of("Tipo de identificación debe contener solo letras") : Optional.empty()),
            ValidationRule.of(v -> !(v.equals("C") || v.equals("P")) ? Optional.of("Tipo de identificación no válido (solo C o P)") : Optional.empty())
    );

    public static final List<ValidationRule<String>> NUMERO_ID_RULES = List.of(
            ValidationRule.of(v -> isBlank(v) ? Optional.of("Número de identificación requerido") : Optional.empty()),
            ValidationRule.of(v -> !ALNUM_RE.matcher(v).matches() ? Optional.of("Número de identificación debe ser alfanumérico") : Optional.empty())
    );

    public static final List<ValidationRule<String>> CORREO_RULES = List.of(
            ValidationRule.of(v -> isBlank(v) ? Optional.of("Correo requerido") : Optional.empty()),
            ValidationRule.of(v -> !EMAIL_RE.matcher(v).matches() ? Optional.of("Correo electrónico no válido") : Optional.empty())
    );

    public static final List<ValidationRule<String>> CELULAR_RULES = List.of(
            ValidationRule.of(v -> isBlank(v) ? Optional.of("Celular requerido") : Optional.empty()),
            ValidationRule.of(v -> !CEL_RE.matcher(v).matches() ? Optional.of("Número de celular debe contener exactamente 10 dígitos") : Optional.empty())
    );

    public static Optional<String> validateFecha(String v) {
        if (isBlank(v)) return Optional.of("Fecha de ingreso requerida");
        try {
            LocalDate.parse(v, DATE_FMT);
            return Optional.empty();
        } catch (Exception e) {
            return Optional.of("Fecha de ingreso no válida, formato esperado yyyy-MM-dd");
        }
    }

    public static Optional<String> validateValor(String v) {
        if (isBlank(v)) return Optional.of("Valor del pago de nómina requerido");
        try {
            new BigDecimal(v);
            return Optional.empty();
        } catch (Exception e) {
            return Optional.of("Valor del pago de nómina debe ser numérico");
        }
    }

    private static boolean isBlank(String v) {
        return v == null || v.trim().isEmpty();
    }
}