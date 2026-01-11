# Pagos Backend

Backend para carga masiva de nómina.

## Tech stack
- Java 18+
- Jakarta EE
- WildFly
- JPA / Hibernate
- Liquibase
- MySQL

## Levantar el proyecto
1. Configurar datasource JNDI `java:/PayrollDS`
2. Desplegar el WAR en WildFly.
3. Las migraciones de las tablas a la base se ejecutaran en el arranque gracias a Liquibase.

## Endpoint principal
POST /pagos/api/v1/payroll/upload