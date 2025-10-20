package com.busticket.Booking.Application.repository;

import com.busticket.Booking.Application.model.Bus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BusRepository extends JpaRepository<Bus, Long> {
    List<Bus> findBySourceAndDestination(String source, String destination);
}

