package com.busticket.Booking.Application.controller;

import com.busticket.Booking.Application.model.Booking;
import com.busticket.Booking.Application.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/bookings")
public class BookingViewController {

    @Autowired
    private BookingRepository bookingRepository;

    // List all bookings (Thymeleaf page)
    @GetMapping
    public String listBookings(Model model) {
        model.addAttribute("bookings", bookingRepository.findAll());
        return "bookings";  // Thymeleaf template: bookings.html
    }

    // Show add booking page
    @GetMapping("/add")
    public String addBookingForm(Model model) {
        model.addAttribute("booking", new Booking());
        return "add-booking"; // create add-booking.html
    }

    // Save booking (form submission)
    @PostMapping("/add")
    public String saveBooking(@ModelAttribute Booking booking) {
        bookingRepository.save(booking);
        return "redirect:/bookings";
    }

    // Show edit booking page
    @GetMapping("/edit/{id}")
    public String editBookingForm(@PathVariable Long id, Model model) {
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid booking ID: " + id));
        model.addAttribute("booking", booking);
        return "edit-booking"; // create edit-booking.html
    }

    // Update booking (form submission)
    @PostMapping("/edit/{id}")
    public String updateBooking(@PathVariable Long id, @ModelAttribute Booking booking) {
        booking.setId(id);
        bookingRepository.save(booking);
        return "redirect:/bookings";
    }

    // Delete booking
    @GetMapping("/delete/{id}")
    public String deleteBooking(@PathVariable Long id) {
        bookingRepository.deleteById(id);
        return "redirect:/bookings";
    }
}
