package za.ac.iie.programming1a;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Message {
    private static ArrayList<Message> allMessages = new ArrayList<>();
    private static int totalMessagesSent = 0;

    private String messageID;
    private int messageNumber;
    private String recipient;
    private String messageText;
    private String messageHash;
    private String messageStatus;

    public Message(String messageID, int messageNumber,
                   String recipient, String messageText,
                   String messageHash, String messageStatus) {

        this.messageID = messageID;
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageHash = messageHash;
        this.messageStatus = messageStatus;
    }

    public boolean checkMessageID() {
        return messageID != null && messageID.length() <= 10;
    }

    public String checkRecipientCell() {
        if (recipient != null && recipient.matches("^\\+27\\d{9}$")) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

    public String createMessageHash() {
        if (messageID == null || messageID.length() < 2
                || messageText == null || messageText.trim().isEmpty()) {
            return "";
        }

        String[] words = messageText.trim().split("\\s+");

        String firstWord = words[0];
        String lastWord = words[words.length - 1];

        messageHash = messageID.substring(0, 2) + ":"
                + messageNumber + ":"
                + firstWord + lastWord;

        messageHash = messageHash.toUpperCase();

        return messageHash;
    }

    public boolean checkMessageLength() {
        return messageText != null && messageText.length() <= 250;
    }

    public String SentMessage(String choice) {

        if (choice == null) {
            return "Invalid choice.";
        }

        if (choice.equalsIgnoreCase("send")) {
            messageStatus = "Sent";
            allMessages.add(this);
            totalMessagesSent++;
            return "Message successfully sent.";

        } else if (choice.equalsIgnoreCase("store")) {
            messageStatus = "Stored";
            return "Message successfully stored.";

        } else if (choice.equalsIgnoreCase("disregard")) {
            messageStatus = "Disregarded";
            return "Press O to delete the message.";

        } else {
            return "Invalid choice.";
        }
    }


    public String getMessageID() {
        return messageID;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessageText() {
        return messageText;
    }

    public String getMessageHash() {
        return messageHash;
    }

    public static String printMessages() {
        StringBuilder result = new StringBuilder();

        for (Message message : allMessages) {
            result.append("Message ID: ")
                    .append(message.messageID)
                    .append("\n");

            result.append("Message Hash: ")
                    .append(message.messageHash)
                    .append("\n");

            result.append("Recipient: ")
                    .append(message.recipient)
                    .append("\n");

            result.append("Message: ")
                    .append(message.messageText)
                    .append("\n\n");
        }

        return result.toString();
    }
    public static int returnTotalMessages() {
        return totalMessagesSent;
    }

    public String storeMessage() {
        messageStatus = "Stored";

        System.out.println("Status before saving: " + messageStatus);
        System.out.println("Saving file to: " + new java.io.File("messages.json").getAbsolutePath());

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileWriter writer = new FileWriter("messages.json", true)) {
            gson.toJson(this, writer);
            writer.write(System.lineSeparator());

            return "Message successfully stored.";
        } catch (IOException e) {
            return "Error storing message: " + e.getMessage();
        }
    }

}



