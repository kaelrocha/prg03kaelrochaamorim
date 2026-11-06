package br.com.ifba.infrastructure.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.flywaydb.core.Flyway;

public class JPAUtil {

    private static final EntityManagerFactory FACTORY;

    // Executado uma única vez, quando a classe é carregada.
    // Ordem importa: primeiro o Flyway cria/atualiza o schema,
    // depois o Hibernate sobe (e, com validate, confere as tabelas).
    static {
        Properties db = carregarCredenciais();

        Flyway.configure()
                .dataSource(
                        db.getProperty("db.url"),
                        db.getProperty("db.user"),
                        db.getProperty("db.password"))
                .locations("classpath:db/migration")
                .load()
                .migrate();

        FACTORY = Persistence.createEntityManagerFactory("prg03PU");
    }

    private JPAUtil() {
    }

    public static EntityManager getEntityManager() {
        return FACTORY.createEntityManager();
    }

    // As credenciais ficam em db.properties, que está no .gitignore
    private static Properties carregarCredenciais() {
        try (InputStream in = JPAUtil.class.getResourceAsStream("/db.properties")) {
            if (in == null) {
                throw new IllegalStateException(
                        "Arquivo db.properties não encontrado em src/main/resources. "
                        + "Copie o db.properties.exemplo e preencha com a sua conexão.");
            }
            Properties props = new Properties();
            props.load(in);
            return props;
        } catch (IOException e) {
            throw new IllegalStateException("Não foi possível ler o db.properties.", e);
        }
    }
}