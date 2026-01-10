package com.banco.pagos.application.usecase;

import com.banco.pagos.application.dto.CargaNominaResultadoDto;
import com.banco.pagos.application.dto.CreacionEstadoEnum;
import com.banco.pagos.application.dto.ItemResultadoDto;
import com.banco.pagos.application.service.NominaTxtParserService;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

@ApplicationScoped
public class CargarNominaUseCase {

    @Inject
    NominaTxtParserService parser;
    @Inject
    CrearClienteCuentaUseCase crearClienteCuenta;

    public CargaNominaResultadoDto ejecutar(InputStream txtStream) {
        var resultados = new ArrayList<ItemResultadoDto>();

        int total = 0, procesadas = 0, creadas = 0, yaExistian = 0, noEncontradas = 0, conErrores = 0;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(txtStream, StandardCharsets.UTF_8))) {
            String line;
            int lineNumber = 0;
            while ((line = br.readLine()) != null) {
                lineNumber++;
                total++;

                var parsed = parser.parseLine(line, lineNumber);
                if (!parsed.isOk()) {
                    conErrores++;
                    resultados.add(ItemResultadoDto.builder()
                            .linea(lineNumber)
                            .estado(CreacionEstadoEnum.ERROR)
                            .mensaje(parsed.error())
                            .build());
                    continue;
                }

                var res = crearClienteCuenta.ejecutar(parsed.registro());
                procesadas++;

                switch (res.getEstado()) {
                    case CREADO ->
                        creadas++;
                    case YA_EXISTE ->
                        yaExistian++;
                    case NO_ENCONTRADO_DATABOOK ->
                        noEncontradas++;
                    case ERROR ->
                        conErrores++;
                }

                resultados.add(ItemResultadoDto.builder()
                        .linea(lineNumber)
                        .tipoIdentificacion(res.getTipoIdentificacion())
                        .numeroIdentificacion(res.getNumeroIdentificacion())
                        .estado(res.getEstado())
                        .mensaje(res.getMensaje())
                        .clienteId(res.getClienteId())
                        .codigoCliente(res.getCodigoCliente())
                        .cuentaId(res.getCuentaId())
                        .numeroCuenta(res.getNumeroCuenta())
                        .build());
            }
        } catch (Exception e) {
            // si falla lectura del archivo completo, devolvemos un resumen con un error global
            resultados.add(ItemResultadoDto.builder()
                    .linea(0)
                    .estado(CreacionEstadoEnum.ERROR)
                    .mensaje("Error leyendo archivo: " + e.getMessage())
                    .build());
            conErrores++;
        }

        return CargaNominaResultadoDto.builder()
                .totalLineas(total)
                .procesadas(procesadas)
                .creadas(creadas)
                .yaExistian(yaExistian)
                .noEncontradasDatabook(noEncontradas)
                .conErrores(conErrores)
                .resultados(resultados)
                .build();
    }
}
