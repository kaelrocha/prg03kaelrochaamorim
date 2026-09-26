package br.com.ifba.personagem.entity;

public class Arqueiro extends Personagem {

    public Arqueiro(String nome) {
        super(nome);
    }

    @Override
    public int calcularDano() {
        return getNivel() * 12; // arqueiro fica no meio termo
    }
}