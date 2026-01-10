package com.banco.pagos.application.usecase;

import com.banco.pagos.application.dto.CreacionEstadoEnum;
import com.banco.pagos.application.dto.CreacionResultadoDto;
import com.banco.pagos.domain.model.Cliente;
import com.banco.pagos.domain.model.RegistroNomina;
import com.banco.pagos.domain.port.ClienteRepositoryPort;
import com.banco.pagos.domain.port.CuentaRepositoryPort;
import com.banco.pagos.domain.port.DatabookPort;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;

@ApplicationScoped
public class CrearClienteCuentaUseCase {

    @Inject
    ClienteRepositoryPort clienteRepo;
    @Inject
    CuentaRepositoryPort cuentaRepo;
    @Inject
    DatabookPort databook;

    @Transactional
    public CreacionResultadoDto ejecutar(RegistroNomina registroNomina) {
        String tipo = registroNomina.getTipoIdentificacion().name();
        String numero = registroNomina.getNumeroIdentificacion();

        try {
            // 1) Ya existe?
            var existente = clienteRepo.findByIdentificacion(registroNomina
                    .getTipoIdentificacion(), numero);
            if (existente.isPresent()) {
                var cliente = existente.get();
                return CreacionResultadoDto.builder()
                        .estado(CreacionEstadoEnum.YA_EXISTE)
                        .tipoIdentificacion(tipo)
                        .numeroIdentificacion(numero)
                        .clienteId(cliente.getId())
                        .codigoCliente(cliente.getCodigoCliente())
                        .mensaje("Cliente ya existe")
                        .valorNomina(cliente.getValorNomina())
                        .build();
            }

            // 2) Databook
            var infoOpt = databook.findById(registroNomina.getTipoIdentificacion(), numero);
            if (infoOpt.isEmpty()) {
                return CreacionResultadoDto.builder()
                        .estado(CreacionEstadoEnum.NO_ENCONTRADO_DATABOOK)
                        .tipoIdentificacion(tipo)
                        .numeroIdentificacion(numero)
                        .mensaje("No encontrado en databook")
                        .build();
            }
            var info = infoOpt.get();

            // 3) Crear cliente (UUID se genera en la entidad @PrePersist)
            Cliente creado = clienteRepo.save(Cliente.builder()
                    .tipoIdentificacion(registroNomina.getTipoIdentificacion())
                    .numeroIdentificacion(numero)
                    .nombres(info.getNombres())
                    .apellidos(info.getApellidos())
                    .fechaNacimiento(info.getFechaNacimiento())
                    .correo(registroNomina.getCorreo())
                    .celular(registroNomina.getCelular())
                    .fechaIngreso(registroNomina.getFechaIngreso())
                    .valorNomina(registroNomina.getValorNomina())
                    .build());

            // 4) Crear cuenta
            var cuentaCreada = cuentaRepo.save(com.banco.pagos.domain.model.Cuenta.builder()
                    .clienteId(creado.getId())
                    .saldo(BigDecimal.ZERO)
                    .build());

            return CreacionResultadoDto.builder()
                    .estado(CreacionEstadoEnum.CREADO)
                    .tipoIdentificacion(tipo)
                    .numeroIdentificacion(numero)
                    .clienteId(creado.getId())
                    .codigoCliente(creado.getCodigoCliente())
                    .cuentaId(cuentaCreada.getId())
                    .numeroCuenta(cuentaCreada.getNumeroCuenta())
                    .valorNomina(creado.getValorNomina())
                    .mensaje("OK")
                    .build();

        } catch (Exception e) {
            return CreacionResultadoDto.builder()
                    .estado(CreacionEstadoEnum.ERROR)
                    .tipoIdentificacion(tipo)
                    .numeroIdentificacion(numero)
                    .mensaje("Error creando cliente/cuenta: " + e.getMessage())
                    .build();
        }
    }
}
