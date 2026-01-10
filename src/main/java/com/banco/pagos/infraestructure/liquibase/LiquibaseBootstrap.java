package com.banco.pagos.infraestructure.liquibase;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import liquibase.command.CommandScope;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;

import javax.naming.InitialContext;
import javax.sql.DataSource;

@WebListener
public class LiquibaseBootstrap implements ServletContextListener {

    private static final String CHANGELOG = "db/changelog/db.changelog-master.yml";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            DataSource ds = (DataSource) new InitialContext().lookup("java:/PayrollDS");

            try (var conn = ds.getConnection()) {
                Database database = DatabaseFactory.getInstance()
                        .findCorrectDatabaseImplementation(new JdbcConnection(conn));

                new CommandScope("update")
                        .addArgumentValue("database", database)
                        .addArgumentValue("changelogFile", CHANGELOG)
                        .addArgumentValue(
                                "resourceAccessor",
                                new ClassLoaderResourceAccessor(getClass().getClassLoader())
                        )
                        .execute();
            }

            // Log visible en server.log
            System.out.println("[Liquibase] Migraciones aplicadas OK");
        } catch (Exception e) {
            throw new RuntimeException("[Liquibase] Falló la migración", e);
        }
    }
}
