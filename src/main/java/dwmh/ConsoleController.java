package main.java.dwmh;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class ConsoleController {

    private final Scanner scanner;
    private final PrintStream out;

    // Default constructor
    public ConsoleController() {
        this(System.in, System.out);
    }

    // Parameterized constructor for testing or custom I/O streams
    public ConsoleController(InputStream in, PrintStream out) {
        this.scanner = new Scanner(in);
        this.out = out;
    }

    public void run() {
        while (true) {
            displayMainMenu();
            String input = scanner.nextLine();
            switch (input) {
                case "0":
                    out.println("Exiting...");
                    return;
                case "1":
                    viewReservationsForHost();
                    break;
                case "2":
                    makeReservation();
                    break;
                case "3":
                    editReservation();
                    break;
                case "4":
                    cancelReservation();
                    break;
                default:
                    out.println("Invalid option. Please select a number between 0 and 4.");
            }
        }
    }

    private void displayMainMenu() {
        out.println("Main Menu");
        out.println("=========");
        out.println("0. Exit");
        out.println("1. View Reservations for Host");
        out.println("2. Make a Reservation");
        out.println("3. Edit a Reservation");
        out.println("4. Cancel a Reservation");
        out.print("Select [0-4]: ");
    }

    // Stub methods for now, you can implement these as per your logic
    private void viewReservationsForHost() {
        out.println("Enter Host Email: ");
        String hostEmail = scanner.nextLine();
        // Logic to view reservations
        out.println("Displaying reservations for host: " + hostEmail);
    }

    private void makeReservation() {
        out.println("Make Reservation");
        // Logic to make a reservation
    }

    private void editReservation() {
        out.println("Edit Reservation");
        // Logic to edit a reservation
    }

    private void cancelReservation() {
        out.println("Cancel Reservation");
        // Logic to cancel a reservation
    }
}
