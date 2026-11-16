package za.ac.iie.programming1a;
import java.util.Arrays;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class MessageManager {

    // Array to store sent messages
    private Message[] sentMessages;

    // Array to store disregarded messages
    private Message[] disregardedMessages;

    // Array to store stored messages
    private Message[] storedMessages;

    // Array to store message hashes
    private String[] messageHashes;

    // Array to store message IDs
    private String[] messageIDs;


    // Constructor to initialise the arrays
    public MessageManager() {

        sentMessages = new Message[0];

        disregardedMessages = new Message[0];

        storedMessages = new Message[0];

        messageHashes = new String[0];

        messageIDs = new String[0];
    }

    // Method to add messages to the correct arrays
    public void addMessage(Message message) {

        // Add message hash to the hash array
        messageHashes = Arrays.copyOf(messageHashes, messageHashes.length + 1);
        messageHashes[messageHashes.length - 1] = message.getMessageHash();

        // Add message ID to the ID array
        messageIDs = Arrays.copyOf(messageIDs, messageIDs.length + 1);
        messageIDs[messageIDs.length - 1] = message.getMessageID();

        // Check the message status
        if (message.getMessageStatus().equalsIgnoreCase("Sent")) {

            sentMessages = Arrays.copyOf(sentMessages, sentMessages.length + 1);
            sentMessages[sentMessages.length - 1] = message;

        } else if (message.getMessageStatus().equalsIgnoreCase("Stored")) {

            storedMessages = Arrays.copyOf(storedMessages, storedMessages.length + 1);
            storedMessages[storedMessages.length - 1] = message;

        } else if (message.getMessageStatus().equalsIgnoreCase("Disregarded")) {

            disregardedMessages = Arrays.copyOf(disregardedMessages, disregardedMessages.length + 1);
            disregardedMessages[disregardedMessages.length - 1] = message;
        }
    }
    public Message[] getSentMessages() {
        return sentMessages;
    }

    public Message[] getDisregardedMessages() {
        return disregardedMessages;
    }

    public Message[] getStoredMessages() {
        return storedMessages;
    }

    public String[] getMessageHashes() {
        return messageHashes;
    }

    public String[] getMessageIDs() {
        return messageIDs;
    }

    public void loadStoredMessages() {
        try (FileReader reader = new FileReader("messages.json")) {

            Gson gson = new Gson();

            Type messageListType = new TypeToken<List<Message>>() {}.getType();

            List<Message> loadedMessages = gson.fromJson(reader, messageListType);

            if (loadedMessages != null) {
                for (Message message : loadedMessages) {

                    if (message.getMessageStatus() != null
                            && message.getMessageStatus().equalsIgnoreCase("Stored")) {

                        addMessage(message);
                    }
                }
            }

            System.out.println("Stored messages loaded successfully.");

        } catch (IOException e) {
            System.out.println("Error loading stored messages: " + e.getMessage());
        }
    }
    public String deleteMessageByHash(String hash) {

        int index = -1;

        for (int i = 0; i < storedMessages.length; i++) {
            if (storedMessages[i].getMessageHash().equals(hash)) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            return "Message hash not found.";
        }

        Message deletedMessage = storedMessages[index];

        storedMessages = removeMessage(storedMessages, index);

        messageHashes = removeString(messageHashes,
                deletedMessage.getMessageHash());

        messageIDs = removeString(messageIDs,
                deletedMessage.getMessageID());

        try (FileReader reader = new FileReader("messages.json")) {

            Gson gson = new Gson();

            Type messageListType =
                    new TypeToken<List<Message>>() {}.getType();

            List<Message> allStoredMessages =
                    gson.fromJson(reader, messageListType);

            if (allStoredMessages != null) {
                allStoredMessages.removeIf(message ->
                        message.getMessageHash().equals(hash));

                Gson prettyGson = new GsonBuilder()
                        .setPrettyPrinting()
                        .create();

                try (FileWriter writer = new FileWriter("messages.json")) {
                    prettyGson.toJson(allStoredMessages, writer);
                }
            }

            return "Message: \"" + deletedMessage.getMessageText()
                    + "\" successfully deleted.";

        } catch (IOException e) {
            return "Error updating messages.json: " + e.getMessage();
        }
    }

    private Message[] removeMessage(Message[] array, int index) {

        Message[] newArray = new Message[array.length - 1];

        for (int i = 0, j = 0; i < array.length; i++) {
            if (i != index) {
                newArray[j++] = array[i];
            }
        }

        return newArray;
    }

    private String[] removeString(String[] array, String value) {

        int index = -1;

        for (int i = 0; i < array.length; i++) {
            if (value.equals(array[i])) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            return array;
        }

        String[] newArray = new String[array.length - 1];

        for (int i = 0, j = 0; i < array.length; i++) {
            if (i != index) {
                newArray[j++] = array[i];
            }
        }

        return newArray;
    }
    public String getSentMessageReport() {

        StringBuilder report = new StringBuilder();

        for (Message message : sentMessages) {
            report.append("Message Hash: ")
                    .append(message.getMessageHash())
                    .append("\n");

            report.append("Recipient: ")
                    .append(message.getRecipient())
                    .append("\n");

            report.append("Message: ")
                    .append(message.getMessageText())
                    .append("\n");
        }

        return report.toString();
    }
}
