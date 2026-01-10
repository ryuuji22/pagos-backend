package com.banco.pagos.infraestructure.jpa.repository;

import com.banco.pagos.domain.model.Cuenta;
import com.banco.pagos.domain.port.CuentaRepositoryPort;
import com.banco.pagos.infraestructure.jpa.entity.CuentaEntity;
import com.banco.pagos.infraestructure.mapper.ICuentaMapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@ApplicationScoped
public class CuentaRepositoryAdapter implements CuentaRepositoryPort {

    @PersistenceContext(unitName = "pagosPU")
    EntityManager em;

    @Inject
    ICuentaMapper mapper;

    @Inject
    ClienteRepositoryAdapter clienteRepoAdapter;

    @Override
    public Cuenta save(Cuenta cuenta) {
        CuentaEntity entity = mapper.toEntity(cuenta);
        entity.setCliente(clienteRepoAdapter.getEntityReference(cuenta.getClienteId()));

        em.persist(entity);
        em.flush();
        return mapper.toDomain(entity);
    }
}
