package za.ac.iie.programming1a;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainTest {

    @Test
    void testMainWithCorrectDetails() {

        String input =
                "John\n" +
                        "Smith\n" +
                        "kyl_1\n" +
                        "Ch&sec@ke99!\n" +
                        "+27838968976\n" +
                        "kyl_1\n" +
                        "Ch&sec@ke99!\n";

        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        Main.main(new String[]{});

        String result = output.toString();

        assertTrue(result.contains("Registration successful."));
        assertTrue(result.contains(
                "Welcome John Smith, it is great to see you again."
        ));
    }
}