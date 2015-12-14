package org.hello.rest;

public class DA {

    String objectId;
    String nome;
    String curso;
    String email;
    int predio;
    boolean situacao;
    int telefone;

    public DA(){
        this.objectId = objectId;
        this.nome = nome;
    }

    public DA(String objectId, String nome, String curso, String email, int predio, boolean situacao, int telefone) {
        this.objectId = objectId;
        this.nome = nome;
        this.curso = curso;
        this.email = email;
        this.predio = predio;
        this.situacao = situacao;
        this.telefone = telefone;
    }

    public String getObjectId() {
        return objectId;
    }

    public String getNome() { return  nome; }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPredio() {
        return predio;
    }

    public void setPredio(int predio) {
        this.predio = predio;
    }

    public boolean isSituacao() {
        return situacao;
    }

    public void setSituacao(boolean situacao) {
        this.situacao = situacao;
    }

    public int getTelefone() {
        return telefone;
    }

    public void setTelefone(int telefone) {
        this.telefone = telefone;
    }
}
