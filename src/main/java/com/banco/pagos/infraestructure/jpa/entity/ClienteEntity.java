package com.banco.pagos.infraestructure.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "cliente",
        uniqueConstraints = {
            @UniqueConstraint(name = "uk_cliente_identificacion",
                    columnNames = {"tipo_identificacion", "numero_identificacion"}),
            @UniqueConstraint(name = "uk_cliente_codigo",
                    columnNames = {"codigo_cliente"})
        }
)
@Getter
@Setter
@NoArgsConstructor
public class ClienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tipo_identificacion", nullable = false, length = 1)
    private String tipoIdentificacion;

    @Column(name = "numero_identificacion", nullable = false, length = 20)
    private String numeroIdentificacion;

    @Column(name = "codigo_cliente", nullable = false, length = 20)
    private String codigoCliente;

    @Column(length = 100)
    private String nombres;

    @Column(length = 100)
    private String apellidos;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column(length = 120)
    private String correo;

    @Column(length = 10)
    private String celular;

    @Column(name = "fecha_ingreso")
    private LocalDate fechaIngreso;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "valor_nomina", nullable = false, precision = 18, scale = 2)
    private BigDecimal valorNomina;

    @PrePersist
    void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (codigoCliente == null || codigoCliente.isBlank()) {
            codigoCliente = UUID.randomUUID().toString();
        }
    }
}
