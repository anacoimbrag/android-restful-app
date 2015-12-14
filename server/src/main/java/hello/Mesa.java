package hello;

/**
 * Created by aninh on 21/11/2015.
 */

public class Mesa {
    String objectId;
    String matricula;
    String nome;
    String curso;
    String funcao;

    public Mesa() {
    }

    public Mesa(String objectId, String matricula, String nome, String curso, String funcao) {
        this.objectId = objectId;
        this.matricula = matricula;
        this.nome = nome;
        this.curso = curso;
        this.funcao = funcao;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
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

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }
}
