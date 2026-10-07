package br.com.ifba.usuario.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepositorioUsuarioEmMemoria {

    private final List<Usuario> usuarios = new ArrayList<>();
    private final Map<String, Usuario> porLogin = new HashMap<>();

    public void cadastrar(Usuario usuario) {
        if (porLogin.containsKey(usuario.getLogin())) {
            throw new IllegalArgumentException("Já existe um usuário com este login.");
    }
        usuarios.add(usuario);
        porLogin.put(usuario.getLogin(), usuario);
    }

    public List<Usuario> listarTodos() {
        return usuarios;
    }
// busca percorrendo a lista (O(n) — varre tudo até achar)
    public Usuario buscarPorLoginComFor(String login) {
        for (Usuario usuario : usuarios) {
            if (usuario.getLogin().equals(login)) {
                return usuario;
            }
        }
        return null;
    }
// busca usando o Map (O(1) — consulta direta por chave)
    public Usuario buscarPorLogin(String login) {
        return porLogin.get(login);
    }
}