package hexlet.code;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public class DiffBuilder {

    /**
     * Вычисляет различия между двумя структурами данных.
     *
     * @param data1 первая структура
     * @param data2 вторая структура
     * @return список с различиями
     */
    public static List<DiffNode> buildDiff(Map<String, Object> data1, Map<String, Object> data2) {
        List<DiffNode> diff = new ArrayList<>();

        // Собираем все ключи и сортируем
        Set<String> allKeys = new TreeSet<>();
        allKeys.addAll(data1.keySet());
        allKeys.addAll(data2.keySet());

        for (String key : allKeys) {
            boolean inFile1 = data1.containsKey(key);
            boolean inFile2 = data2.containsKey(key);

            if (inFile1 && inFile2) {
                Object value1 = data1.get(key);
                Object value2 = data2.get(key);

                if (Objects.equals(value1, value2)) {
                    diff.add(new DiffNode(key, value1, value2, DiffNode.Status.UNCHANGED));
                } else {
                    diff.add(new DiffNode(key, value1, value2, DiffNode.Status.CHANGED));
                }
            } else if (inFile1) {
                diff.add(new DiffNode(key, data1.get(key), null, DiffNode.Status.REMOVED));
            } else {
                diff.add(new DiffNode(key, null, data2.get(key), DiffNode.Status.ADDED));
            }
        }

        return diff;
    }
}
