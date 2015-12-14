package hello;

import org.parse4j.Parse;
import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;
import org.parse4j.callback.GetCallback;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aninh on 08/11/2015.
 */
@RestController
public class DAController {

    @RequestMapping(value = "/da", method = RequestMethod.POST, consumes = {"application/xml", "application/json"})
    public DA inserirDa(@RequestBody DA da) throws ParseException{
        ParseObject gameScore = new ParseObject("DA");
        gameScore.put("Nome", da.getNome());
        gameScore.put("Curso", da.getCurso());
        gameScore.put("Email", da.getEmail());
        gameScore.put("Predio", da.getPredio());
        gameScore.put("Situacao", da.isSituacao());
        gameScore.put("Telefone", da.getTelefone());
        gameScore.save();

        return da;
    }

    @RequestMapping(value = "/da", method = RequestMethod.GET)
    public List<DA> listarDas() throws ParseException{
        List<DA> retorno = new ArrayList<>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("DA");
        List<ParseObject> results = query.find();
        for (ParseObject obj : results) {
            retorno.add(new DA(obj.getObjectId(), obj.getString("Nome"),obj.getString("Curso"),obj.getString("Email"),obj.getInt("Predio"),obj.getBoolean("Situacao"), obj.getInt("Telefone")));
        }
        return retorno;
    }

    @RequestMapping(value = "/da/{objectId}", method = RequestMethod.GET)
    public DA getDaPorId(@PathVariable String objectId) throws ParseException{
        ParseQuery<ParseObject> query = ParseQuery.getQuery("DA");
        ParseObject obj = query.get(objectId);
        return new DA(obj.getObjectId(), obj.getString("Nome"),obj.getString("Curso"),obj.getString("Email"),obj.getInt("Predio"),obj.getBoolean("Situacao"), obj.getInt("Telefone"));
    }

    @RequestMapping(value = "/da/{objectID}", method = RequestMethod.DELETE)
    public String deletarDa(@PathVariable String objectId) throws ParseException{
        ParseQuery<ParseObject> query = ParseQuery.getQuery("DA");
        ParseObject obj = query.get(objectId);
        obj.delete();
        return "DA " + obj.getString("Nome") + " deletado com sucesso";
    }

}
