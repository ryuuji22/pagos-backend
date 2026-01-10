package com.banco.pagos.domain.port;

import com.banco.pagos.domain.model.Cuenta;

public interface CuentaRepositoryPort {
    Cuenta save(Cuenta cuenta);
}
