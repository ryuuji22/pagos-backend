package com.banco.pagos.application.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ItemResultadoDto {
    int linea;
    String tipoIdentificacion;
    String numeroIdentificacion;

    CreacionEstadoEnum estado;
    String mensaje;

    Long clienteId;
    String codigoCliente;

    Long cuentaId;
    String numeroCuenta;
}
