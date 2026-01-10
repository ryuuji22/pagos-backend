package com.banco.pagos.domain.model;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder(toBuilder = true)
public class DatabookInfo {
    String nombres;
    String apellidos;
    LocalDate fechaNacimiento;
}
