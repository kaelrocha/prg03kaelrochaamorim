/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.usuario.validar;

/**
 *
 * @author milar
 */
public class ValidadorUsuario {
    /**
     * Verifica se o texto informado contém alguma palavra proibida.
     * @param texto texto a ser verificado (ex: login)
     * @return true se contiver palavra proibida, false caso contrário
     */
    public static boolean contemPalavraProibida(String texto) {
        // Lista local de palavras não permitidas
        String[] palavrasProibidas = {"admin", "teste", "root", "senha123"};

        // Percorre o array verificando se o texto contém alguma delas
        for (String palavra : palavrasProibidas) {
            if (texto.toLowerCase().contains(palavra.toLowerCase())) {
                return true;
            }
        }

        return false;
    }
}
