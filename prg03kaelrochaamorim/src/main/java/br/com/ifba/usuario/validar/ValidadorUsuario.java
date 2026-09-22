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

    /**
     * Verifica se todos os campos obrigatórios foram preenchidos.
     */
    public static boolean camposPreenchidos(String nome, String cpf, String dataNascimento,
                                             String telefone, String email, String login,
                                             String senha, String confirmarSenha) {
        if (nome == null || nome.trim().isEmpty()) return false;
        if (cpf == null || cpf.trim().isEmpty()) return false;
        if (dataNascimento == null || dataNascimento.trim().isEmpty()) return false;
        if (telefone == null || telefone.trim().isEmpty()) return false;
        if (email == null || email.trim().isEmpty()) return false;
        if (login == null || login.trim().isEmpty()) return false;
        if (senha == null || senha.trim().isEmpty()) return false;
        if (confirmarSenha == null || confirmarSenha.trim().isEmpty()) return false;
        return true;
    }

    /**
     * Verifica se o CPF tem formato válido (11 dígitos numéricos).
     */
    public static boolean cpfValido(String cpf) {
        if (cpf == null) return false;
        String cpfLimpo = cpf.replaceAll("[^0-9]", "");
        return cpfLimpo.length() == 11;
    }

    /**
     * Verifica se a senha atende ao tamanho mínimo exigido.
     */
    public static boolean senhaForte(String senha) {
        if (senha == null) return false;
        return senha.length() >= 6;
    }

    /**
     * Verifica se a senha e a confirmação de senha são iguais.
     */
    public static boolean senhasCoincidem(String senha, String confirmarSenha) {
        if (senha == null || confirmarSenha == null) return false;
        return senha.equals(confirmarSenha);
    }
}