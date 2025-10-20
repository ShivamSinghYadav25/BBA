package com.busticket.Booking.Application.repository;

import com.busticket.Booking.Application.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUserEmail(String email);
}
