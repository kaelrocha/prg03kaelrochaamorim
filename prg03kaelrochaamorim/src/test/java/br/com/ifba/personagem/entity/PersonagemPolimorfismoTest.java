/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.personagem.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PersonagemPolimorfismoTest {

    @Test
    void guerreiroCalculaDanoProprio_viaTipoGeral() {
        Personagem personagem = new Guerreiro("Thalor"); // tipo geral à esquerda
        personagem.setNivel(2);

        assertEquals(20, PersonagemService.processarDano(personagem));
    }

    @Test
    void magoCalculaDanoProprio_viaTipoGeral() {
        Personagem personagem = new Mago("Elyria"); // tipo geral à esquerda
        personagem.setNivel(2);

        assertEquals(30, PersonagemService.processarDano(personagem));
    }

    @Test
    void arqueiroCalculaDanoProprio_viaTipoGeral() {
        Personagem personagem = new Arqueiro("Sylas"); // tipo geral à esquerda
        personagem.setNivel(2);

        assertEquals(24, PersonagemService.processarDano(personagem));
    }

    @Test
    void mesmoMetodoRetornaResultadosDiferentesParaTiposDiferentes() {
        Personagem guerreiro = new Guerreiro("Thalor");
        Personagem mago = new Mago("Elyria");

        int danoGuerreiro = PersonagemService.processarDano(guerreiro);
        int danoMago = PersonagemService.processarDano(mago);

        assertNotEquals(danoGuerreiro, danoMago);
    }

    @Test
    void construtorSobrecarregado_deveDefinirNivelInicial() {
        Personagem personagem = new Guerreiro("Veterano", 5);

        assertEquals(5, personagem.getNivel());
    }

    @Test
    void construtorPadrao_deveDefinirNivelUm() {
        Personagem personagem = new Guerreiro("Novato");

        assertEquals(1, personagem.getNivel());
    }
}
