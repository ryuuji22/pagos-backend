package com.banco.pagos.application.dto;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ItemResultadoDto {

    int linea;
    String tipoIdentificacion;
    String numeroIdentificacion;

    BigDecimal valorNomina;

    CreacionEstadoEnum estado;
    String mensaje;

    Long clienteId;
    String codigoCliente;

    Long cuentaId;
    String numeroCuenta;
}
