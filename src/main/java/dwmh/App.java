package main.java.dwmh;

import main.java.dwmh.impl.GuestRepositoryImpl;
import main.java.dwmh.impl.HostRepositoryImpl;
import main.java.dwmh.impl.ReservationRepositoryImpl;
import main.java.dwmh.model.Guest;
import main.java.dwmh.model.Host;
import main.java.dwmh.model.Reservation;
import main.java.dwmh.services.ReservationService;
import main.java.dwmh.services.ReservationServiceImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import static java.lang.System.in;

public class App {

    public static final ReservationService reservationService;

    static {
        reservationService = new ReservationServiceImpl(
                new ReservationRepositoryImpl("data/reservations/"),
                new GuestRepositoryImpl("data/guests.csv"),
                new HostRepositoryImpl("data/hosts.csv")
        );
    }

    private final Scanner scanner;

    public App() {
        this.scanner = new Scanner(in);
    }

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }
    public void run() {
        while (true) {
            displayMainMenu();
            String input = scanner.nextLine();
            switch (input) {
                case "0":
                    System.out.println("Exiting...");
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
                    System.out.println("Invalid option. Please select a number between 0 and 4.");
            }
        }
    }

    public void makeReservation() {
        String guestEmail = promptForGuestEmail();
        Guest guest = reservationService.findGuestByEmail(guestEmail);

        if (guest == null) {
            System.out.println("Guest not found.");
            return;
        }

        String hostEmail = promptForHostEmail();
        Host host = reservationService.findHostByEmail(hostEmail);

        if (host == null) {
            System.out.println("Host not found.");
            return;
        }

        LocalDate startDate = promptForStartDate();
        if (startDate == null) return;

        LocalDate endDate = promptForEndDate();
        if (endDate == null) return;

        try {
            Reservation reservation = createReservation(guest, host, startDate, endDate);
            System.out.println("Reservation created successfully.");
            System.out.println(reservation);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private String promptForGuestEmail() {
        System.out.print("Enter Guest Email: ");
        return scanner.nextLine();
    }

    private String promptForHostEmail() {
        System.out.print("Enter Host Email: ");
        return scanner.nextLine();
    }

    private LocalDate promptForStartDate() {
        System.out.print("Enter Start Date (yyyy-MM-dd): ");
        return promptForDate();
    }

    private LocalDate promptForEndDate() {
        System.out.print("Enter End Date (yyyy-MM-dd): ");
        return promptForDate();
    }

    private Reservation createReservation(Guest guest, Host host, LocalDate startDate, LocalDate endDate) {
        return reservationService.makeReservation(guest, host, startDate, endDate);
    }


    private void displayMainMenu() {
        System.out.println("Main Menu");
        System.out.println("=========");
        System.out.println("0. Exit");
        System.out.println("1. View Reservations for Host");
        System.out.println("2. Make a Reservation");
        System.out.println("3. Edit a Reservation");
        System.out.println("4. Cancel a Reservation");
        System.out.print("Select [0-4]: ");
    }

    private void viewReservationsForHost() {
        System.out.print("Enter Host Email: ");
        String hostEmail = scanner.nextLine();
        Host host = reservationService.findHostByEmail(hostEmail);

        if (host == null) {
            System.out.println("Host not found.");
            return;
        }

        List<Reservation> reservations = reservationService.findReservationsByHost(host);
        if (reservations.isEmpty()) {
            System.out.println("No reservations found for this host.");
        } else {
            for (Reservation reservation : reservations) {
                System.out.println(reservation);
            }
        }
    }



    private void editReservation() {
        System.out.print("Enter Host Email: ");
        String hostEmail = scanner.nextLine();
        Host host = reservationService.findHostByEmail(hostEmail);

        if (host == null) {
            System.out.println("Host not found.");
            return;
        }

        System.out.print("Enter Reservation ID: ");
        int reservationId = Integer.parseInt(scanner.nextLine());

        Reservation reservation = reservationService.findReservationByIdAndHost(host, reservationId);
        if (reservation == null) {
            System.out.println("Reservation not found.");
            return;
        }

        System.out.print("Enter New Start Date (yyyy-MM-dd): ");
        LocalDate newStartDate = promptForDate();
        if (newStartDate == null) return;

        System.out.print("Enter New End Date (yyyy-MM-dd): ");
        LocalDate newEndDate = promptForDate();
        if (newEndDate == null) return;

        try {
            Reservation updatedReservation = reservationService.editReservation(reservation, newStartDate, newEndDate);
            System.out.println("Reservation updated successfully.");
            System.out.println(updatedReservation);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void cancelReservation() {
        System.out.print("Enter Host Email: ");
        String hostEmail = scanner.nextLine();
        Host host = reservationService.findHostByEmail(hostEmail);

        if (host == null) {
            System.out.println("Host not found.");
            return;
        }

        System.out.print("Enter Reservation ID: ");
        int reservationId = Integer.parseInt(scanner.nextLine());

        Reservation reservation = reservationService.findReservationByIdAndHost(host, reservationId);
        if (reservation == null) {
            System.out.println("Reservation not found.");
            return;
        }

        boolean success = reservationService.cancelReservation(reservation);
        if (success) {
            System.out.println("Reservation canceled successfully.");
        } else {
            System.out.println("Failed to cancel the reservation.");
        }
    }

    private LocalDate promptForDate() {
        String input = scanner.nextLine();
        try {
            return LocalDate.parse(input, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please enter in yyyy-MM-dd format.");
            return null;
        }
    }
}



