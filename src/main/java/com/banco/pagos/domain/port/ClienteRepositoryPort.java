package com.banco.pagos.domain.port;

import com.banco.pagos.domain.model.Cliente;
import com.banco.pagos.domain.model.TipoIdentificacionEnum;

import java.util.Optional;

public interface ClienteRepositoryPort {
    Optional<Cliente> findByIdentificacion(TipoIdentificacionEnum tipo, String numero);
    Cliente save(Cliente cliente);
}
