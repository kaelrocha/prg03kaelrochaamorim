package br.com.ifba.infrastructure.util;

import br.com.ifba.usuario.entity.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.Properties;
import java.util.function.Supplier;
import org.flywaydb.core.Flyway;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;

@org.springframework.context.annotation.Configuration(proxyBeanMethods = false)
public class JPAUtil {

    @Bean(destroyMethod = "close")
    @Lazy
    EntityManagerFactory entityManagerFactory(Environment ambiente) {
        String url = ambiente.getRequiredProperty("app.datasource.url");
        String usuario = ambiente.getRequiredProperty("app.datasource.username");
        String senha = ambiente.getRequiredProperty("app.datasource.password");

        // O Hibernate valida as tabelas depois que o Flyway aplica as migrations.
        Flyway.configure()
                .dataSource(url, usuario, senha)
                .locations("classpath:db/migration")
                .load()
                .migrate();

        Properties config = new Properties();
        config.setProperty("jakarta.persistence.jdbc.driver",
                ambiente.getRequiredProperty("app.datasource.driver"));
        config.setProperty("jakarta.persistence.jdbc.url", url);
        config.setProperty("jakarta.persistence.jdbc.user", usuario);
        config.setProperty("jakarta.persistence.jdbc.password", senha);
        config.setProperty("hibernate.hbm2ddl.auto",
                ambiente.getRequiredProperty("app.jpa.schema"));
        config.setProperty("hibernate.show_sql",
                ambiente.getRequiredProperty("app.jpa.show-sql"));

        return new Configuration()
                .addAnnotatedClass(Usuario.class)
                .setProperties(config)
                .buildSessionFactory();
    }

    @Bean
    Supplier<EntityManager> fornecedorEntityManager(ObjectProvider<EntityManagerFactory> fabrica) {
        return () -> fabrica.getObject().createEntityManager();
    }
}
