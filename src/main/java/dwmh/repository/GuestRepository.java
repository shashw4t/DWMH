package main.java.dwmh.repository;

import main.java.dwmh.model.Guest;

import java.util.List;

public interface GuestRepository {
    Guest findByEmail(String email);

    List<Guest> findAll();

    Guest findById(int guestId);
}
