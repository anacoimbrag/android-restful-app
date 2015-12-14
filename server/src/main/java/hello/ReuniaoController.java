package hello;

import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by aninh on 23/11/2015.
 */
@RestController
@RequestMapping(value = "/reuniao")
public class ReuniaoController {

    @RequestMapping(method = RequestMethod.GET)
    public List<Reuniao> getProxReunioes(){

        List<Reuniao> retorno = new ArrayList<>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Reuniao");
//        query.whereGreaterThan("data", new Date());
        try {
            List<ParseObject> results = query.find();
            for (ParseObject obj : results) {
                retorno.add(new Reuniao(obj.getObjectId(), obj.getDate("data"), obj.getParseFile("ata")));
            }
        }catch (ParseException e){
            System.out.println(e.getMessage());
        }

        return retorno;
    }
}
