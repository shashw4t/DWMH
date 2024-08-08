package main.tests.dwmh;

import main.java.dwmh.impl.GuestRepositoryImpl;
import main.java.dwmh.impl.HostRepositoryImpl;
import main.java.dwmh.impl.ReservationRepositoryImpl;
import main.java.dwmh.model.Guest;
import main.java.dwmh.model.Host;
import main.java.dwmh.model.Reservation;
import main.java.dwmh.repository.GuestRepository;
import main.java.dwmh.repository.HostRepository;
import main.java.dwmh.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ReservationRepositoryImplTest {

    private ReservationRepository repository;
    private GuestRepository guestRepository;
    private HostRepository hostRepository;

    @BeforeEach
    void setUp() {
        guestRepository = new GuestRepositoryImpl("data/guests.csv");
        hostRepository = new HostRepositoryImpl("data/hosts.csv");
        repository = new ReservationRepositoryImpl("data/reservations_test/");
    }

    @Test
    void shouldAddReservation() {
        Host host = hostRepository.findByEmail("jcroneya@noaa.gov");
        Guest guest = guestRepository.findByEmail("wisaqb@blogger.com");

        // You should use the appropriate constructor for your Reservation class.
        Reservation newReservation = getReservation(guest, host);

        @SuppressWarnings("unused")
        boolean success;
        assertTrue(success = repository.addReservation(newReservation));

        List<Reservation> reservations = repository.findReservationsByHost(host);
        assertFalse(reservations.contains(newReservation), "New reservation should be present in the list");
    }

    private static Reservation getReservation(Guest guest, Host host) {
        return new Reservation
                (10,
                        LocalDate.of(2024, 12, 1),
                        LocalDate.of(2024, 12, 5),
                        new BigDecimal("500"),
                        guest,
                        host);
    }

    @Test
    void shouldFindReservationsByHost() {
        Host host = hostRepository.findByEmail("jcroneya@noaa.gov");

        List<Reservation> reservations = repository.findReservationsByHost(host);

        assertFalse(reservations.isEmpty(), "Reservations list should not be empty");
    }

    @Test
    void shouldEditReservation() {
        Host host = hostRepository.findByEmail("jcroneya@noaa.gov");
        Reservation reservation = repository.findReservationByIdAndHost(10, host);

        LocalDate newStartDate = LocalDate.of(2024, 12, 2);
        LocalDate newEndDate = LocalDate.of(2022, 2, 23);
        reservation.setStartDate(newStartDate);
        reservation.setEndDate(newEndDate);

        boolean success = repository.editReservation(reservation);
        assertTrue(success, "Reservation should be updated successfully");
    }

    @Test
    void shouldCancelReservation() {
        Host host = hostRepository.findByEmail("jcroneya@noaa.gov");
        Reservation reservation = repository.findReservationByIdAndHost(10, host);

        boolean success = repository.cancelReservation(reservation);
        assertTrue(success, "Reservation should be canceled successfully");

        List<Reservation> reservations = repository.findReservationsByHost(host);
        assertFalse(reservations.contains(reservation), "Canceled reservation should no longer be in the list");
    }
}
