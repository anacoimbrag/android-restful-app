package hello;

/**
 * Created by aninh on 23/11/2015.
 */
public class Membro {
    String objectId;
    String matricula;
    String nome;
    String DA;
    String Funcao;

    public Membro() {
    }

    public Membro(String objectId, String matricula, String nome, String DA, String funcao) {
        this.objectId = objectId;
        this.matricula = matricula;
        this.nome = nome;
        this.DA = DA;
        Funcao = funcao;
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

    public String getDA() {
        return DA;
    }

    public void setDA(String DA) {
        this.DA = DA;
    }

    public String getFuncao() {
        return Funcao;
    }

    public void setFuncao(String funcao) {
        Funcao = funcao;
    }
}
