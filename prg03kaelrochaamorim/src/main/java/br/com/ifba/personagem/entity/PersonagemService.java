/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.personagem.entity;

public class PersonagemService {

    // recebe o tipo geral (abstrato), nunca o tipo concreto
    public static int processarDano(Personagem personagem) {
        return personagem.calcularDano();
    }
}