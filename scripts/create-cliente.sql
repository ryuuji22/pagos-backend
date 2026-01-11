CREATE TABLE `cliente` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `tipo_identificacion` varchar(1) NOT NULL,
  `numero_identificacion` varchar(20) NOT NULL,
  `codigo_cliente` char(36) NOT NULL,
  `nombres` varchar(100) DEFAULT NULL,
  `apellidos` varchar(100) DEFAULT NULL,
  `fecha_nacimiento` date DEFAULT NULL,
  `correo` varchar(120) DEFAULT NULL,
  `celular` varchar(10) DEFAULT NULL,
  `fecha_ingreso` date DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `valor_nomina` decimal(18,2) NOT NULL DEFAULT '0.00',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_cliente_identificacion` (`tipo_identificacion`,`numero_identificacion`),
  UNIQUE KEY `codigo_cliente` (`codigo_cliente`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
