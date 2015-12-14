package hello;

import org.parse4j.ParseFile;

import java.io.File;
import java.util.Date;

/**
 * Created by aninh on 23/11/2015.
 */
public class Reuniao {
    String assunto;
    Date data;
    ParseFile ata;

    public Reuniao() {
    }

    public Reuniao(String assunto, Date data, ParseFile ata) {
        this.assunto = assunto;
        this.data = data;
        this.ata = ata;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public ParseFile getAta() {
        return ata;
    }

    public void setAta(ParseFile ata) {
        this.ata = ata;
    }
}

