package br.com.ifba.usuario.service;

import br.com.ifba.usuario.dao.UsuarioIDao;
import br.com.ifba.usuario.entity.Usuario;
import br.com.ifba.usuario.exception.UsuarioNaoEncontradoException;
import java.util.ArrayList;
import java.util.List;

// Dublê do DAO: guarda tudo numa lista em memória, sem banco
public class FakeUsuarioDao implements UsuarioIDao {

    private final List<Usuario> usuarios = new ArrayList<>();
    private long proximoId = 1;

    @Override
    public void save(Usuario usuario) {
        usuario.setId(proximoId++);
        usuarios.add(usuario);
    }

    @Override
    public Usuario update(Usuario usuario) {
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getId().equals(usuario.getId())) {
                usuarios.set(i, usuario);
                return usuario;
            }
        }
        throw new UsuarioNaoEncontradoException(usuario.getId());
    }

    @Override
    public void delete(Usuario usuario) {
        usuarios.remove(usuario);
    }

    @Override
    public Usuario findById(Long id) {
        for (Usuario u : usuarios) {
            if (u.getId().equals(id)) {
                return u;
            }
        }
        return null;
    }

    @Override
    public List<Usuario> findAll() {
        return new ArrayList<>(usuarios);
    }

    @Override
    public Usuario findByLogin(String login) {
        for (Usuario u : usuarios) {
            if (u.getLogin().equals(login)) {
                return u;
            }
        }
        throw new UsuarioNaoEncontradoException(login);
    }

    @Override
    public List<Usuario> findByNome(String texto) {
        List<Usuario> resultado = new ArrayList<>();
        for (Usuario u : usuarios) {
            if (u.getNome().toLowerCase().contains(texto.toLowerCase())) {
                resultado.add(u);
            }
        }
        return resultado;
    }
}