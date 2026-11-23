package br.com.ifba.usuario.controller;

import br.com.ifba.usuario.entity.Usuario;
import java.util.List;

public interface UsuarioIController {

    void save(Usuario usuario);

    Usuario update(Usuario usuario);

    void delete(Usuario usuario);

    List<Usuario> findAll();

    List<Usuario> findByNome(String texto);
}