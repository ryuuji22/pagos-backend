package com.banco.pagos.domain.model;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder(toBuilder = true)
public class Cuenta {

    Long id;
    Long clienteId;

    String numeroCuenta;
    BigDecimal saldo;
}
