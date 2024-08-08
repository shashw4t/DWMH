package main.java.dwmh.impl;

import main.java.dwmh.model.Guest;
import main.java.dwmh.model.Host;
import main.java.dwmh.model.Reservation;
import main.java.dwmh.repository.GuestRepository;
import main.java.dwmh.repository.ReservationRepository;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReservationRepositoryImpl implements ReservationRepository {
    private final String filePath;
    private final GuestRepository guestRepository;
    private static final Logger logger = Logger.getLogger(ReservationRepositoryImpl.class.getName());

    public ReservationRepositoryImpl(String filePath) {
        this.filePath = filePath;
        this.guestRepository = new GuestRepositoryImpl("data/guests.csv");
    }

    @Override
    public List<Reservation> findByHost(Host host) {
        return readReservationsFromFile(host.getId());
    }

    @Override
    public Reservation save(Reservation reservation) {
        List<Reservation> reservations = findByHost(reservation.getHost());
        reservations.add(reservation);

        return writeToCSV(reservations, reservation.getHost().getId()) ? reservation : null;
    }

    @Override
    public boolean delete(Reservation reservation) {
        List<Reservation> reservations = findByHost(reservation.getHost());
        boolean removed = reservations.removeIf(r -> r.getId() == reservation.getId());

        return removed && writeToCSV(reservations, reservation.getHost().getId());
    }

    @Override
    public Reservation findReservationByIdAndHost(int reservationId, Host host) {
        return findByHost(host).stream()
                .filter(r -> r.getId() == reservationId)
                .findFirst()
                .orElse(null);
    }

    public boolean addReservation(Reservation newReservation) {
        return save(newReservation) != null;
    }

    public List<Reservation> findReservationsByHost(Host host) {
        return findByHost(host);
    }

    public boolean editReservation(Reservation reservationToEdit) {
        List<Reservation> reservations = findByHost(reservationToEdit.getHost());
        for (int i = 0; i < reservations.size(); i++) {
            if (reservations.get(i).getId() == reservationToEdit.getId()) {
                reservations.set(i, reservationToEdit);
                return writeToCSV(reservations, reservationToEdit.getHost().getId());
            }
        }
        return false;
    }

    public boolean cancelReservation(Reservation reservationToCancel) {
        return delete(reservationToCancel);
    }

    private List<Reservation> readReservationsFromFile(String hostId) {
        List<Reservation> reservations = new ArrayList<>();
        File file = new File(filePath + hostId + ".csv");

        if (!file.exists()) {
            logger.log(Level.WARNING, "File not found for host ID: " + hostId);
            return reservations;
        }

        try (Scanner scanner = new Scanner(file)) {
            if (scanner.hasNextLine()) {
                scanner.nextLine(); // Skip header
            }
            while (scanner.hasNextLine()) {
                String[] fields = scanner.nextLine().split(",");
                if (fields.length < 5) {
                    logger.log(Level.WARNING, "Invalid data format in reservations file: " + String.join(",", fields));
                    continue;
                }

                Guest guest = guestRepository.findById(Integer.parseInt(fields[3]));
                if (guest != null) {
                    Reservation reservation = new Reservation(
                            Integer.parseInt(fields[0]), // id
                            LocalDate.parse(fields[1]), // start date
                            LocalDate.parse(fields[2]), // end date
                            new BigDecimal(fields[4]),  // total
                            guest, // Guest object
                            new Host(hostId, null, null, null, null, null)  // Host object with ID
                    );
                    reservations.add(reservation);
                } else {
                    logger.log(Level.WARNING, "Guest not found for ID: " + fields[3]);
                }
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error reading reservations for host ID: " + hostId, e);
        }
        return reservations;
    }

    private boolean writeToCSV(List<Reservation> reservations, String hostId) {
        File file = new File(filePath + hostId + ".csv");
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            writer.println("id,start_date,end_date,guest_id,total");
            for (Reservation reservation : reservations) {
                writer.printf("%d,%s,%s,%d,%s%n",
                        reservation.getId(),
                        reservation.getStartDate(),
                        reservation.getEndDate(),
                        reservation.getGuest().getId(),
                        reservation.getTotal()
                );
            }
            return true;
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to write reservations to file for host ID: " + hostId, e);
        }
        return false;
    }
}
