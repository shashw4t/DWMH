package main.java.dwmh.impl;

import main.java.dwmh.model.Host;
import main.java.dwmh.repository.HostRepository;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HostRepositoryImpl implements HostRepository {
    private final String filePath;

    public HostRepositoryImpl(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public Host findByEmail(String email) {
        File file = new File(filePath);
        try (Scanner scanner = new Scanner(file)) {
            scanner.nextLine(); // Skip header
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] fields = line.split(","); // Assuming CSV is comma-delimited
                if (fields[2].equalsIgnoreCase(email)) { // Assuming email is at index 2
                    return new Host(
                            fields[0],    // id (UUID)
                            fields[1],    // last_name
                            fields[2],    // email
                            fields[3],    // phone
                            // address
                            // city
                            // state
                            // postal_code
                            new BigDecimal(fields[8]), // standard_rate
                            new BigDecimal(fields[9])  // weekend_rate
                    );
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Host> findAll() {
        List<Host> hosts = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filePath))) {
            scanner.nextLine(); // Skip header
            while (scanner.hasNextLine()) {
                String[] fields = scanner.nextLine().split(",");
                Host host = new Host(
                        fields[0],    // id (UUID)
                        fields[1],    // last_name
                        fields[2],    // email
                        fields[3],    // phone
                        // address
                        // city
                        // state
                        // postal_code
                        new BigDecimal(fields[8]), // standard_rate
                        new BigDecimal(fields[9])  // weekend_rate
                );
                hosts.add(host);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return hosts;
    }
}
