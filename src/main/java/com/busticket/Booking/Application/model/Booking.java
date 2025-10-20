package com.busticket.Booking.Application.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many bookings belong to one Bus
    @ManyToOne
    @JoinColumn(name = "bus_id")
    private Bus bus;

    // Many bookings belong to one User
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private int seatsBooked;
    private LocalDateTime bookingDate;
    private String status;
    private String userEmail; // optional: store email separately

    // ===== Constructors =====
    public Booking() {
    }

    public Booking(Long id, Bus bus, User user, int seatsBooked, LocalDateTime bookingDate,
                   String status, String userEmail) {
        this.id = id;
        this.bus = bus;
        this.user = user;
        this.seatsBooked = seatsBooked;
        this.bookingDate = bookingDate;
        this.status = status;
        this.userEmail = userEmail;
    }

    // ===== Getters and Setters =====
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getSeatsBooked() {
        return seatsBooked;
    }

    public void setSeatsBooked(int seatsBooked) {
        this.seatsBooked = seatsBooked;
    }

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    // ===== Helper Methods =====

    // Return the passenger name (from the user entity)
    public String getPassengerName() {
        return user != null ? user.getName() : null;
    }

    // Return the booking time (alias for bookingDate)
    public LocalDateTime getBookingTime() {
        return bookingDate;
    }

    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingDate = bookingTime;
    }

    public void setPassengerName(String passengerName) {
        // This method doesn't directly set passenger name as it's derived from user
        // In a real scenario, you might want to throw an exception or handle differently
        // For now, we'll leave it empty as the passenger name comes from the user relationship
    }
}
