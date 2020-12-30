package chu.bilibili.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class JacksonUtil {
    //解析Json字符串 输出String类型
    public String parse(String body, String filed) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(body).get(filed);
        return String.valueOf(jsonNode);
    }

    public Integer parseInteger(String body, String filed) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(body).get(filed);
        return Integer.valueOf(String.valueOf(jsonNode));
    }

    //解析Json数组并将元素类型转换为String
    public List<String> parseList(String body, String field) throws JsonProcessingException {
        List<String> strings = new ArrayList<String>();
        ObjectMapper mapper = new ObjectMapper();
        String parse = parse(body, field);
        JSONArray objects = new JSONArray(parse);

        for (Object object : objects) {
            if (object != null) {
                strings.add(String.valueOf(object));
            }
        }
        return strings;
    }

}
