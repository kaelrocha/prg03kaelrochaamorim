package br.com.ifba.personagem.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Personagem {

    private String nome;
    private int nivel;
    private List<Item> inventario;

    public Personagem(String nome) {
        this.nome = nome;
        this.nivel = 1;
        this.inventario = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public void adicionarItem(Item item) {
        this.inventario.add(item);
    }

    public List<Item> getInventario() {
        return Collections.unmodifiableList(inventario);
    }

    public int quantidadeItens() {
        return inventario.size();
    }

    // método abstrato: cada filha decide sua própria fórmula de dano
    public abstract int calcularDano();
}