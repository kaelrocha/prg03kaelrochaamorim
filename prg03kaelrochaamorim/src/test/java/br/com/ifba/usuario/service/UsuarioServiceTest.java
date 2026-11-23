package br.com.ifba.usuario.service;

import br.com.ifba.usuario.entity.Usuario;
import br.com.ifba.usuario.exception.UsuarioInvalidoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UsuarioServiceTest {

    private FakeUsuarioDao dao;
    private UsuarioIService service;

    @BeforeEach
    void preparar() {
        dao = new FakeUsuarioDao();
        service = new UsuarioService(dao);
    }

    private Usuario valido() {
        return new Usuario("Ana Souza", null, "ana", "123456");
    }

    @Test
    void save_comDadosValidos_deveGravarNoDao() {
        service.save(valido());

        assertEquals(1, dao.findAll().size());
    }

    @Test
    void save_semNome_deveLancarExcecaoENaoGravar() {
        Usuario usuario = valido();
        usuario.setNome("");

        assertThrows(UsuarioInvalidoException.class, () -> service.save(usuario));
        assertEquals(0, dao.findAll().size());
    }

    @Test
    void save_semLogin_deveLancarExcecao() {
        Usuario usuario = valido();
        usuario.setLogin(" ");

        assertThrows(UsuarioInvalidoException.class, () -> service.save(usuario));
    }

    @Test
    void save_semSenha_deveLancarExcecao() {
        Usuario usuario = valido();
        usuario.setSenha(null);

        assertThrows(UsuarioInvalidoException.class, () -> service.save(usuario));
    }

    @Test
    void save_comCpfEEmailVazios_deveGravarComoNull() {
        Usuario usuario = valido();
        usuario.setCpf("");
        usuario.setEmail("");

        service.save(usuario);

        assertNull(usuario.getCpf());
        assertNull(usuario.getEmail());
    }

    @Test
    void update_semNome_deveLancarExcecao() {
        Usuario usuario = valido();
        service.save(usuario);
        usuario.setNome("");

        assertThrows(UsuarioInvalidoException.class, () -> service.update(usuario));
    }

    @Test
    void update_comDadosValidos_naoDeveLancarExcecao() {
        Usuario usuario = valido();
        service.save(usuario);
        usuario.setNome("Ana Maria Souza");

        assertDoesNotThrow(() -> service.update(usuario));
    }

    @Test
    void delete_deveRemoverDoDao() {
        Usuario usuario = valido();
        service.save(usuario);

        service.delete(usuario);

        assertEquals(0, dao.findAll().size());
    }

    @Test
    void findByNome_deveDevolverSomenteOsQueContemOTexto() {
        service.save(valido());
        service.save(new Usuario("Bruno Lima", null, "bruno", "123456"));

        assertEquals(1, service.findByNome("bruno").size());
        assertEquals("Bruno Lima", service.findByNome("bruno").get(0).getNome());
    }
}