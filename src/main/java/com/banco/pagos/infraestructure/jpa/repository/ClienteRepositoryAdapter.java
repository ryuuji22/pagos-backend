package com.banco.pagos.infraestructure.jpa.repository;

import com.banco.pagos.domain.model.Cliente;
import com.banco.pagos.domain.model.TipoIdentificacionEnum;
import com.banco.pagos.domain.port.ClienteRepositoryPort;
import com.banco.pagos.infraestructure.jpa.entity.ClienteEntity;
import com.banco.pagos.infraestructure.mapper.IClienteMapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.Optional;

@ApplicationScoped
public class ClienteRepositoryAdapter implements ClienteRepositoryPort {

    @PersistenceContext(unitName = "pagosPU")
    EntityManager em;

    @Inject
    IClienteMapper mapper;

    @Override
    public Optional<Cliente> findByIdentificacion(TipoIdentificacionEnum tipo, String numero) {
        Optional<ClienteEntity> e = em.createQuery("""
                SELECT c FROM ClienteEntity c
                WHERE c.tipoIdentificacion = :tipo
                  AND c.numeroIdentificacion = :numero
                """, ClienteEntity.class)
                .setParameter("tipo", tipo.name())
                .setParameter("numero", numero)
                .getResultStream()
                .findFirst();

        return e.map(mapper::toDomain);
    }

    @Override
    public Cliente save(Cliente cliente) {
        ClienteEntity entity = mapper.toEntity(cliente);
        em.persist(entity);
        em.flush(); // para obtener id
        return mapper.toDomain(entity);
    }

    // helper para obtener entity cuando se necesita asociar cuenta (uso interno)
    public ClienteEntity getEntityReference(Long id) {
        return em.getReference(ClienteEntity.class, id);
    }
}
