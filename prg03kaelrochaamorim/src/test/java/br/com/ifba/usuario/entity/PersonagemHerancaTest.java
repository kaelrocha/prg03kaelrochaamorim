package br.com.ifba.usuario.entity;

import br.com.ifba.personagem.entity.Arqueiro;
import br.com.ifba.personagem.entity.Guerreiro;
import br.com.ifba.personagem.entity.Item;
import br.com.ifba.personagem.entity.Mago;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PersonagemHerancaTest {

    // ---------- comportamento HERDADO (adicionarItem vem da mãe) ----------

    @Test
    void guerreiro_deveHerdarAdicionarItemDaClasseMae() {
        Guerreiro guerreiro = new Guerreiro("Thalor");
        guerreiro.adicionarItem(new Item("Machado", 200));

        assertEquals(1, guerreiro.quantidadeItens());
    }

    @Test
    void mago_deveHerdarAdicionarItemDaClasseMae() {
        Mago mago = new Mago("Elyria");
        mago.adicionarItem(new Item("Grimório", 300));

        assertEquals(1, mago.quantidadeItens());
    }

    @Test
    void arqueiro_deveHerdarAdicionarItemDaClasseMae() {
        Arqueiro arqueiro = new Arqueiro("Sylas");
        arqueiro.adicionarItem(new Item("Flecha", 5));

        assertEquals(1, arqueiro.quantidadeItens());
    }

    // ---------- comportamento SOBRESCRITO (cada filha calcula seu próprio dano) ----------

    @Test
    void guerreiro_deveCalcularDanoProprio() {
        Guerreiro guerreiro = new Guerreiro("Thalor");
        guerreiro.setNivel(2);

        assertEquals(20, guerreiro.calcularDano()); // 2 * 10
    }

    @Test
    void mago_deveCalcularDanoProprio() {
        Mago mago = new Mago("Elyria");
        mago.setNivel(2);

        assertEquals(30, mago.calcularDano()); // 2 * 15
    }

    @Test
    void arqueiro_deveCalcularDanoProprio() {
        Arqueiro arqueiro = new Arqueiro("Sylas");
        arqueiro.setNivel(2);

        assertEquals(24, arqueiro.calcularDano()); // 2 * 12
    }

    @Test
    void tiposDiferentes_devemProduzirDanosDiferentesNoMesmoNivel() {
        Guerreiro guerreiro = new Guerreiro("Thalor");
        Mago mago = new Mago("Elyria");
        Arqueiro arqueiro = new Arqueiro("Sylas");

        // todos no nível 1 (padrão do construtor)
        assertNotEquals(guerreiro.calcularDano(), mago.calcularDano());
        assertNotEquals(mago.calcularDano(), arqueiro.calcularDano());
        assertNotEquals(guerreiro.calcularDano(), arqueiro.calcularDano());
    }
}