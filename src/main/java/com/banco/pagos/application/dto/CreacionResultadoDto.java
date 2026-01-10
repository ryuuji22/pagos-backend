package com.banco.pagos.application.dto;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreacionResultadoDto {

    CreacionEstadoEnum estado;

    String tipoIdentificacion;
    String numeroIdentificacion;

    Long clienteId;
    String codigoCliente;

    Long cuentaId;
    String numeroCuenta;

    BigDecimal valorNomina;

    String mensaje;

}
