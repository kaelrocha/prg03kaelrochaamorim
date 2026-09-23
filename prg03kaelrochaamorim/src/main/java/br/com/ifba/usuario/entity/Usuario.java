/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.usuario.entity;

//Importa classe Personagem
import br.com.ifba.personagem.entity.Personagem;

import br.com.ifba.usuario.interfaces.Autenticavel;



// Classe de dominio que representa um Usuario do sistema
// Adcionado o implements autenticavel
public class Usuario implements Autenticavel {

    private String nome;
    private String cpf;
    private String genero;
    private String dataNascimento;
    private String telefone;
    private String email;
    private String login;
    private String senha;
    private Personagem personagem;
    
//Constructo sem atributos    
    public Usuario() {} 
    
//Constructo com atributos    
    public Usuario(String nome, String cpf, String login, String senha) {
    this.nome = nome;
    this.cpf = cpf;
    this.login = login;
    this.senha = senha;
}
    public Personagem getPersonagem() {
        return personagem;
    }
    
    public void setPersonagem(Personagem personagem) {
        this.personagem = personagem;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getGenero() {
        return genero;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }
    
    @Override
    public boolean autenticar(String login, String senha) {
        return this.login.equals(login) && this.senha.equals(senha);
    }
}


/**
 *
 * @author milar
 */
