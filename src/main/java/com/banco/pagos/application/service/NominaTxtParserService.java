package com.banco.pagos.application.service;

import com.banco.pagos.domain.model.RegistroNomina;
import com.banco.pagos.domain.model.TipoIdentificacionEnum;

import jakarta.enterprise.context.ApplicationScoped;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.banco.pagos.application.service.NominaValidations.*;

@ApplicationScoped
public class NominaTxtParserService {

    public ParseResult parseLine(String rawLine, int lineNumber) {
        String line = rawLine == null ? "" : rawLine.trim();
        if (line.isEmpty()) {
            return ParseResult.error(lineNumber, "Línea vacía");
        }

        String[] p = line.split(",", -1);
        if (p.length != 6) {
            return ParseResult.error(lineNumber, "Formato no válido: se esperaban 6 columnas separadas por ','");
        }

        String tipo = p[0].trim();
        String numeroId = p[1].trim();
        String fechaIngresoStr = p[2].trim();
        String valorNominaStr = p[3].trim();
        String correo = p[4].trim();
        String celular = p[5].trim();

        Optional<String> err = firstError(TIPO_ID_RULES, tipo)
                .or(() -> firstError(NUMERO_ID_RULES, numeroId))
                .or(() -> validateFecha(fechaIngresoStr))
                .or(() -> validateValor(valorNominaStr))
                .or(() -> firstError(CORREO_RULES, correo))
                .or(() -> firstError(CELULAR_RULES, celular));

        if (err.isPresent()) {
            return ParseResult.error(lineNumber, err.get());
        }

        RegistroNomina registro = RegistroNomina.builder()
                .tipoIdentificacion(TipoIdentificacionEnum.valueOf(tipo))
                .numeroIdentificacion(numeroId)
                .fechaIngreso(LocalDate.parse(fechaIngresoStr, DATE_FMT))
                .valorNomina(new BigDecimal(valorNominaStr))
                .correo(correo)
                .celular(celular)
                .build();

        return ParseResult.ok(lineNumber, registro);
    }

    private static Optional<String> firstError(List<ValidationRule<String>> rules, String value) {
        return rules.stream()
                .map(r -> r.validate(value))
                .flatMap(Optional::stream)
                .findFirst();
    }

    public record ParseResult(int lineNumber, RegistroNomina registro, String error) {

        public static ParseResult ok(int lineNumber, RegistroNomina registro) {
            return new ParseResult(lineNumber, registro, null);
        }

        public static ParseResult error(int lineNumber, String error) {
            return new ParseResult(lineNumber, null, error);
        }

        public boolean isOk() {
            return registro != null;
        }
    }
}
