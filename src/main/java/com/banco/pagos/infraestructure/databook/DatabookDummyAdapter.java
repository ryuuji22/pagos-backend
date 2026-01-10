package com.banco.pagos.infraestructure.databook;

import com.banco.pagos.domain.model.DatabookInfo;
import com.banco.pagos.domain.model.TipoIdentificacionEnum;
import com.banco.pagos.domain.port.DatabookPort;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

@ApplicationScoped
public class DatabookDummyAdapter implements DatabookPort {

    private static final Map<String, DatabookInfo> DATA = Map.of(
            "C|0102030405", DatabookInfo.builder()
                    .nombres("Juan Carlos")
                    .apellidos("Perez Gomez")
                    .fechaNacimiento(LocalDate.of(1990, 5, 12))
                    .build(),
            "P|A12345X9", DatabookInfo.builder()
                    .nombres("Maria Fernanda")
                    .apellidos("Lopez Ruiz")
                    .fechaNacimiento(LocalDate.of(1988, 11, 3))
                    .build()
    );

    @Override
    public Optional<DatabookInfo> findById(TipoIdentificacionEnum tipo, String numero) {
        return Optional.ofNullable(DATA.get(tipo.name() + "|" + numero));
    }
}
