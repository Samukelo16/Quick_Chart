package com.mycompany.quick_chart;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Message {

    private static final int MAX_MESSAGE_LENGTH = 250;
    private static int totalMessagesSent = 0;

    private final String messageID;
    private final int messageNumber;
    private final String recipient;
    private final String messageText;
    private final String messageHash;

    public Message(int messageNumber, String recipient, String messageText) {
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageID = generateMessageID();
        this.messageHash = createMessageHash();
    }

    public boolean checkMessageLength() {
        if (messageText.length() <= MAX_MESSAGE_LENGTH) {
            System.out.println("Message ready to send.");
            return true;
        }

        int excess = messageText.length() - MAX_MESSAGE_LENGTH;
        System.out.println("Message exceeds 250 characters by " + excess + ".");
        return false;
    }

    public String checkRecipientCell() {
        if (Login.checkPhoneNumber(recipient)) {
            return "Cell phone number successfully captured.";
        }

        return "Invalid phone number.";
    }

    public boolean hasValidRecipient() {
        return Login.checkPhoneNumber(recipient);
    }

    public String createMessageHash() {
        String firstTwo = messageID.substring(0, 2);
        String trimmedMessage = messageText.trim();

        if (trimmedMessage.isEmpty()) {
            return (firstTwo + ":" + messageNumber + ":EMPTY").toUpperCase();
        }

        String[] words = trimmedMessage.split("\\s+");
        String firstWord = words[0];
        String lastWord = words[words.length - 1];

        return (firstTwo + ":" + messageNumber + ":" + firstWord + lastWord).toUpperCase();
    }

    private String generateMessageID() {
        Random rand = new Random();
        long id = 1000000000L + (long) (rand.nextDouble() * 9000000000L);
        return String.valueOf(id);
    }

    public String sentMessage(Scanner scanner) {
        System.out.println("1. Send");
        System.out.println("2. Store");
        System.out.println("0. Delete");
        System.out.print("Choose an option: ");

        int choice = readInt(scanner);

        if (choice == 1) {
            totalMessagesSent++;
            return "Message successfully sent.";
        }

        if (choice == 2) {
            storeMessage();
            return "Message stored.";
        }

        if (choice == 0) {
            return "Message deleted.";
        }

        return "Invalid option.";
    }

    public void storeMessage() {
        try (FileWriter writer = new FileWriter("messages.json", true)) {
            writer.write("{\"id\":\"" + messageID + "\",");
            writer.write("\"hash\":\"" + messageHash + "\",");
            writer.write("\"recipient\":\"" + escapeJson(recipient) + "\",");
            writer.write("\"message\":\"" + escapeJson(messageText) + "\"}");
            writer.write(System.lineSeparator());
        } catch (IOException e) {
            System.out.println("Could not store message: " + e.getMessage());
        }
    }

    public String printMessage() {
        return "Message ID: " + messageID
                + "\nMessage Hash: " + messageHash
                + "\nRecipient: " + recipient
                + "\nMessage: " + messageText;
    }

    public static int returnTotalMessages() {
        return totalMessagesSent;
    }

    private int readInt(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.print("Please enter a number: ");
            scanner.nextLine();
        }

        int value = scanner.nextInt();
        scanner.nextLine();
        return value;
    }

    private String escapeJson(String value) {
        return value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\r", "\\r")
                .replace("\n", "\\n");
    }
}
