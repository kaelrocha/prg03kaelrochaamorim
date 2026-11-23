package br.com.ifba.usuario.service;

import br.com.ifba.usuario.dao.UsuarioIDao;
import br.com.ifba.usuario.entity.Usuario;
import br.com.ifba.usuario.exception.UsuarioInvalidoException;
import java.util.List;

public class UsuarioService implements UsuarioIService {

    private final UsuarioIDao dao;

    // o DAO chega pelo construtor
    public UsuarioService(UsuarioIDao dao) {
        this.dao = dao;
    }

    @Override
    public void save(Usuario usuario) {
        validarNomeELogin(usuario);
        if (vazio(usuario.getSenha())) {
            throw new UsuarioInvalidoException("A senha é obrigatória no cadastro.");
        }
        normalizar(usuario);
        dao.save(usuario);
    }

    @Override
    public Usuario update(Usuario usuario) {
        validarNomeELogin(usuario);
        normalizar(usuario);
        return dao.update(usuario);
    }

    @Override
    public void delete(Usuario usuario) {
        dao.delete(usuario);
    }

    @Override
    public List<Usuario> findAll() {
        return dao.findAll();
    }

    @Override
    public List<Usuario> findByNome(String texto) {
        return dao.findByNome(texto);
    }

    private void validarNomeELogin(Usuario usuario) {
        if (vazio(usuario.getNome())) {
            throw new UsuarioInvalidoException("O nome é obrigatório.");
        }
        if (vazio(usuario.getLogin())) {
            throw new UsuarioInvalidoException("O login é obrigatório.");
        }
    }

    // CPF e e-mail são unique: vazio vira null (o Postgres aceita vários NULL)
    private void normalizar(Usuario usuario) {
        if (vazio(usuario.getCpf())) {
            usuario.setCpf(null);
        }
        if (vazio(usuario.getEmail())) {
            usuario.setEmail(null);
        }
    }

    private boolean vazio(String texto) {
        return texto == null || texto.isBlank();
    }
}