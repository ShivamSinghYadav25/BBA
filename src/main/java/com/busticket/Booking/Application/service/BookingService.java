package com.busticket.Booking.Application.service;

import com.busticket.Booking.Application.model.Booking;
import com.busticket.Booking.Application.model.Bus;
import com.busticket.Booking.Application.model.User;
import com.busticket.Booking.Application.repository.BookingRepository;
import com.busticket.Booking.Application.repository.BusRepository;
import com.busticket.Booking.Application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private UserRepository userRepository;

    // Get all bookings
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    // Get booking by ID
    public Optional<Booking> getBookingById(Long id) {
        return bookingRepository.findById(id);
    }

    // Create booking
    public Booking createBooking(Booking booking) {
        // Ensure Bus exists
        if (booking.getBus() != null) {
            Optional<Bus> busOptional = busRepository.findById(booking.getBus().getId());
            if (busOptional.isEmpty()) return null;
            booking.setBus(busOptional.get());
        }

        // Ensure User exists
        if (booking.getUser() != null) {
            Optional<User> userOptional = userRepository.findById(booking.getUser().getId());
            if (userOptional.isEmpty()) return null;
            booking.setUser(userOptional.get());
            booking.setUserEmail(userOptional.get().getEmail()); // store email
        }

        return bookingRepository.save(booking);
    }

    // Update booking
    public Booking updateBooking(Long id, Booking bookingDetails) {
        Optional<Booking> optionalBooking = bookingRepository.findById(id);
        if (optionalBooking.isPresent()) {
            Booking booking = optionalBooking.get();
            booking.setSeatsBooked(bookingDetails.getSeatsBooked());
            booking.setBookingDate(bookingDetails.getBookingDate());
            booking.setStatus(bookingDetails.getStatus());

            if (bookingDetails.getBus() != null) {
                Optional<Bus> busOptional = busRepository.findById(bookingDetails.getBus().getId());
                if (busOptional.isPresent()) {
                    booking.setBus(busOptional.get());
                }
            }

            if (bookingDetails.getUser() != null) {
                Optional<User> userOptional = userRepository.findById(bookingDetails.getUser().getId());
                if (userOptional.isPresent()) {
                    booking.setUser(userOptional.get());
                    booking.setUserEmail(userOptional.get().getEmail());
                }
            }

            return bookingRepository.save(booking);
        } else {
            return null;
        }
    }

    // Delete booking
    public boolean deleteBooking(Long id) {
        if (bookingRepository.existsById(id)) {
            bookingRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
