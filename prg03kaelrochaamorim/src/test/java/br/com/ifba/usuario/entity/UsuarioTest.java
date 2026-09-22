/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.usuario.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UsuarioTest {

    @Test
    void autenticar_deveRetornarTrue_quandoCredenciaisCorretas() {
        Usuario usuario = new Usuario();
        usuario.setLogin("milar");
        usuario.setSenha("1234");

        assertTrue(usuario.autenticar("milar", "1234"));
    }

    @Test
    void autenticar_deveRetornarFalse_quandoSenhaIncorreta() {
        Usuario usuario = new Usuario();
        usuario.setLogin("milar");
        usuario.setSenha("1234");

        assertFalse(usuario.autenticar("milar", "senhaErrada"));
    }
}