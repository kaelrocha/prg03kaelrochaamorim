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
    private static class RegistroOperacoesJpa {
        boolean rollbackExecutado;
        boolean entityManagerFechado;
    }

    // EntityManager falso: persist, merge, remove e find falham
    private EntityManager criarEntityManagerComFalha(RegistroOperacoesJpa registro) {
        EntityTransaction transacao = (EntityTransaction) Proxy.newProxyInstance(
                getClass().getClassLoader(), new Class<?>[]{EntityTransaction.class},
                (proxy, metodo, args) -> {
                    switch (metodo.getName()) {
                        case "isActive":
                            return true;
                        case "rollback":
                            registro.rollbackExecutado = true;
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
                            registro.entityManagerFechado = true;
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

    private GenericDao<Usuario> criarDaoComFalhaSimulada(RegistroOperacoesJpa registro) {
        return new GenericDao<>(Usuario.class, () -> criarEntityManagerComFalha(registro));
    }

    @Test
    void save_quandoFalha_deveLancarPersistenciaException() {
        RegistroOperacoesJpa registro = new RegistroOperacoesJpa();

        assertThrows(PersistenciaException.class,
                () -> criarDaoComFalhaSimulada(registro).save(new Usuario()));
    }

    @Test
    void save_quandoFalha_deveFazerRollbackEFecharOEntityManager() {
        RegistroOperacoesJpa registro = new RegistroOperacoesJpa();

        assertThrows(PersistenciaException.class,
                () -> criarDaoComFalhaSimulada(registro).save(new Usuario()));

        assertTrue(registro.rollbackExecutado);
        assertTrue(registro.entityManagerFechado);
    }

    @Test
    void update_quandoFalha_deveLancarPersistenciaException() {
        RegistroOperacoesJpa registro = new RegistroOperacoesJpa();

        assertThrows(PersistenciaException.class,
                () -> criarDaoComFalhaSimulada(registro).update(new Usuario()));
        assertTrue(registro.rollbackExecutado);
        assertTrue(registro.entityManagerFechado);
    }

    @Test
    void delete_quandoFalha_deveLancarPersistenciaException() {
        RegistroOperacoesJpa registro = new RegistroOperacoesJpa();

        assertThrows(PersistenciaException.class,
                () -> criarDaoComFalhaSimulada(registro).delete(new Usuario()));
        assertTrue(registro.rollbackExecutado);
        assertTrue(registro.entityManagerFechado);
    }

    @Test
    void findById_quandoFalha_deveLancarPersistenciaEFecharOEntityManager() {
        RegistroOperacoesJpa registro = new RegistroOperacoesJpa();

        assertThrows(PersistenciaException.class,
                () -> criarDaoComFalhaSimulada(registro).findById(1L));
        assertTrue(registro.entityManagerFechado);
    }
}
