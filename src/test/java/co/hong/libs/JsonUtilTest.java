package co.hong.libs;

import co.hong.libs.json.JsonUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import jdk.jfr.Description;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class JsonUtilTest {

    @Test
    @Description("getJson 메서드는 String을 반환한다.")
    void getJson() {
        var testObject = new TestObject();
        testObject.setKey("first");
        String json = JsonUtil.getJson(testObject);
        assertEquals("{\"key\":\"first\"}", json);

        // null을 json 문자열로 변환하면 null 이다.
        assertEquals("null", JsonUtil.getJson(null));

        assertNull(JsonUtil.getJson(new JacksonCannotSerialize()));
    }

    @Test
    @Description("json을 파싱하여 값을 추출할 수 있다.")
    public void testParseAsMap() {
        String json = "{ \"value\":\"jason\", \"num\":1 }";
        Map<String, Object> parsed = JsonUtil.parseAsMap(json);
        assertEquals(2, parsed.size());
        assertEquals("jason", parsed.get("value"));
        assertTrue(parsed.get("num") instanceof Number);

        assertThrows(RuntimeException.class, () -> JsonUtil.parseAsMap(null));
        assertThrows(RuntimeException.class, () -> JsonUtil.parseAsMap(""));
        assertThrows(RuntimeException.class, () -> JsonUtil.parseAsMap("{null:\"\"}"));

        // parseAsMapQuietly 테스트
        assertNull(JsonUtil.parseAsMapQuietly(null));
        assertNull(JsonUtil.parseAsMapQuietly(""));
    }

    @Getter
    @Setter
    public static class TestObject {
        @JsonProperty("key")
        private String key;
    }

    public static class JacksonCannotSerialize {
        @JsonProperty("name")
        public String getName() {
            throw new RuntimeException();
        }
    }

}
