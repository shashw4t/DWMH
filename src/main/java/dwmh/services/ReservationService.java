package main.java.dwmh.services;

import main.java.dwmh.model.Guest;
import main.java.dwmh.model.Host;
import main.java.dwmh.model.Reservation;

import java.time.LocalDate;
import java.util.List;

public interface ReservationService {
    List<Reservation> findReservationsByHost(Host host);

    Reservation makeReservation(Guest guest, Host host, LocalDate startDate, LocalDate endDate);

    Reservation editReservation(Reservation reservation, LocalDate newStartDate, LocalDate newEndDate);

    boolean cancelReservation(Reservation reservation);

    Host findHostByEmail(String email);

    Guest findGuestByEmail(String email);

    Reservation findReservationByIdAndHost(Host host, int reservationId);

    Reservation createReservation(Guest guest, Host host, LocalDate of, LocalDate of1);
}

