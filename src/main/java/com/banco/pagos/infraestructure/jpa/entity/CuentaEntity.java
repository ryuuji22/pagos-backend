package com.banco.pagos.infraestructure.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "cuenta",
        uniqueConstraints = {
            @UniqueConstraint(name = "uk_cuenta_numero", columnNames = {"numero_cuenta"})
        }
)
@Getter
@Setter
@NoArgsConstructor
public class CuentaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private ClienteEntity cliente;

    @Column(name = "numero_cuenta", nullable = false, length = 20)
    private String numeroCuenta;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal saldo;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (saldo == null) {
            saldo = BigDecimal.ZERO;
        }
        if (numeroCuenta == null || numeroCuenta.isBlank()) {
            numeroCuenta = UUID.randomUUID().toString();
        }
    }
}
