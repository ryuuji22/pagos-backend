package com.banco.pagos.adapters.inbound.rest;

import com.banco.pagos.application.dto.CargaNominaResultadoDto;
import com.banco.pagos.application.usecase.CargarNominaUseCase;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.InputStream;

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

@Path("/payroll")
@Produces(MediaType.APPLICATION_JSON)
public class PayrollUploadResource {

    @Inject
    CargarNominaUseCase cargarNomina;

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response upload(@MultipartForm MultipartFormDataInput input) {

        var map = input.getFormDataMap();
        if (!map.containsKey("file") || map.get("file").isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Debe enviar el archivo en el campo multipart 'file'")
                    .build();
        }

        try ( var is = map.get("file").get(0).getBody(InputStream.class, null)) {
            CargaNominaResultadoDto resultado = cargarNomina.ejecutar(is);
            return Response.ok(resultado).build();
        } catch (Exception e) {
            return Response.serverError()
                    .entity("Error procesando archivo: " + e.getMessage())
                    .build();
        }
    }

}
