package com.banco.pagos.domain.model;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder(toBuilder = true)
public class Cliente {

    Long id;

    TipoIdentificacionEnum tipoIdentificacion;
    String numeroIdentificacion;

    BigDecimal valorNomina;

    String codigoCliente;

    String nombres;
    String apellidos;
    LocalDate fechaNacimiento;

    String correo;
    String celular;
    LocalDate fechaIngreso;
}
