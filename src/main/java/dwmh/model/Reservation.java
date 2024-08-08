package main.java.dwmh.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Reservation {
    private int id;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal total;
    private Guest guest;
    private Host host;

    public Reservation(int id, LocalDate startDate, LocalDate endDate, BigDecimal total, Guest guest, Host host) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.total = total;
        this.guest = guest;
        this.host = host;
    }
    @Override
    public String toString() {
        return "Reservation ID: " + id +
                ", Start Date: " + startDate +
                ", End Date: " + endDate +
                ", Guest: " + guest.getEmail() +
                ", Host: " + host.getEmail() +
                ", Total: " + total;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public Guest getGuest() {
        return guest;
    }

    public Host getHost() {
        return host;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    public boolean isOverlapping(Reservation reservation) {
        // Check if the start or end date of this reservation is within the other reservation's date range
        return (this.startDate.isBefore(reservation.getEndDate()) || this.startDate.isEqual(reservation.getEndDate())) &&
                (this.endDate.isAfter(reservation.getStartDate()) || this.endDate.isEqual(reservation.getStartDate()));
    }

    public boolean isOverlapping(LocalDate startDate, LocalDate endDate) {
        return false;
    }
}
