package br.com.ifba.infrastructure.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {

    // criada uma única vez, quando a classe é carregada
    private static final EntityManagerFactory FACTORY =
            Persistence.createEntityManagerFactory("prg03PU");

    private JPAUtil() {
    }

    public static EntityManager getEntityManager() {
        return FACTORY.createEntityManager();
    }
}