package main.java.dwmh.model;

import java.math.BigDecimal;

public class Host {
    private String id;
    private final String lastName;
    private String email;
    private final String phone;
    private final BigDecimal standardRate;
    private final BigDecimal weekendRate;

    public Host(String id, String lastName, String email, String phone,
                BigDecimal standardRate, BigDecimal weekendRate) {
        this.id = id;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.standardRate = standardRate;
        this.weekendRate = weekendRate;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public BigDecimal getStandardRate() {
        return standardRate;
    }
    public BigDecimal getWeekendRate() {
        return weekendRate;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setEmail(String email) {
        this.email = email;
    }
//
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }
//
//    public String getAddress() {
//        return address;
//    }
//
//    public String getCity() {
//        return city;
//    }
//
//    public String getState() {
//        return state;
//    }
//
//    public String getPostalCode() {
//        return postalCode;
//    }
//
//
//    public void setPhone(String phone) {
//        this.phone = phone;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }
//
//    public void setCity(String city) {
//        this.city = city;
//    }
//
//    public void setState(String state) {
//        this.state = state;
//    }
//
//    public void setPostalCode(String postalCode) {
//        this.postalCode = postalCode;
//    }
//
//    public void setStandardRate(BigDecimal standardRate) {
//        this.standardRate = standardRate;
//    }
//
//    public void setWeekendRate(BigDecimal weekendRate) {
//        this.weekendRate = weekendRate;
//    }
//
//    public BigDecimal calculateRate(java.time.LocalDate date) {
//        // Implement logic to calculate rate based on date (weekday/weekend)
//        return BigDecimal.ZERO;
//    }
}
