package br.com.ifba.personagem.entity;

public class Arqueiro extends Personagem {

    public Arqueiro(String nome) {
        super(nome);
    }
    
public Arqueiro(String nome, int nivelInicial) {
    super(nome, nivelInicial);
}

    @Override
    public int calcularDano() {
        return getNivel() * 12; // arqueiro fica no meio termo
    }
}