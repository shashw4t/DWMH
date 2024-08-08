package main.java.dwmh.repository;

import main.java.dwmh.model.Host;
import main.java.dwmh.model.Reservation;

import java.util.List;

public interface ReservationRepository {
    List<Reservation> findByHost(Host host);

    Reservation save(Reservation reservation);

    boolean delete(Reservation reservation);

    Reservation findReservationByIdAndHost(int reservationId, Host host);

    boolean addReservation(Reservation newReservation);

    List<Reservation> findReservationsByHost(Host host);

    boolean editReservation(Reservation reservation);

    boolean cancelReservation(Reservation reservation);
}
