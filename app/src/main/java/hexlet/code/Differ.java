package hexlet.code;

import hexlet.code.formatters.Formatter;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

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

        String fileFormat = getFileFormat(filepath1);

        // Парсинг файлов в Map
        Map<String, Object> data1 = Parser.parsingFormat(content1, fileFormat);
        Map<String, Object> data2 = Parser.parsingFormat(content2, fileFormat);

        List<DiffNode> diff = DiffBuilder.buildDiff(data1, data2);

        return Formatter.format(diff, format);
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
}
