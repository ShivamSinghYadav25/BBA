package com.busticket.Booking.Application.controller;

import com.busticket.Booking.Application.model.Booking;
import com.busticket.Booking.Application.model.Bus;
import com.busticket.Booking.Application.repository.BookingRepository;
import com.busticket.Booking.Application.repository.BusRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BusRepository busRepository;

    // Get all bookings
    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    // Get a booking by ID
    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
        Optional<Booking> booking = bookingRepository.findById(id);
        return booking.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a booking
    @PostMapping
    public ResponseEntity<?> createBooking(@Valid @RequestBody Booking booking, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Validation errors: " + bindingResult.getAllErrors());
        }

        try {
            // Check if the bus exists
            if (booking.getBus() != null && booking.getBus().getId() != null) {
                Optional<Bus> busOptional = busRepository.findById(booking.getBus().getId());
                if (busOptional.isEmpty()) {
                    return ResponseEntity.badRequest().body("Bus with ID " + booking.getBus().getId() + " not found");
                }
                booking.setBus(busOptional.get());
            }
            Booking savedBooking = bookingRepository.save(booking);
            return ResponseEntity.ok(savedBooking);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating booking: " + e.getMessage());
        }
    }

    // Update a booking
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBooking(@PathVariable Long id, @Valid @RequestBody Booking bookingDetails, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Validation errors: " + bindingResult.getAllErrors());
        }

        try {
            Optional<Booking> optionalBooking = bookingRepository.findById(id);
            if (optionalBooking.isPresent()) {
                Booking booking = optionalBooking.get();

                // Update booking details
                booking.setSeatsBooked(bookingDetails.getSeatsBooked());
                booking.setBookingDate(bookingDetails.getBookingDate() != null ? bookingDetails.getBookingDate() : bookingDetails.getBookingTime());
                booking.setStatus(bookingDetails.getStatus());
                booking.setUserEmail(bookingDetails.getUserEmail());

                // Update bus if provided
                if (bookingDetails.getBus() != null && bookingDetails.getBus().getId() != null) {
                    Optional<Bus> busOptional = busRepository.findById(bookingDetails.getBus().getId());
                    if (busOptional.isEmpty()) {
                        return ResponseEntity.badRequest().body("Bus with ID " + bookingDetails.getBus().getId() + " not found");
                    }
                    booking.setBus(busOptional.get());
                }

                return ResponseEntity.ok(bookingRepository.save(booking));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating booking: " + e.getMessage());
        }
    }

    // Delete a booking
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        if (bookingRepository.existsById(id)) {
            bookingRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
