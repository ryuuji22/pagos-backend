CREATE TABLE `cuenta` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cliente_id` bigint NOT NULL,
  `numero_cuenta` char(36) NOT NULL,
  `saldo` decimal(18,2) NOT NULL DEFAULT '0.00',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `numero_cuenta` (`numero_cuenta`),
  KEY `fk_cuenta_cliente` (`cliente_id`),
  CONSTRAINT `fk_cuenta_cliente` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
