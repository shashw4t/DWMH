package main.java.dwmh.services;

import main.java.dwmh.model.Guest;
import main.java.dwmh.model.Host;
import main.java.dwmh.model.Reservation;
import main.java.dwmh.repository.GuestRepository;
import main.java.dwmh.repository.HostRepository;
import main.java.dwmh.repository.ReservationRepository;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final GuestRepository guestRepository;
    private final HostRepository hostRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository,
                                  GuestRepository guestRepository,
                                  HostRepository hostRepository) {
        this.reservationRepository = reservationRepository;
        this.guestRepository = guestRepository;
        this.hostRepository = hostRepository;
    }

    @Override
    public List<Reservation> findReservationsByHost(Host host) {
        if (host == null) {
            throw new IllegalArgumentException("Host must not be null.");
        }
        return reservationRepository.findByHost(host);
    }

    @Override
    public Reservation makeReservation(Guest guest, Host host, LocalDate startDate, LocalDate endDate) {
        validateReservationInputs(guest, host, startDate, endDate);
        checkForOverlappingReservations(host, startDate, endDate);

        BigDecimal total = calculateTotalCost(host, startDate, endDate);
        Reservation newReservation = new Reservation(0, startDate, endDate, total, guest, host);
        return reservationRepository.save(newReservation);
    }

    private void checkForOverlappingReservations(Host host, LocalDate startDate, LocalDate endDate) {
        List<Reservation> existingReservations = reservationRepository.findByHost(host);
        for (Reservation reservation : existingReservations) {
            if (reservation.isOverlapping(startDate, endDate)) {
                throw new IllegalArgumentException("Reservation dates overlap with an existing reservation.");
            }
        }
    }

    @Override
    public Reservation editReservation(Reservation reservation, LocalDate newStartDate, LocalDate newEndDate) {
        validateReservationInputs(reservation.getGuest(), reservation.getHost(), newStartDate, newEndDate);

        List<Reservation> existingReservations = reservationRepository.findByHost(reservation.getHost())
                .stream()
                .filter(r -> r.getId() != reservation.getId()) // Exclude the current reservation
                .toList();

        for (Reservation existingReservation : existingReservations) {
            if (existingReservation.isOverlapping(newStartDate, newEndDate)) {
                throw new IllegalArgumentException("Reservation dates overlap with an existing reservation.");
            }
        }

        reservation.setStartDate(newStartDate);
        reservation.setEndDate(newEndDate);
        reservation.setTotal(calculateTotalCost(reservation.getHost(), newStartDate, newEndDate));
        return reservationRepository.save(reservation);
    }

    @Override
    public boolean cancelReservation(Reservation reservation) {
        if (reservation == null) {
            throw new IllegalArgumentException("Reservation must not be null.");
        }
        if (reservation.getStartDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Cannot cancel a past reservation.");
        }
        return reservationRepository.delete(reservation);
    }

    private void validateReservationInputs(Guest guest, Host host, LocalDate startDate, LocalDate endDate) {
        if (guest == null) {
            throw new IllegalArgumentException("Guest must not be null.");
        }
        if (host == null) {
            throw new IllegalArgumentException("Host must not be null.");
        }
        if (startDate == null) {
            throw new IllegalArgumentException("Start date must not be null.");
        }
        if (endDate == null) {
            throw new IllegalArgumentException("End date must not be null.");
        }
        if (!startDate.isBefore(endDate)) {
            throw new IllegalArgumentException("Start date must be before end date.");
        }
        if (startDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Start date must be in the future.");
        }
    }

    private BigDecimal calculateTotalCost(Host host, LocalDate startDate, LocalDate endDate) {
        BigDecimal total = BigDecimal.ZERO;
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            if (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
                total = total.add(host.getWeekendRate());
            } else {
                total = total.add(host.getStandardRate());
            }
        }
        return total;
    }

    @Override
    public Host findHostByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email must not be null or empty.");
        }
        return hostRepository.findByEmail(email);
    }

    @Override
    public Guest findGuestByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email must not be null or empty.");
        }
        return guestRepository.findByEmail(email);
    }

    @Override
    public Reservation findReservationByIdAndHost(Host host, int reservationId) {
        if (host == null) {
            throw new IllegalArgumentException("Host must not be null.");
        }
        if (reservationId <= 0) {
            throw new IllegalArgumentException("Reservation ID must be positive.");
        }
        List<Reservation> reservations = reservationRepository.findByHost(host);
        return reservations.stream()
                .filter(r -> r.getId() == reservationId)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Reservation createReservation(Guest guest, Host host, LocalDate startDate, LocalDate endDate) {
        validateReservationInputs(guest, host, startDate, endDate);
        checkForOverlappingReservations(host, startDate, endDate);

        BigDecimal total = calculateTotalCost(host, startDate, endDate);
        Reservation newReservation = new Reservation(0, startDate, endDate, total, guest, host);
        return reservationRepository.save(newReservation);
    }
}

