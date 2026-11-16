
        package za.ac.iie.programming1a;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("================================");
        System.out.println("      REGISTRATION SYSTEM");
        System.out.println("================================");

        System.out.print("Enter your first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter your last name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter a username: ");
        String username = scanner.nextLine();

        System.out.print("Enter a password: ");
        String password = scanner.nextLine();

        System.out.print("Enter your cellphone number: ");
        String cellphoneNumber = scanner.nextLine();

        Login user = new Login(
                username,
                password,
                cellphoneNumber,
                firstName,
                lastName
        );

        String registrationMessage = user.registerUser();

        System.out.println();
        System.out.println(registrationMessage);

        if (registrationMessage.equals("Registration successful.")) {

            System.out.println();
            System.out.println("================================");
            System.out.println("             LOGIN");
            System.out.println("================================");

            System.out.print("Enter your username: ");
            String enteredUsername = scanner.nextLine();

            System.out.print("Enter your password: ");
            String enteredPassword = scanner.nextLine();

            boolean loginStatus =
                    user.loginUser(enteredUsername, enteredPassword);

            System.out.println();
            System.out.println(user.returnLoginStatus(loginStatus));

            if (loginStatus) {

                System.out.println();
                System.out.println("Welcome to QuickChat.");

                int numberOfMessages = -1;

                while (numberOfMessages < 0) {
                    System.out.print(
                            "How many messages would you like to enter? "
                    );

                    if (scanner.hasNextInt()) {
                        numberOfMessages = scanner.nextInt();
                        scanner.nextLine();

                        if (numberOfMessages < 0) {
                            System.out.println(
                                    "Please enter zero or a positive number."
                            );
                        }
                    } else {
                        System.out.println("Please enter a valid whole number.");
                        scanner.nextLine();
                    }
                }

                ArrayList<Message> messages = new ArrayList<>();

                MessageManager messageManager = new MessageManager();
                messageManager.loadStoredMessages();
                int choice = 0;
                int messageNumber = 0;

                while (choice != 3) {

                    System.out.println();
                    System.out.println("================================");
                    System.out.println("          QUICKCHAT MENU");
                    System.out.println("================================");
                    System.out.println("1) Send Messages");
                    System.out.println("2) Show Messages");
                    System.out.println("3) Quit");
                    System.out.println("4) Stored Messages");
                    System.out.print("Enter your choice: ");

                    if (!scanner.hasNextInt()) {
                        System.out.println("Please enter 1, 2, 3, or 4.");
                        scanner.nextLine();
                        continue;
                    }

                    choice = scanner.nextInt();
                    scanner.nextLine();

                    switch (choice) {

                        case 1:
                            if (messages.size() >= numberOfMessages) {
                                System.out.println(
                                        "You have reached your message limit."
                                );
                                break;
                            }

                            System.out.print(
                                    "Enter recipient cellphone number (+27XXXXXXXXX): "
                            );
                            String recipient = scanner.nextLine();

                            System.out.print("Enter your message: ");
                            String messageText = scanner.nextLine();

                            String messageID = String.format(
                                    "%010d",
                                    new Random().nextInt(1_000_000_000)
                            );

                            Message message = new Message(
                                    messageID,
                                    messageNumber,
                                    recipient,
                                    messageText,
                                    "",
                                    ""
                            );

                            if (!message.checkMessageID()) {
                                System.out.println("Invalid message ID.");
                                break;
                            }

                            String recipientResult = message.checkRecipientCell();

                            if (!recipientResult.equals("Cell phone number successfully captured.")) {
                                System.out.println(recipientResult);
                                break;
                            }

                            if (!message.checkMessageLength()) {
                                System.out.println(
                                        "Message is too long. Maximum is 250 characters."
                                );
                                break;
                            }

                            String messageHash = message.createMessageHash();

                            System.out.println();
                            System.out.println("Message details:");
                            System.out.println("Message ID: " + messageID);
                            System.out.println("Message Hash: " + messageHash);
                            System.out.println("Recipient: " + recipient);
                            System.out.println("Message: " + messageText);

                            System.out.println();
                            System.out.println("Choose an action:");
                            System.out.println("1) Send");
                            System.out.println("2) Store");
                            System.out.println("3) Disregard");
                            System.out.print("Enter your choice: ");

                            String actionChoice = scanner.nextLine();
                            String action;

                            switch (actionChoice) {
                                case "1":
                                    action = "send";
                                    break;
                                case "2":
                                    action = "store";
                                    break;
                                case "3":
                                    action = "disregard";
                                    break;
                                default:
                                    System.out.println("Invalid action.");
                                    break;
                            }

                            if (actionChoice.equals("1")
                                    || actionChoice.equals("2")
                                    || actionChoice.equals("3")) {

                                if (actionChoice.equals("2")) {
                                    System.out.println(message.storeMessage());
                                } else {
                                    System.out.println(
                                            message.SentMessage(
                                                    actionChoice.equals("1") ? "send" : "disregard"
                                            )
                                    );
                                }

                                messageManager.addMessage(message);

                                if (actionChoice.equals("1") || actionChoice.equals("2")) {
                                    messages.add(message);
                                    messageNumber++;
                                }

                            } else {
                                System.out.println("Invalid action.");
                            }


                            break;

                        case 2:
                            if (messages.isEmpty()) {
                                System.out.println("No messages available.");
                            } else {
                                System.out.println();
                                System.out.println("Messages:");

                                for (Message savedMessage : messages) {
                                    System.out.println("----------------------------");
                                    System.out.println(
                                            "Message ID: "
                                                    + savedMessage.getMessageID()
                                    );
                                    System.out.println(
                                            "Recipient: "
                                                    + savedMessage.getRecipient()
                                    );
                                    System.out.println(
                                            "Message: "
                                                    + savedMessage.getMessageText()
                                    );
                                    System.out.println(
                                            "Hash: "
                                                    + savedMessage.getMessageHash()
                                    );
                                }
                            }
                            break;

                        case 3:
                            System.out.println("Total messages sent: " + Message.returnTotalMessages());
                            System.out.println("Exiting QuickChat. Goodbye!");
                            break;

                        case 4: {
                            boolean returnToMenu = false;

                            while (!returnToMenu) {
                                System.out.println();
                                System.out.println("================================");
                                System.out.println("        STORED MESSAGES");
                                System.out.println("================================");
                                System.out.println("A) Display senders and recipients");
                                System.out.println("B) Display longest stored message");
                                System.out.println("C) Search message by ID");
                                System.out.println("D) Search messages by recipient");
                                System.out.println("E) Delete message using message hash");
                                System.out.println("F) Display full message report");
                                System.out.println("X) Return to main menu");
                                System.out.print("Enter your choice: ");

                                String storedChoice = scanner.nextLine();

                                switch (storedChoice.toUpperCase()) {

                                    case "A":
                                        Message[] storedMessages =
                                                messageManager.getStoredMessages();

                                        if (storedMessages.length == 0) {
                                            System.out.println("No stored messages available.");
                                        } else {
                                            for (Message storedMessage : storedMessages) {
                                                System.out.println("----------------------------");
                                                System.out.println("Sender: " + firstName + " " + lastName);
                                                System.out.println("Recipient: " + storedMessage.getRecipient());
                                            }
                                        }
                                        break;

                                    case "B":
                                        Message[] storedMessagesForLength =
                                                messageManager.getStoredMessages();

                                        if (storedMessagesForLength.length == 0) {
                                            System.out.println("No stored messages available.");
                                        } else {
                                            Message longestMessage = storedMessagesForLength[0];

                                            for (Message currentMessage : storedMessagesForLength) {
                                                if (currentMessage.getMessageText().length()
                                                        > longestMessage.getMessageText().length()) {

                                                    longestMessage = currentMessage;
                                                }
                                            }

                                            System.out.println("Longest stored message:");
                                            System.out.println(longestMessage.getMessageText());
                                            System.out.println("Length: "
                                                    + longestMessage.getMessageText().length() +
                                                    " characters");
                                        }
                                        break;

                                    case "C":
                                        System.out.print("Enter the message ID to search for: ");
                                        String searchID = scanner.nextLine();

                                        Message[] messagesToSearch = messageManager.getStoredMessages();

                                        boolean messageFound = false;

                                        for (Message searchedMessage : messagesToSearch) {

                                            if (searchedMessage.getMessageID().equals(searchID)) {

                                                System.out.println("Message found!");
                                                System.out.println("Recipient: "
                                                        + searchedMessage.getRecipient());
                                                System.out.println("Message: "
                                                        + searchedMessage.getMessageText());

                                                messageFound = true;
                                                break;
                                            }
                                        }

                                        if (!messageFound) {
                                            System.out.println("Message ID not found.");
                                        }

                                        break;

                                    case "D":
                                        System.out.print("Enter the recipient cellphone number: ");
                                        String searchRecipient = scanner.nextLine();

                                        Message[] recipientMessages =
                                                messageManager.getStoredMessages();

                                        boolean recipientFound = false;

                                        for (Message recipientMessage : recipientMessages) {

                                            if (recipientMessage.getRecipient()
                                                    .equals(searchRecipient)) {

                                                System.out.println("----------------------------");
                                                System.out.println("Recipient: "
                                                        + recipientMessage.getRecipient());
                                                System.out.println("Message: "
                                                        + recipientMessage.getMessageText());

                                                recipientFound = true;
                                            }
                                        }

                                        if (!recipientFound) {
                                            System.out.println("No messages found for this recipient.");
                                        }

                                        break;

                                    case "E":
                                        System.out.print("Enter the message hash to delete: ");
                                        String hashToDelete = scanner.nextLine();

                                        String deleteResult =
                                                messageManager.deleteMessageByHash(hashToDelete);

                                        System.out.println(deleteResult);

                                        break;

                                    case "F":
                                        Message[] reportMessages = messageManager.getStoredMessages();

                                        if (reportMessages.length == 0) {
                                            System.out.println("No stored messages available.");
                                        } else {
                                            System.out.println("\n========== STORED MESSAGE REPORT ==========");

                                            for (Message reportMessage : reportMessages) {
                                                System.out.println("-------------------------------------------");
                                                System.out.println("Message Hash: "
                                                        + reportMessage.getMessageHash());
                                                System.out.println("Recipient: "
                                                        + reportMessage.getRecipient());
                                                System.out.println("Message: "
                                                        + reportMessage.getMessageText());
                                            }

                                            System.out.println("===========================================");
                                        }
                                        break;

                                    case "X":
                                        returnToMenu = true;
                                        break;

                                    default:
                                        System.out.println("Invalid choice. Please try again.");
                                }
                            }

                            break;
                        }
                        default:
                            System.out.println(
                                    "Invalid choice. Please try again."
                            );
                    }
                }
            }
        }

        scanner.close();
    }
}

