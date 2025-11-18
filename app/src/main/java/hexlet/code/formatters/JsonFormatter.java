package hexlet.code.formatters;

import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.DiffNode;

import java.util.List;

/**
 * @author mikhail
 * <p>
 * Данный класс создает валидный JSON массив элементов.
 */
public class JsonFormatter {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static String format(List<DiffNode> diff) throws Exception {
        return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(diff);
    }
}
