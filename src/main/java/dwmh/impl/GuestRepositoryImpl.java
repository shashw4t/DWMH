package main.java.dwmh.impl;

import main.java.dwmh.model.Guest;
import main.java.dwmh.repository.GuestRepository;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GuestRepositoryImpl implements GuestRepository {
    private final String filePath;

    public GuestRepositoryImpl(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public Guest findByEmail(String email) {
        File file = new File(filePath);
        try (Scanner scanner = new Scanner(file)) {
            scanner.nextLine(); // Skip header
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] fields = line.split(","); // Assuming CSV is comma-delimited
                if (fields[3].equalsIgnoreCase(email)) { // Assuming email is at index 3
                    return new Guest(
                            Integer.parseInt(fields[0]), // id
                            fields[1],                   // first_name
                            fields[2],                   // last_name
                            fields[3]                   // email
                            // phone
                            // state
                    );
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Guest findById(int guestId) {
        File file = new File(filePath);
        try (Scanner scanner = new Scanner(file)) {
            scanner.nextLine(); // Skip header
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] fields = line.split(","); // Assuming CSV is comma-delimited
                if (Integer.parseInt(fields[0]) == guestId) { // Assuming id is at index 0
                    return new Guest(
                            Integer.parseInt(fields[0]), // id
                            fields[1],                   // first_name
                            fields[2],                   // last_name
                            fields[3]                   // email
                            // phone
                            // state
                    );
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<Guest> findAll() {
        List<Guest> guests = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filePath))) {
            scanner.nextLine(); // Skip header
            while (scanner.hasNextLine()) {
                String[] fields = scanner.nextLine().split(",");
                Guest guest = new Guest(
                        Integer.parseInt(fields[0]),
                        fields[1],
                        fields[2],
                        fields[3]
                );
                guests.add(guest);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return guests;
    }
}
