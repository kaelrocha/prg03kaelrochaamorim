/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.infrastructure.exception;

public class PersistenciaException extends RuntimeException {

    public PersistenciaException(String operacao, Throwable causa) {
        super("Falha ao " + operacao + ": " + causa.getMessage(), causa);
    }
}