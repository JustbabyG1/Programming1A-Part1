package za.ac.iie.programming1a;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MessageManagerTest {

    @Test
    void testSentMessagesArray() {

        MessageManager manager = new MessageManager();

        Message message1 = new Message(
                "1111111111", 0, "+27834557896",
                "Did you get the cake?", "", "Sent"
        );

        Message message4 = new Message(
                "0838884567", 3, "0838884567",
                "It is dinner time !", "", "Sent"
        );

        manager.addMessage(message1);
        manager.addMessage(message4);

        Message[] sentMessages = manager.getSentMessages();

        assertEquals(2, sentMessages.length);

        assertEquals(
                "Did you get the cake?",
                sentMessages[0].getMessageText()
        );

        assertEquals(
                "It is dinner time !",
                sentMessages[1].getMessageText()
        );
    }
    @Test
    void testLongestStoredMessage() {

        MessageManager manager = new MessageManager();

        Message message1 = new Message(
                "1111111111", 0, "+27834557896",
                "Did you get the cake?", "", "Stored"
        );

        Message message2 = new Message(
                "2222222222", 1, "+27838884567",
                "Where are you? You are late! I have asked you to be on time.",
                "", "Stored"
        );

        manager.addMessage(message1);
        manager.addMessage(message2);

        Message[] storedMessages = manager.getStoredMessages();

        Message longestMessage = storedMessages[0];

        for (Message currentMessage : storedMessages) {
            if (currentMessage.getMessageText().length()
                    > longestMessage.getMessageText().length()) {
                longestMessage = currentMessage;
            }
        }

        assertEquals(
                "Where are you? You are late! I have asked you to be on time.",
                longestMessage.getMessageText()
        );
    }

    @Test
    void testSearchMessageByID() {

        MessageManager manager = new MessageManager();

        Message message = new Message(
                "0838884567", 3, "0838884567",
                "It is dinner time !", "", "Stored"
        );

        manager.addMessage(message);

        Message foundMessage = null;

        for (Message currentMessage : manager.getStoredMessages()) {
            if (currentMessage.getMessageID().equals("0838884567")) {
                foundMessage = currentMessage;
                break;
            }
        }

        assertEquals(
                "It is dinner time !",
                foundMessage.getMessageText()
        );
    }
    @Test
    void testSearchMessagesByRecipient() {

        MessageManager manager = new MessageManager();

        Message message1 = new Message(
                "1111111111", 1, "+27838884567",
                "Where are you? You are late! I have asked you to be on time.",
                "", "Stored"
        );

        Message message2 = new Message(
                "2222222222", 2, "+27838884567",
                "Ok, I am leaving without you.", "", "Stored"
        );

        Message message3 = new Message(
                "3333333333", 3, "+27834557896",
                "Did you get the cake?", "", "Stored"
        );

        manager.addMessage(message1);
        manager.addMessage(message2);
        manager.addMessage(message3);

        int foundMessages = 0;

        for (Message message : manager.getStoredMessages()) {
            if (message.getRecipient().equals("+27838884567")) {
                foundMessages++;
            }
        }

        assertEquals(2, foundMessages);
    }
    @Test
    void testDeleteMessageByHash() {

        MessageManager manager = new MessageManager();

        Message message = new Message(
                "2222222222", 1, "+27838884567",
                "Where are you? You are late! I have asked you to be on time.",
                "22:1:WHERETIME", "Stored"
        );

        manager.addMessage(message);

        String result = manager.deleteMessageByHash("22:1:WHERETIME");

        assertEquals(
                "Message: \"Where are you? You are late! I have asked you to be on time.\" successfully deleted.",
                result
        );

        assertEquals(0, manager.getStoredMessages().length);
    }
    @Test
    void testDisplayMessageReport() {

        MessageManager manager = new MessageManager();

        Message message1 = new Message(
                "1111111111", 0, "+27834557896",
                "Did you get the cake?",
                "11:0:DIDCAKE", "Sent"
        );

        Message message4 = new Message(
                "0838884567", 3, "0838884567",
                "It is dinner time !",
                "08:3:ITSDINNER", "Sent"
        );

        manager.addMessage(message1);
        manager.addMessage(message4);

        String report = manager.getSentMessageReport();

        assertTrue(report.contains("Did you get the cake?"));
        assertTrue(report.contains("It is dinner time !"));
        assertTrue(report.contains("Message Hash:"));
        assertTrue(report.contains("Recipient:"));
        assertTrue(report.contains("Message:"));
    }
}