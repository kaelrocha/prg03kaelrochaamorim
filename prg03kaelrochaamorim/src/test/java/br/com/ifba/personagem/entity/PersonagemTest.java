/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.personagem.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PersonagemTest {

    @Test
    void personagemRecemCriado_deveNascerComNivelUm() {
        Personagem personagem = new Guerreiro("Thalor");

        assertEquals(1, personagem.getNivel());
    }

    @Test
    void personagemRecemCriado_deveNascerComInventarioVazio() {
        Personagem personagem = new Guerreiro("Thalor");

        assertEquals(0, personagem.quantidadeItens());
    }

    @Test
    void adicionarItem_deveFazerAListaCrescer() {
        Personagem personagem = new Guerreiro("Thalor");
        Item espada = new Item("Espada Longa", 150);

        personagem.adicionarItem(espada);

        assertEquals(1, personagem.quantidadeItens());
    }

    @Test
    void adicionarVariosItens_deveAcumularNaLista() {
        Personagem personagem = new Guerreiro("Thalor");
        personagem.adicionarItem(new Item("Espada Longa", 150));
        personagem.adicionarItem(new Item("Escudo de Ferro", 80));
        personagem.adicionarItem(new Item("Poção de Vida", 20));

        assertEquals(3, personagem.quantidadeItens());
    }

    @Test
    void getInventario_deveRetornarOsItensAdicionados() {
        Personagem personagem = new Guerreiro("Thalor");
        Item espada = new Item("Espada Longa", 150);
        personagem.adicionarItem(espada);

        assertTrue(personagem.getInventario().contains(espada));
    }

    @Test
    void getInventario_naoDevePermitirModificacaoExterna() {
        Personagem personagem = new Guerreiro("Thalor");
        personagem.adicionarItem(new Item("Espada Longa", 150));

        assertThrows(UnsupportedOperationException.class, () -> {
            personagem.getInventario().add(new Item("Item Hackeado", 999));
        });
    }
}