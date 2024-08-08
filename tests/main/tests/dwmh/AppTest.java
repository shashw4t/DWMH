package main.tests.dwmh;

import main.java.dwmh.App;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AppTest {

    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUpStreams() {
        // This method will run before each test to set up streams
    }

    @AfterEach
    void restoreStreams() {
        // Restore original System.in and System.out after each test
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

//    @Test
//    void runTest_simulation() {
//        // Prepare a simulation
//        InputStream inputStream = new ByteArrayInputStream("0\n".getBytes());
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        PrintStream printStream = new PrintStream(outputStream);
//
//        // Run the test
//        App.runTest(inputStream, printStream);
//
//        // Check if the expected output file is created
//        File expectedOutputFile = new File("expectedOutput.txt");
//        assertFalse(expectedOutputFile.exists(), "Expected output file was not created.");
//
//        // Print out the contents of the file
//        try {
//            List<String> lines = Files.readAllLines(expectedOutputFile.toPath());
//            assertFalse(lines.isEmpty(), "Expected output file is empty.");
//
//            System.out.println("Contents of expectedOutput.txt:");
//            for (String line : lines) {
//                System.out.println(line);
//            }
//        } catch (IOException e) {
//            fail("Failed to read the expected output file.");
//        }
//    }




    @Test
    void main_handlesInvalidOption() {
        // Simulate invalid option input and capture output
        String simulatedInput = "5\n0\n"; // Simulate selecting an invalid option and then exiting
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        // Redirect System.in and System.out
        System.setIn(inputStream);
        System.setOut(printStream);

        // Run the application
        App app = new App();
        app.run();

        // Normalize the output by removing excess whitespace
        String expectedOutput = "Invalid option. Please select a number between 0 and 4.";
        String actualOutput = outputStream.toString().replaceAll("\\s+", " ").trim();

        // Check if the expected output is a substring of the actual output
        assertTrue(actualOutput.contains(expectedOutput), "Expected invalid option message to be displayed.");
    }

    @Test
    void main_displaysMenu() {
        // Simulate input and capture output
        String simulatedInput = "0\n"; // Simulate selecting exit immediately
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        // Redirect System.in and System.out
        System.setIn(inputStream);
        System.setOut(printStream);

        // Run the application
        App app = new App();
        app.run();

        // Normalize the output by removing excess whitespace
        String expectedOutput = "Main Menu";
        String actualOutput = outputStream.toString().replaceAll("\\s+", " ").trim();

        // Check if the expected output is a substring of the actual output
        assertTrue(actualOutput.contains(expectedOutput), "Expected main menu output to be displayed.");
    }

    @Test
    void main_handlesExitOption() {
        // Simulates user choosing to exit the program
        String simulatedInput = "0\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        assertDoesNotThrow(() -> {
            String[] args = {};
            App.main(args);
        });

        String expectedOutput = "0. Exit";
        String actualOutput = outContent.toString();
        assertTrue(actualOutput.contains(expectedOutput), "Expected exit option to be displayed.");
    }
}
