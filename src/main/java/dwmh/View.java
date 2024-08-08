package main.java.dwmh;

import main.java.dwmh.model.Reservation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class View {
    private final Scanner scanner = new Scanner(System.in);
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public int displayMainMenu() {
        System.out.println("Main Menu");
        System.out.println("=========");
        System.out.println("0. Exit");
        System.out.println("1. View Reservations for Host");
        System.out.println("2. Make a Reservation");
        System.out.println("3. Edit a Reservation");
        System.out.println("4. Cancel a Reservation");
        System.out.print("Select [0-4]: ");
        return readInt(0, 4);
    }

    public String getHostEmail() {
        System.out.print("Host Email: ");
        return scanner.nextLine().trim();
    }

    public String getGuestEmail() {
        System.out.print("Guest Email: ");
        return scanner.nextLine().trim();
    }

    public int promptForReservationId() {
        System.out.print("Reservation ID: ");
        return readInt();
    }

    public LocalDate promptForStartDate() {
        return promptForDate("Enter start date (MM/dd/yyyy): ");
    }

    public LocalDate promptForEndDate() {
        return promptForDate("Enter end date (MM/dd/yyyy): ");
    }

    private LocalDate promptForDate(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return LocalDate.parse(input, formatter);
            } catch (DateTimeParseException ex) {
                System.out.println("Invalid date format. Please use MM/dd/yyyy.");
            }
        }
    }

    public void displayReservationDetails(Reservation reservation) {
        System.out.println("Reservation Details:");
        System.out.println("ID: " + reservation.getId());
        System.out.println("Start Date: " + reservation.getStartDate().format(formatter));
        System.out.println("End Date: " + reservation.getEndDate().format(formatter));
        System.out.println("Total: " + reservation.getTotal());
        System.out.println("Guest: " + reservation.getGuest().getFirstName() + " " + reservation.getGuest().getLastName());
        System.out.println("Host: " + reservation.getHost().getLastName() + " (" + reservation.getHost().getEmail() + ")");
        System.out.println("---------------------------");
    }

    private int readInt() {
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. Please enter a number: ");
            scanner.next();
        }
        int value = scanner.nextInt();
        scanner.nextLine(); // consume the remaining newline
        return value;
    }

    private int readInt(int min, int max) {
        int value;
        do {
            value = readInt();
            if (value < min || value > max) {
                System.out.print("Please enter a number between " + min + " and " + max + ": ");
            }
        } while (value < min || value > max);
        return value;
    }
}
