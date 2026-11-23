package br.com.ifba.usuario.exception;

public class UsuarioInvalidoException extends RuntimeException {

    public UsuarioInvalidoException(String motivo) {
        super(motivo);
    }
}