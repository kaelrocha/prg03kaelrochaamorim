package br.com.ifba.personagem.entity;

public class Guerreiro extends Personagem {

    public Guerreiro(String nome) {
        super(nome);
    }

    @Override
    public int calcularDano() {
        return getNivel() * 10; // guerreiro bate forte no corpo a corpo
    }
}