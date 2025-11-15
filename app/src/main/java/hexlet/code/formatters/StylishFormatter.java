package hexlet.code.formatters;

import hexlet.code.DiffNode;

import java.util.List;

public class StylishFormatter {

    public static String format(List<DiffNode> diff) {
        StringBuilder result = new StringBuilder("{\n");

        for (DiffNode node : diff) {
            switch (node.status()) {
                case UNCHANGED -> result.append("    ")
                        .append(node.key())
                        .append(": ")
                        .append(stringify(node.oldValue()))
                        .append("\n");
                case CHANGED -> {
                    result.append("  - ")
                            .append(node.key())
                            .append(": ")
                            .append(stringify(node.oldValue()))
                            .append("\n");
                    result.append("  + ")
                            .append(node.key())
                            .append(": ")
                            .append(stringify(node.newValue()))
                            .append("\n");
                }
                case REMOVED -> result.append("  - ")
                        .append(node.key())
                        .append(": ")
                        .append(stringify(node.oldValue()))
                        .append("\n");
                case ADDED -> result.append("  + ")
                        .append(node.key())
                        .append(": ")
                        .append(stringify(node.newValue()))
                        .append("\n");
                default -> throw new IllegalStateException("Unexpected node status: " + node.status());
            }
        }

        result.append("}");
        return result.toString();
    }

    private static String stringify(Object value) {
        if (value == null) {
            return "null";
        }
        return value.toString();
    }
}
