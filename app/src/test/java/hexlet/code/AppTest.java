package hexlet.code;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AppTest {

    private static String jsonOutput;
    private static String plainOutput;
    private static String stylishOutput;

    @BeforeAll
    public static void beforeAll() throws Exception {
        jsonOutput = readFixture("expected/json_output.txt");
        plainOutput = readFixture("expected/plain_output.txt");
        stylishOutput = readFixture("expected/stylish_output.txt");
    }

    private static String readFixture(String fileName) throws Exception {
        return Files.readString(Paths.get(getFixtureAbsolutePath(fileName))).trim();
    }

    private static String getFixtureAbsolutePath(String fileName) {
        String resourcesPath = new File("src/test/resources").getAbsolutePath();
        return resourcesPath + "/fixtures/" + fileName;
    }

    @ParameterizedTest
    @ValueSource(strings = {".json", ".yaml"})
    public void generateTest(String format) throws Exception {
        String filePath1 = getFixtureAbsolutePath("file1" + format);
        String filePath2 = getFixtureAbsolutePath("file2" + format);

        assertEquals(normalize(stylishOutput), Differ.generate(filePath1, filePath2, "stylish"));
        assertEquals(normalize(plainOutput), Differ.generate(filePath1, filePath2, "plain"));
        assertEquals(jsonOutput, Differ.generate(filePath1, filePath2, "json"));
    }

    private static String normalize(String str) {
        return str.replace("\r\n", "\n").replace("\r", "\n");
    }
}
