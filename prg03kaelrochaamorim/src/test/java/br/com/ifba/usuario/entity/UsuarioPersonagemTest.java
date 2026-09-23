/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.usuario.entity;

import br.com.ifba.personagem.entity.Personagem;
import br.com.ifba.personagem.entity.TipoPersonagem;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UsuarioPersonagemTest {

    @Test
    void getPersonagem_deveRetornarOMesmoObjetoQueFoiSetado() {
        Usuario usuario = new Usuario();
        Personagem personagem = new Personagem("Thalor", TipoPersonagem.GUERREIRO);

        usuario.setPersonagem(personagem);

        assertSame(personagem, usuario.getPersonagem());
    }

    @Test
    void getPersonagem_deveRetornarNomeCorretoDoPersonagemAssociado() {
        Usuario usuario = new Usuario();
        Personagem personagem = new Personagem("Elyria", TipoPersonagem.MAGO);

        usuario.setPersonagem(personagem);

        assertEquals("Elyria", usuario.getPersonagem().getNome());
    }
}
