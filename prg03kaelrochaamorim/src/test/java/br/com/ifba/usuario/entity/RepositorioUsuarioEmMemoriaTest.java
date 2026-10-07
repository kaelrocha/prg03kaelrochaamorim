package br.com.ifba.usuario.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RepositorioUsuarioEmMemoriaTest {

    @Test
    void cadastrar_deveFazerUsuarioAparecerEmListarTodos() {
        RepositorioUsuarioEmMemoria repositorio = new RepositorioUsuarioEmMemoria();
        Usuario usuario = new Usuario();
        usuario.setLogin("milar");
        usuario.setSenha("1234");

        repositorio.cadastrar(usuario);

        assertTrue(repositorio.listarTodos().contains(usuario));
    }

    @Test
    void buscarPorLogin_deveDevolverOUsuarioCorreto() {
        RepositorioUsuarioEmMemoria repositorio = new RepositorioUsuarioEmMemoria();

        Usuario usuario1 = new Usuario();
        usuario1.setLogin("milar");
        usuario1.setSenha("1234");

        Usuario usuario2 = new Usuario();
        usuario2.setLogin("thalor");
        usuario2.setSenha("abcd");

        repositorio.cadastrar(usuario1);
        repositorio.cadastrar(usuario2);

        assertEquals(usuario2, repositorio.buscarPorLogin("thalor"));
    }

    @Test
    void buscarPorLogin_comLoginInexistente_deveDevolverNull() {
        RepositorioUsuarioEmMemoria repositorio = new RepositorioUsuarioEmMemoria();

        assertNull(repositorio.buscarPorLogin("naoExiste"));
    }

    @Test
    void cadastrar_comLoginDuplicado_deveLancarExcecao() {
        RepositorioUsuarioEmMemoria repositorio = new RepositorioUsuarioEmMemoria();

        Usuario usuario1 = new Usuario();
        usuario1.setLogin("milar");
        usuario1.setSenha("1234");

        Usuario usuario2 = new Usuario();
        usuario2.setLogin("milar"); // mesmo login
        usuario2.setSenha("outraSenha");

        repositorio.cadastrar(usuario1);

        assertThrows(IllegalArgumentException.class, () -> repositorio.cadastrar(usuario2));
    }

    @Test
    void doisUsuariosComMesmoLogin_devemSerIguaisParaALista() {
        Usuario usuario1 = new Usuario();
        usuario1.setLogin("milar");
        usuario1.setSenha("1234");

        Usuario usuario2 = new Usuario();
        usuario2.setLogin("milar"); // mesmo login, objeto diferente
        usuario2.setSenha("outraSenha");

        assertEquals(usuario1, usuario2); // prova do equals sobrescrito
    }
}