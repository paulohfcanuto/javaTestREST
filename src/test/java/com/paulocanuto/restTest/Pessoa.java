package com.paulocanuto.restTest;

public class Pessoa {
    private int id;
    private String nome;
    private String endereco;
    private String profissao;

    public Pessoa (int id, String nome, String endereco, String profissao) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.profissao = profissao;
    }

    public Pessoa (String nome, String endereco, String profissao) {
        this.nome = nome;
        this.endereco = endereco;
        this.profissao = profissao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getProfissao() {
        return profissao;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }
}
