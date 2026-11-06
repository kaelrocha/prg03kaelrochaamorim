package br.com.ifba.usuario.exception;

public class UsuarioNaoEncontradoException extends RuntimeException {

    public UsuarioNaoEncontradoException(Object identificador) {
        super("Nenhum usuário encontrado com o identificador: " + identificador);
    }
}