package main.java.dwmh.repository;

import main.java.dwmh.model.Host;

import java.util.List;

public interface HostRepository {
    Host findByEmail(String email);

    List<Host> findAll();
}
