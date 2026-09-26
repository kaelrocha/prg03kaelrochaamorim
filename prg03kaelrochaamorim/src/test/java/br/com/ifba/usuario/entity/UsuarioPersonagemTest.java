/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.usuario.entity;

import br.com.ifba.personagem.entity.Personagem;
import br.com.ifba.personagem.entity.Guerreiro;
import br.com.ifba.personagem.entity.Mago;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UsuarioPersonagemTest {

    @Test
    void getPersonagem_deveRetornarOMesmoObjetoQueFoiSetado() {
        Usuario usuario = new Usuario();
        Personagem personagem = new Guerreiro("Thalor");

        usuario.setPersonagem(personagem);

        assertSame(personagem, usuario.getPersonagem());
    }

    @Test
    void getPersonagem_deveRetornarNomeCorretoDoPersonagemAssociado() {
        Usuario usuario = new Usuario();
        Personagem personagem = new Mago("Elyria");

        usuario.setPersonagem(personagem);

        assertEquals("Elyria", usuario.getPersonagem().getNome());
    }
}
