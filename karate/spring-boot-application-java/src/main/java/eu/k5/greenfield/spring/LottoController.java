package eu.k5.greenfield.spring;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import org.springframework.web.bind.annotation.*;

@RestController
public class LottoController {

    @RequestMapping(value = "/lotto", method = RequestMethod.GET, produces = "application/json")
    public String getLotto() {
        String json = "{\n" +
                "\"lotto\":{\n" +
                " \"lottoId\":5,\n" +
                " \"winning-numbers\":[2,45,34,23,7,5,3],\n" +
                " \"winners\":[{\n" +
                "   \"winnerId\":23,\n" +
                "   \"numbers\":[2,45,34,23,3,5]\n" +
                " },{\n" +
                "   \"winnerId\":54,\n" +
                "   \"numbers\":[52,3,12,11,18,22]\n" +
                " }]\n" +
                "}\n" +
                "}";

        return json;
    }

    @RequestMapping(value = "/lotto", method = RequestMethod.POST, produces = "application/json")
    public String postLotto(@RequestBody String lotto) {
        try {
            ObjectNode node = (ObjectNode) new ObjectMapper().readTree(lotto);

            node.set("changed", new TextNode("value"));

            return node.toPrettyString();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }


    @RequestMapping(value = "/withParams", produces = "application/json")
    public String getWithParams(@RequestParam("name") String name) {
        return "{\"name\":\"" + name + "\"}";
    }
}
