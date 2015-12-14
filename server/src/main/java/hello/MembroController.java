package hello;

import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aninh on 21/11/2015.
 */
@RestController
@RequestMapping(value = "/membro")
public class MembroController {
    @RequestMapping(method = RequestMethod.POST, consumes = {"application/xml", "application/json"})
    public Membro inserirDa(@RequestBody Membro membro) throws ParseException {
        ParseObject gameScore = new ParseObject("Membro");
        gameScore.put("Matricula", membro.getMatricula());
        gameScore.put("Nome", membro.getNome());
        gameScore.put("DA", membro.getDA());
        gameScore.put("Funcao", membro.getFuncao());
        gameScore.save();

        return membro;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Membro> listarDas() throws ParseException{
        List<Membro> retorno = new ArrayList<>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Membro");
        List<ParseObject> results = query.find();
        for (ParseObject obj : results) {
            retorno.add(new Membro(obj.getObjectId(), obj.getNumber("Matricula").toString(), obj.getString("Nome"), obj.getString("DA"), obj.getString("Funcao")));
        }
        return retorno;
    }
}
