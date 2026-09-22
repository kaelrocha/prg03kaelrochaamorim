/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.usuario.validar;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ValidadorUsuarioTest {

    // ---------- camposPreenchidos ----------

    @Test
    void camposPreenchidos_deveRetornarTrue_quandoTodosPreenchidos() {
        boolean resultado = ValidadorUsuario.camposPreenchidos(
                "Milar", "12345678901", "01/01/2000", "71999999999",
                "milar@teste.com", "milar", "senha123", "senha123");
        assertTrue(resultado);
    }

    @Test
    void camposPreenchidos_deveRetornarFalse_quandoCampoVazio() {
        boolean resultado = ValidadorUsuario.camposPreenchidos(
                "", "12345678901", "01/01/2000", "71999999999",
                "milar@teste.com", "milar", "senha123", "senha123");
        assertFalse(resultado);
    }

    @Test
    void camposPreenchidos_deveRetornarFalse_quandoCampoNull() {
        boolean resultado = ValidadorUsuario.camposPreenchidos(
                null, "12345678901", "01/01/2000", "71999999999",
                "milar@teste.com", "milar", "senha123", "senha123");
        assertFalse(resultado);
    }

    // ---------- cpfValido ----------

    @Test
    void cpfValido_deveRetornarTrue_paraCpfComOnzeDigitos() {
        assertTrue(ValidadorUsuario.cpfValido("12345678901"));
    }

    @Test
    void cpfValido_deveRetornarFalse_paraCpfVazio() {
        assertFalse(ValidadorUsuario.cpfValido(""));
    }

    @Test
    void cpfValido_deveRetornarFalse_paraCpfComLetras() {
        assertFalse(ValidadorUsuario.cpfValido("123abc78901"));
    }

    @Test
    void cpfValido_deveRetornarFalse_paraCpfNull() {
        assertFalse(ValidadorUsuario.cpfValido(null));
    }

    @Test
    void cpfValido_deveRetornarFalse_paraCpfComPoucosDigitos() {
        assertFalse(ValidadorUsuario.cpfValido("123456"));
    }

    // ---------- senhaForte ----------

    @Test
    void senhaForte_deveRetornarTrue_paraSenhaComTamanhoMinimo() {
        assertTrue(ValidadorUsuario.senhaForte("senha123"));
    }

    @Test
    void senhaForte_deveRetornarFalse_paraSenhaCurta() {
        assertFalse(ValidadorUsuario.senhaForte("123"));
    }

    @Test
    void senhaForte_deveRetornarFalse_paraSenhaNull() {
        assertFalse(ValidadorUsuario.senhaForte(null));
    }

    // ---------- senhasCoincidem ----------

    @Test
    void senhasCoincidem_deveRetornarTrue_quandoIguais() {
        assertTrue(ValidadorUsuario.senhasCoincidem("senha123", "senha123"));
    }

    @Test
    void senhasCoincidem_deveRetornarFalse_quandoDiferentes() {
        assertFalse(ValidadorUsuario.senhasCoincidem("senha123", "outraSenha"));
    }

    @Test
    void senhasCoincidem_deveRetornarFalse_quandoNull() {
        assertFalse(ValidadorUsuario.senhasCoincidem(null, "senha123"));
    }
}
