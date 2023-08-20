package co.hong.libs.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.util.Map;

public class JsonUtil {

    private static final ObjectMapper om;
    public static final TypeReference<Map<String, Object>> MAP_TYPE_REFERENCE;

    static {
        om = new ObjectMapper();
        om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        MAP_TYPE_REFERENCE = new TypeReference<>() {
        };
    }
    public static Map<String, Object> parseAsMapQuietly(final String json) {
        try {
            return parseAsMap(json);
        } catch (RuntimeException e) {
            return null;
        }
    }

    public static Map<String, Object> parseAsMap(final String json) {
        try {
            return om.readValue(json, MAP_TYPE_REFERENCE);
        } catch (IOException e) {
            // json parameter가 null 이거나 유효하지 않아서 JSON 파싱이 실패할 경우, 발생
            throw new RuntimeException(e);
        }
    }
}
