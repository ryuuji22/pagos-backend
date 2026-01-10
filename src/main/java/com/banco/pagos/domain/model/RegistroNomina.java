package com.banco.pagos.domain.model;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;

@Value
@Builder(toBuilder = true)
public class RegistroNomina {
    TipoIdentificacionEnum tipoIdentificacion;
    String numeroIdentificacion;

    LocalDate fechaIngreso;
    BigDecimal valorNomina;

    String correo;
    String celular;
}
