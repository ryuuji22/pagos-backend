package com.banco.pagos.adapters.inbound.rest;

import com.banco.pagos.adapters.inbound.rest.dto.RegistroNominaRequest;
import com.banco.pagos.application.usecase.CrearClienteCuentaUseCase;
import com.banco.pagos.domain.model.RegistroNomina;
import com.banco.pagos.domain.model.TipoIdentificacionEnum;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/test-crear")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TestCrearDesdeJsonResource {

    @Inject
    CrearClienteCuentaUseCase useCase;

    @POST
    public Object crear(RegistroNominaRequest req) {
        RegistroNomina registro = RegistroNomina.builder()
                .tipoIdentificacion(TipoIdentificacionEnum
                        .valueOf(req.getTipoIdentificacion()))
                .numeroIdentificacion(req.getNumeroIdentificacion())
                .fechaIngreso(req.getFechaIngreso())
                .valorNomina(req.getValorNomina())
                .correo(req.getCorreo())
                .celular(req.getCelular())
                .build();

        return useCase.ejecutar(registro);
    }
}
