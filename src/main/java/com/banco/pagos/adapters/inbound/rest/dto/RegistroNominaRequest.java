package com.banco.pagos.adapters.inbound.rest.dto;

import jakarta.json.bind.annotation.JsonbDateFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class RegistroNominaRequest {

    private String tipoIdentificacion;     // "C" o "P"
    private String numeroIdentificacion;

    @JsonbDateFormat("yyyy-MM-dd")
    private LocalDate fechaIngreso;

    private BigDecimal valorNomina;

    private String correo;
    private String celular;
}
