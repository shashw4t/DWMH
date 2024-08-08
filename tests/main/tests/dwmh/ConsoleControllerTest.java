package main.tests.dwmh;

import main.java.dwmh.ConsoleController;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class ConsoleControllerTest {

    @Test
    void testMainMenuDisplay() {
        String simulatedInput = "0\n"; // Simulate selecting exit immediately
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        // Use parameterized constructor to inject custom input/output streams
        ConsoleController controller = new ConsoleController(inputStream, printStream);
        controller.run();

        String expectedOutput = "Main Menu";
        String actualOutput = outputStream.toString().replaceAll("\\s+", " ").trim();

        assertTrue(actualOutput.contains(expectedOutput), "Expected main menu output to be displayed.");
    }

    @Test
    void testViewReservationsForHost_Valid() {
        String simulatedInput = "1\nexisting_host_email@example.com\n0\n";
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        ConsoleController controller = new ConsoleController(inputStream, printStream);
        controller.run();

        String expectedOutput = "Displaying reservations for host:";
        String actualOutput = outputStream.toString().replaceAll("\\s+", " ").trim();

        assertTrue(actualOutput.contains(expectedOutput), "Expected to find reservation information in the output.");
    }

    @Test
    void testMakeReservation_Invalid() {
        String simulatedInput = "2\ninvalid_guest_email@example.com\nvalid_host_email@example.com\n2023-01-01\n2023-01-05\n0\n";
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        ConsoleController controller = new ConsoleController(inputStream, printStream);
        controller.run();

        String expectedOutput = "Make Reservation";
        String actualOutput = outputStream.toString().replaceAll("\\s+", " ").trim();

        assertTrue(actualOutput.contains(expectedOutput), "Expected Make Reservation prompt to be displayed.");
    }

    // Additional tests for Edit Reservation, Cancel Reservation, etc.
}
