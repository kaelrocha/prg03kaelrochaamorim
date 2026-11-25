package br.com.ifba.usuario.controller;

import org.springframework.stereotype.Controller;

import br.com.ifba.usuario.entity.Usuario;
import br.com.ifba.usuario.service.UsuarioIService;
import java.util.List;

@Controller
public class UsuarioController implements UsuarioIController {

    private final UsuarioIService service;

    // o Service chega pelo construtor
    public UsuarioController(UsuarioIService service) {
        this.service = service;
    }

    @Override
    public void save(Usuario usuario) {
        service.save(usuario);
    }

    @Override
    public Usuario update(Usuario usuario) {
        return service.update(usuario);
    }

    @Override
    public void delete(Usuario usuario) {
        service.delete(usuario);
    }

    @Override
    public List<Usuario> findAll() {
        return service.findAll();
    }

    @Override
    public List<Usuario> findByNome(String texto) {
        return service.findByNome(texto);
    }
}