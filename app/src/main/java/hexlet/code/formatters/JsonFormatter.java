package hexlet.code.formatters;

import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.DiffNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mikhail
 * <p>
 * Данный класс создает валидный JSON массив элементов.
 */
public class JsonFormatter {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static String format(List<DiffNode> diff) throws Exception {
        List<Map<String, Object>> jsonDiff = new ArrayList<>();

        for (DiffNode node : diff) {
            Map<String, Object> entry = new HashMap<>();
            entry.put("key", node.key());
            entry.put("status", node.status().toString().toLowerCase());

            switch (node.status()) {
                case UNCHANGED -> entry.put("value", node.oldValue());
                case CHANGED -> {
                    entry.put("oldValue", node.oldValue());
                    entry.put("newValue", node.newValue());
                }
                case REMOVED -> entry.put("value", node.oldValue());
                case ADDED -> entry.put("value", node.newValue());
                default -> throw new IllegalStateException("Unexpected node status: " + node.status());
            }

            jsonDiff.add(entry);
        }

        return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(jsonDiff);
    }
}
