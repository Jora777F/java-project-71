package hexlet.code;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Differ {

    /**
     * Метод сравнивает два файла и генерирует на их основе строку в выбранном формате.
     *
     * @param filepath1 путь до первого файла
     * @param filepath2 путь до второго файла
     * @param format    формат текста, который должен быть на выходе
     * @return сгенерированная строка
     * @throws Exception исключение, которое может возникнуть во время работы
     */
    public static String generate(String filepath1, String filepath2, String format) throws Exception {
        Path path1 = Paths.get(filepath1).toAbsolutePath().normalize();
        Path path2 = Paths.get(filepath2).toAbsolutePath().normalize();

        // Читаем содержимое файлов
        String content1 = Files.readString(path1);
        String content2 = Files.readString(path2);

        // Определяем формат файлов по расширению
        String fileFormat = getFileFormat(filepath1);

        // Парсинг файлов в Map
        Map<String, Object> data1 = Parser.parsingFormat(content1, fileFormat);
        Map<String, Object> data2 = Parser.parsingFormat(content2, fileFormat);

        // Собираем все ключи и сортируем
        Set<String> allKeys = new TreeSet<>();
        allKeys.addAll(data1.keySet());
        allKeys.addAll(data2.keySet());

        StringBuilder result = new StringBuilder("{\n");

        for (String key : allKeys) {
            boolean inFile1 = data1.containsKey(key);
            boolean inFile2 = data2.containsKey(key);

            if (inFile1 && inFile2) {
                Object value1 = data1.get(key);
                Object value2 = data2.get(key);

                if (isEqual(value1, value2)) {
                    // Значения одинаковые
                    result.append("    ").append(key).append(": ").append(value1).append("\n");
                } else {
                    // Значения разные - сначала из первого файла, потом из второго
                    result.append("  - ").append(key).append(": ").append(value1).append("\n");
                    result.append("  + ").append(key).append(": ").append(value2).append("\n");
                }

            } else if (inFile1) {
                // Ключ только в первом файле
                result.append("  - ").append(key).append(": ").append(data1.get(key)).append("\n");
            } else {
                // Ключ только во втором файле
                result.append("  + ").append(key).append(": ").append(data2.get(key)).append("\n");
            }
        }

        result.append("}");
        return result.toString();
    }

    private static String getFileFormat(String filepath) {
        if (filepath.endsWith(".json")) {
            return "json";
        } else if (filepath.endsWith(".yml") || filepath.endsWith(".yaml")) {
            return "yaml";
        }

        throw new IllegalArgumentException("Unsupported file format: " + filepath);
    }

    private static boolean isEqual(Object obj1, Object obj2) {
        if (obj1 == null && obj2 == null) {
            return true;
        }
        if (obj1 == null || obj2 == null) {
            return false;
        }
        return obj1.equals(obj2);
    }
}
