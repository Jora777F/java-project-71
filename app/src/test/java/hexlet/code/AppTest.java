package hexlet.code;

import org.junit.jupiter.api.Test;
import picocli.CommandLine;

class AppTest {

    @Test
    public void testCommandLineArguments() {
        String[] args = {
                "src/test/resources/fixtures/file1.json ",
                "src/test/resources/fixtures/file2.json ",
                "-f",
                "stylish"
        };
        App app = new App();
        new CommandLine(app).parseArgs(args);

        System.out.println("Hello World!");
    }
}