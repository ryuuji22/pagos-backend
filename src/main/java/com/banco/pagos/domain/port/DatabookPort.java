package com.banco.pagos.domain.port;

import com.banco.pagos.domain.model.DatabookInfo;
import com.banco.pagos.domain.model.TipoIdentificacionEnum;

import java.util.Optional;

public interface DatabookPort {

    Optional<DatabookInfo> findById(TipoIdentificacionEnum tipo, String numero);
}
