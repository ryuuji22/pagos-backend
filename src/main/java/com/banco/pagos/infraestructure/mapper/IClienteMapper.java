package com.banco.pagos.infraestructure.mapper;

import com.banco.pagos.domain.model.Cliente;
import com.banco.pagos.infraestructure.jpa.entity.ClienteEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi", imports = com.banco.pagos.domain.model.TipoIdentificacionEnum.class)
public interface IClienteMapper {

    @Mapping(target = "tipoIdentificacion", expression = "java(TipoIdentificacionEnum.valueOf(e.getTipoIdentificacion()))")
    Cliente toDomain(ClienteEntity e);

    @Mapping(target = "tipoIdentificacion", expression = "java(d.getTipoIdentificacion().name())")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    ClienteEntity toEntity(Cliente d);
}
