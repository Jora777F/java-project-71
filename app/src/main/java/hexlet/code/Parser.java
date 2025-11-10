package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.util.Map;

public class Parser {

    public static final ObjectMapper JSON_MAPPER = new JsonMapper();
    public static final ObjectMapper YAML_MAPPER = new YAMLMapper();

    public static Map<String, Object> parsingFormat(String data, String format) throws Exception {
        return switch (format) {
            case "json" -> JSON_MAPPER.readValue(data, new TypeReference<>() {});
            case "yaml", "yml" -> YAML_MAPPER.readValue(data, new TypeReference<>() {});
            default -> throw new IllegalArgumentException("Unsupported format: " + format);
        };
    }
}
