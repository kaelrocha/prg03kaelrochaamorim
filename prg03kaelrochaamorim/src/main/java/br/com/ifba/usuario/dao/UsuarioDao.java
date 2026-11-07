package br.com.ifba.usuario.dao;

import br.com.ifba.infrastructure.dao.GenericDao;
import br.com.ifba.infrastructure.exception.PersistenciaException;
import br.com.ifba.usuario.entity.Usuario;
import br.com.ifba.usuario.exception.UsuarioNaoEncontradoException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import java.util.List;

public class UsuarioDao extends GenericDao<Usuario> implements UsuarioIDao {

    public UsuarioDao() {
        super(Usuario.class);
    }

    @Override
    public Usuario findByLogin(String login) {
        EntityManager em = criarEntityManager();
        try {
            return em.createQuery("SELECT u FROM Usuario u WHERE u.login = :login", Usuario.class)
                    .setParameter("login", login)
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new UsuarioNaoEncontradoException(login);
        } catch (RuntimeException e) {
            throw new PersistenciaException("buscar Usuario por login", e);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Usuario> findByNome(String texto) {
        EntityManager em = criarEntityManager();
        try {
            return em.createQuery(
                    "SELECT u FROM Usuario u WHERE LOWER(u.nome) LIKE LOWER(:texto)", Usuario.class)
                    .setParameter("texto", "%" + texto + "%")
                    .getResultList();
        } catch (RuntimeException e) {
            throw new PersistenciaException("buscar Usuario por nome", e);
        } finally {
            em.close();
        }
    }
}