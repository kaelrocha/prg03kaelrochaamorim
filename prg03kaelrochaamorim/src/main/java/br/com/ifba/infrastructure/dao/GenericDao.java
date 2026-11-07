package br.com.ifba.infrastructure.dao;

import br.com.ifba.infrastructure.entity.PersistenceEntity;
import br.com.ifba.infrastructure.exception.PersistenciaException;
import br.com.ifba.infrastructure.util.JPAUtil;
import jakarta.persistence.EntityManager;
import java.util.List;

public class GenericDao<Entity extends PersistenceEntity> implements GenericIDao<Entity> {

    private final Class<Entity> tipo;

    public GenericDao(Class<Entity> tipo) {
        this.tipo = tipo;
    }

    // ponto de extensão: os testes sobrescrevem para não depender do banco
    protected EntityManager criarEntityManager() {
        return JPAUtil.getEntityManager();
    }

    protected Class<Entity> getTipo() {
        return tipo;
    }

    @Override
    public void save(Entity entity) {
        EntityManager em = criarEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("salvar " + tipo.getSimpleName(), e);
        } finally {
            em.close();
        }
    }

    @Override
    public Entity update(Entity entity) {
        EntityManager em = criarEntityManager();
        try {
            em.getTransaction().begin();
            Entity atualizada = em.merge(entity);
            em.getTransaction().commit();
            return atualizada;
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("atualizar " + tipo.getSimpleName(), e);
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(Entity entity) {
        EntityManager em = criarEntityManager();
        try {
            em.getTransaction().begin();
            em.remove(em.merge(entity));
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("excluir " + tipo.getSimpleName(), e);
        } finally {
            em.close();
        }
    }

    @Override
    public Entity findById(Long id) {
        EntityManager em = criarEntityManager();
        try {
            return em.find(tipo, id);
        } catch (RuntimeException e) {
            throw new PersistenciaException("buscar " + tipo.getSimpleName() + " por id", e);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Entity> findAll() {
        EntityManager em = criarEntityManager();
        try {
            return em.createQuery("SELECT e FROM " + tipo.getSimpleName() + " e", tipo)
                    .getResultList();
        } catch (RuntimeException e) {
            throw new PersistenciaException("listar " + tipo.getSimpleName(), e);
        } finally {
            em.close();
        }
    }
}
