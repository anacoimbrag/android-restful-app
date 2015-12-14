package hello;

import org.hibernate.validator.internal.util.logging.Log;
import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;
import org.parse4j.ParseRelation;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping(value = "/mesa")
public class MesaController {
    @RequestMapping(method = RequestMethod.POST, consumes = {"application/xml", "application/json"})
    public Mesa inserirDa(@RequestBody Mesa mesa) throws ParseException {
        ParseObject gameScore = new ParseObject("Mesa");
        gameScore.put("Matricula", mesa.getMatricula());
        gameScore.put("Nome", mesa.getNome());
        gameScore.put("Curso", mesa.getCurso());
        gameScore.put("Funcao", mesa.getFuncao());
        gameScore.save();

        return mesa;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Mesa> listarDas() throws ParseException{
        List<Mesa> retorno = new ArrayList<>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Mesa");
        List<ParseObject> results = query.find();
        for (ParseObject obj : results) {
            retorno.add(new Mesa(obj.getObjectId(), obj.getString("Matricula"), obj.getString("Nome"), obj.getString("Curso"), obj.getString("Funcao")));
        }

        return retorno;
    }
}
