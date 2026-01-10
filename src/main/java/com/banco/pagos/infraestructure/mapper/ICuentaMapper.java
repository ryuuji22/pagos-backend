package com.banco.pagos.infraestructure.mapper;

import com.banco.pagos.domain.model.Cuenta;
import com.banco.pagos.infraestructure.jpa.entity.CuentaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface ICuentaMapper {

  @Mapping(target = "clienteId", source = "cliente.id")
  Cuenta toDomain(CuentaEntity e);

  @Mapping(target = "cliente", ignore = true)   // se setea fuera con getReference
  @Mapping(target = "createdAt", ignore = true) // lo pone @PrePersist
  @Mapping(target = "id", ignore = true)        // lo genera la BD
  CuentaEntity toEntity(Cuenta d);
}

