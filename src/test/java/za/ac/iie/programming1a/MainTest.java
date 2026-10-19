
package za.ac.iie.programming1a;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

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
                        "Ch&sec@ke99!\n" +
                        "0\n" +
                        "3\n";

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

    @Test
    void testMessageIDLength() {

        Message message = new Message(
                "1234567890", 0, "+27718693002",
                "Hi thanks", "", ""
        );

        assertTrue(message.checkMessageID());
    }

    @Test
    void testMessageIDTooLong() {

        Message message = new Message(
                "12345678901", 0, "+27718693002",
                "Hi thanks", "", ""
        );

        assertFalse(message.checkMessageID());
    }

    @Test
    void testMessageLengthSuccess() {

        Message message = new Message(
                "0012345678", 0, "+27718693002",
                "Hi thanks", "", ""
        );

        assertTrue(message.checkMessageLength());
    }

    @Test
    void testMessageHash() {

        Message message = new Message(
                "0012345678", 0, "+27718693002",
                "Hi thanks", "", ""
        );

        assertEquals("00:0:HITHANKS", message.createMessageHash());
    }

    @Test
    void testSendMessage() {

        Message message = new Message(
                "0012345678", 0, "+27718693002",
                "Hi thanks", "", ""
        );

        assertEquals(
                "Message successfully sent.",
                message.SentMessage("send")
        );
    }

    @Test
    void testDisregardMessage() {

        Message message = new Message(
                "0012345678", 0, "+27718693002",
                "Hi thanks", "", ""
        );

        assertEquals(
                "Press O to delete the message.",
                message.SentMessage("disregard")
        );
    }

    @Test
    void testStoreMessage() {

        Message message = new Message(
                "0012345678", 0, "+27718693002",
                "Hi thanks", "", ""
        );

        assertEquals(
                "Message successfully stored.",
                message.SentMessage("store")
        );
    }
}

