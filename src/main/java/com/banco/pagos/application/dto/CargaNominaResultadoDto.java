package com.banco.pagos.application.dto;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class CargaNominaResultadoDto {
    int totalLineas;
    int procesadas;
    int creadas;
    int yaExistian;
    int noEncontradasDatabook;
    int conErrores;

    List<ItemResultadoDto> resultados; 
}
