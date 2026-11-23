package br.com.ifba.usuario.service;

import br.com.ifba.usuario.entity.Usuario;
import java.util.List;

public interface UsuarioIService {

    void save(Usuario usuario);

    Usuario update(Usuario usuario);

    void delete(Usuario usuario);

    List<Usuario> findAll();

    List<Usuario> findByNome(String texto);
}