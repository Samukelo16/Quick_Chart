package com.mycompany.quick_chart;

import java.util.Scanner;

public class Quick_Chart {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Login login = new Login();

        if (login.registerAndLogin(scan)) {
            showMessagingMenu(scan);
        }

        scan.close();
    }

    public static void showMessagingMenu(Scanner scan) {
        System.out.println("\n=== Welcome to QuickChat! ===");

        int numMessages = readPositiveInt(scan, "How many messages do you want to send today? ");
        int messagesCreated = 0;
        boolean running = true;

        while (running) {
            System.out.println("\n=== QuickChat Menu ===");
            System.out.println("Messages created: " + messagesCreated + " of " + numMessages);
            System.out.println("1. Send Messages");
            System.out.println("2. Show Total Messages Sent");
            System.out.println("0. Quit");
            System.out.print("Choose an option: ");

            int choice = readInt(scan);

            switch (choice) {
                case 1:
                    messagesCreated = captureMessages(scan, numMessages, messagesCreated);
                    break;
                case 2:
                    System.out.println("Total messages sent: " + Message.returnTotalMessages());
                    break;
                case 0:
                    System.out.println("Goodbye! Thanks for using QuickChat.");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static int captureMessages(Scanner scan, int maxMessages, int messagesCreated) {
        if (messagesCreated >= maxMessages) {
            System.out.println("You have already created all " + maxMessages + " messages.");
            return messagesCreated;
        }

        while (messagesCreated < maxMessages) {
            int currentMessageNumber = messagesCreated + 1;
            System.out.println("\nMessage " + currentMessageNumber + " of " + maxMessages);

            System.out.print("Enter recipient phone number (+27...): ");
            String recipient = scan.nextLine().trim();

            System.out.print("Enter your message (max 250 chars): ");
            String messageText = scan.nextLine().trim();

            Message msg = new Message(currentMessageNumber, recipient, messageText);

            if (!msg.checkMessageLength()) {
                continue;
            }

            System.out.println(msg.checkRecipientCell());

            if (!msg.hasValidRecipient()) {
                continue;
            }

            String sendResult = msg.sentMessage(scan);
            System.out.println(sendResult);

            if (!sendResult.equals("Invalid option.")) {
                System.out.println(msg.printMessage());
                messagesCreated++;
            }

            if (messagesCreated < maxMessages && !askYesNo(scan, "Create another message now? (y/n): ")) {
                break;
            }
        }

        return messagesCreated;
    }

    private static boolean askYesNo(Scanner scan, String prompt) {
        while (true) {
            System.out.print(prompt);
            String answer = scan.nextLine().trim();

            if (answer.equalsIgnoreCase("y") || answer.equalsIgnoreCase("yes")) {
                return true;
            }

            if (answer.equalsIgnoreCase("n") || answer.equalsIgnoreCase("no")) {
                return false;
            }

            System.out.println("Please enter y or n.");
        }
    }

    private static int readPositiveInt(Scanner scan, String prompt) {
        int value;

        do {
            System.out.print(prompt);
            value = readInt(scan);

            if (value <= 0) {
                System.out.println("Please enter a number greater than 0.");
            }
        } while (value <= 0);

        return value;
    }

    private static int readInt(Scanner scan) {
        while (!scan.hasNextInt()) {
            System.out.print("Please enter a number: ");
            scan.nextLine();
        }

        int value = scan.nextInt();
        scan.nextLine();
        return value;
    }
}
