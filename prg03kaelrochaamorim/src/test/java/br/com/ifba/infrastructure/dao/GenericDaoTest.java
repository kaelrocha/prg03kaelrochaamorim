/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.infrastructure.dao;

import br.com.ifba.infrastructure.exception.PersistenciaException;
import br.com.ifba.usuario.entity.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.lang.reflect.Proxy;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GenericDaoTest {

    // registra o que o DAO fez com o EntityManager falso
    private static class Registro {
        boolean rollbackChamado;
        boolean fechado;
    }

    // EntityManager falso: persist, merge, remove e find falham
    private EntityManager emQueFalha(Registro registro) {
        EntityTransaction transacao = (EntityTransaction) Proxy.newProxyInstance(
                getClass().getClassLoader(), new Class<?>[]{EntityTransaction.class},
                (proxy, metodo, args) -> {
                    switch (metodo.getName()) {
                        case "isActive":
                            return true;
                        case "rollback":
                            registro.rollbackChamado = true;
                            return null;
                        default:
                            return null; // begin, commit
                    }
                });

        return (EntityManager) Proxy.newProxyInstance(
                getClass().getClassLoader(), new Class<?>[]{EntityManager.class},
                (proxy, metodo, args) -> {
                    switch (metodo.getName()) {
                        case "getTransaction":
                            return transacao;
                        case "close":
                            registro.fechado = true;
                            return null;
                        case "persist":
                        case "merge":
                        case "remove":
                        case "find":
                            throw new IllegalStateException("falha simulada");
                        default:
                            return null;
                    }
                });
    }

    private GenericDao<Usuario> daoComFalha(Registro registro) {
        return new GenericDao<Usuario>(Usuario.class) {
            @Override
            protected EntityManager criarEntityManager() {
                return emQueFalha(registro);
            }
        };
    }

    @Test
    void save_quandoFalha_deveLancarPersistenciaException() {
        Registro registro = new Registro();

        assertThrows(PersistenciaException.class,
                () -> daoComFalha(registro).save(new Usuario()));
    }

    @Test
    void save_quandoFalha_deveFazerRollbackEFecharOEntityManager() {
        Registro registro = new Registro();

        assertThrows(PersistenciaException.class,
                () -> daoComFalha(registro).save(new Usuario()));

        assertTrue(registro.rollbackChamado);
        assertTrue(registro.fechado);
    }

    @Test
    void update_quandoFalha_deveLancarPersistenciaException() {
        Registro registro = new Registro();

        assertThrows(PersistenciaException.class,
                () -> daoComFalha(registro).update(new Usuario()));
        assertTrue(registro.rollbackChamado);
        assertTrue(registro.fechado);
    }

    @Test
    void delete_quandoFalha_deveLancarPersistenciaException() {
        Registro registro = new Registro();

        assertThrows(PersistenciaException.class,
                () -> daoComFalha(registro).delete(new Usuario()));
        assertTrue(registro.rollbackChamado);
        assertTrue(registro.fechado);
    }

    @Test
    void findById_quandoFalha_deveLancarPersistenciaEFecharOEntityManager() {
        Registro registro = new Registro();

        assertThrows(PersistenciaException.class,
                () -> daoComFalha(registro).findById(1L));
        assertTrue(registro.fechado);
    }
}
