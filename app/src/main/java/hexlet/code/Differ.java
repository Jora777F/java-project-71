package hexlet.code;

import hexlet.code.formatters.Formatter;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Differ {

    /**
     * Метод сравнивает два файла и генерирует на их основе строку в формате stylish.
     *
     * @param filepath1 путь до первого файла
     * @param filepath2 путь до второго файла
     * @return сгенерированная строка
     * @throws Exception исключение, которое может возникнуть во время работы
     */
    public static String generate(String filepath1, String filepath2) throws Exception {
        return generate(filepath1, filepath2, "stylish");
    }

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

        // Определяем формат файлов
        String fileFormat = getFileFormat(filepath1);

        // Парсинг файлов в Map
        Map<String, Object> data1 = Parser.parsingFormat(content1, fileFormat);
        Map<String, Object> data2 = Parser.parsingFormat(content2, fileFormat);

        List<DiffNode> diff = buildDiff(data1, data2);

        return Formatter.format(diff, format);
    }

    /**
     * Вычисляет различия между файлами и возвращает список {@link DiffNode}.
     *
     * @param data1 первая структура
     * @param data2 вторая структура
     * @return список с различиями
     */
    private static List<DiffNode> buildDiff(Map<String, Object> data1, Map<String, Object> data2) {
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

                if (isEqual(value1, value2)) {
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

    /**
     * Возвращает формат (расширение) файла.
     * <p>Если у файла отсутствует расширение, выбрасывается {@link IllegalArgumentException}.</p>
     * @param filepath файл, для которого следует определить формат
     * @return расширение файла
     */
    private static String getFileFormat(String filepath) {
        int lastDotIndex = filepath.lastIndexOf(".");
        if (lastDotIndex == -1 || lastDotIndex == filepath.length() - 1) {
            throw new IllegalArgumentException("File has no extension: " + filepath);
        }
        return filepath.substring(lastDotIndex + 1);
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
