package br.com.ifba.personagem.entity;

public class Mago extends Personagem {

    public Mago(String nome) {
        super(nome);
    }

    @Override
    public int calcularDano() {
        return getNivel() * 15; // mago tem o maior dano mágico
    }
}