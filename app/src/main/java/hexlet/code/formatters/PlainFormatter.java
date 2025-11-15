package hexlet.code.formatters;

import hexlet.code.DiffNode;

import java.util.List;
import java.util.Map;

public class PlainFormatter {

    public static String format(List<DiffNode> diff) {
        StringBuilder result = new StringBuilder();

        for (DiffNode node : diff) {
            String key = node.key().toString();

            switch (node.status()) {
                case ADDED -> result.append("Property '")
                        .append(key)
                        .append("' was added with value: ")
                        .append(formatValue(node.newValue()))
                        .append("\n");
                case REMOVED -> result.append("Property '")
                        .append(key)
                        .append("' was removed\n");
                case CHANGED -> result.append("Property '")
                        .append(key)
                        .append("' was updated. From ")
                        .append(formatValue(node.oldValue()))
                        .append(" to ")
                        .append(formatValue(node.newValue()))
                        .append("\n");
                case UNCHANGED -> {
                    // В plain формате неизмененные значения не выводятся
                }
                default -> throw new IllegalStateException("Unexpected node status: " + node.status());
            }
        }

        return result.toString().trim();
    }

    private static String formatValue(Object value) {
        if (value == null) {
            return "null";
        }
        if (value instanceof String) {
            return "'" + value + "'";
        }
        if (value instanceof Map || value instanceof List) {
            return "[complex value]";
        }
        return value.toString();
    }
}
