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
import main.java.dwmh.services.ReservationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ReservationServiceImplTest {

    private ReservationServiceImpl service;
    private GuestRepository guestRepository;

    @BeforeEach
    void setUp() {
        // Initialize repositories with test data paths
        ReservationRepository reservationRepository = new ReservationRepositoryImpl("data/reservations_test/res.csv");
        guestRepository = new GuestRepositoryImpl("data/guests.csv");
        HostRepository hostRepository = new HostRepositoryImpl("data/hosts.csv");

        // Initialize the service with repositories
        service = new ReservationServiceImpl(reservationRepository, guestRepository, hostRepository);
    }

//    @Test
//    void makeReservation_shouldCreateReservationSuccessfully() {
//        // Given a guest and host
//        Host host = createTestHost("test-host-1");
//        Guest guest = guestRepository.findById(1);
//        assertNotNull(guest, "Guest should be found.");
//        assertNotNull(host, "Host should be created.");
//
//        // When creating a reservation
//        Reservation reservation = service.createReservation(guest, host, LocalDate.now().plusDays(5), LocalDate.now().plusDays(7));
//
//        // Then the reservation should be created successfully
//        assertNotNull(reservation, "Reservation should not be null.");
//        assertTrue(reservation.getId() > 0, "Reservation ID should be positive.");
//        assertEquals(guest, reservation.getGuest(), "Guest should match.");
//        assertEquals(host, reservation.getHost(), "Host should match.");
//        assertEquals(LocalDate.now().plusDays(5), reservation.getStartDate(), "Start date should match.");
//        assertEquals(LocalDate.now().plusDays(7), reservation.getEndDate(), "End date should match.");
//    }

//    @Test
//    void makeReservation_shouldFailForOverlappingDates() {
//        // Given a guest and host with an existing reservation
//        Host host = createTestHost("test-host-2");
//        Guest guest = guestRepository.findById(1);
//        assertNotNull(guest, "Guest should be found.");
//        assertNotNull(host, "Host should be created.");
//
//        // Create an initial reservation
//        service.createReservation(guest, host, LocalDate.now().plusDays(10), LocalDate.now().plusDays(12));
//
//        // When trying to create an overlapping reservation
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
//                service.createReservation(guest, host, LocalDate.now().plusDays(11), LocalDate.now().plusDays(13))
//        );
//
//        // Then an exception should be thrown
//        assertEquals("Reservation dates overlap with an existing reservation.", exception.getMessage());
//    }

    @Test
    void makeReservation_shouldFailForPastDates() {
        // Given a guest and host
        Host host = createTestHost("test-host-3");
        Guest guest = guestRepository.findById(1);
        assertNotNull(guest, "Guest should be found.");
        assertNotNull(host, "Host should be created.");

        // When trying to create a reservation with past dates
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                service.createReservation(guest, host, LocalDate.now().minusDays(5), LocalDate.now().minusDays(2))
        );

        // Then an exception should be thrown
        assertEquals("Start date must be in the future.", exception.getMessage());
    }

    @Test
    void editReservation_shouldUpdateDatesSuccessfully() {
        // Given an existing reservation
        Host host = createTestHost("test-host-4");
        Guest guest = guestRepository.findById(1);
        assertNotNull(guest, "Guest should be found.");
        assertNotNull(host, "Host should be created.");

        Reservation reservation = service.createReservation(guest, host, LocalDate.of(2025, 12, 1), LocalDate.of(2025, 12, 5));

        // When editing the reservation to new valid dates
        Reservation editedReservation = service.editReservation(reservation, LocalDate.now().plusDays(8), LocalDate.now().plusDays(10));

        // Then the reservation dates should be updated successfully
        assertEquals(LocalDate.now().plusDays(8), editedReservation.getStartDate(), "Start date should be updated.");
        assertEquals(LocalDate.now().plusDays(10), editedReservation.getEndDate(), "End date should be updated.");
    }

//    @Test
//    void editReservation_shouldFailForOverlappingDates() {
//        // Given an existing reservation
//        Host host = createTestHost("test-host-5");
//        Guest guest = guestRepository.findById(1);
//        assertNotNull(guest, "Guest should be found.");
//        assertNotNull(host, "Host should be created.");
//
//        // Create an initial reservation
//        service.createReservation(guest, host, LocalDate.now().plusDays(10), LocalDate.now().plusDays(12));
//
//        // When trying to edit to overlapping dates
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
//                service.editReservation(service.createReservation(guest, host, LocalDate.now().plusDays(10), LocalDate.now().plusDays(12)),
//                        LocalDate.now().plusDays(11), LocalDate.now().plusDays(13))
//        );
//
//        // Then an exception should be thrown
//        assertEquals("Reservation dates overlap with an existing reservation.", exception.getMessage());
//    }



    private Host createTestHost(String id) {
        return new Host(id, "Test Host", "testhost@example.com", "1234567890",
                new BigDecimal("150.00"), new BigDecimal("200.00"));
    }
}
