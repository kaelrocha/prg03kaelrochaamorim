/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.personagem.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Personagem {

    private String nome;
    private int nivel;
    private TipoPersonagem tipo;
    private List<Item> inventario;

    public Personagem() {
        this.inventario = new ArrayList<>();
        this.nivel = 1; // valor inicial padrão
    }

    public Personagem(String nome, TipoPersonagem tipo) {
        this();
        this.nome = nome;
        this.tipo = tipo;
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

    public TipoPersonagem getTipo() {
        return tipo;
    }

    public void setTipo(TipoPersonagem tipo) {
        this.tipo = tipo;
    }

    // método que adiciona item — a lista nunca é exposta diretamente
    public void adicionarItem(Item item) {
        this.inventario.add(item);
    }

    // devolve uma cópia não-modificável, protegendo o encapsulamento
    public List<Item> getInventario() {
        return Collections.unmodifiableList(inventario);
    }

    public int quantidadeItens() {
        return inventario.size();
    }
}
