package com.busticket.Booking.Application.controller;

import com.busticket.Booking.Application.model.Bus;
import com.busticket.Booking.Application.repository.BusRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/buses")
public class BusController {

    @Autowired
    private BusRepository busRepository;

    // Get all buses
    @GetMapping
    public List<Bus> getAllBuses() {
        return busRepository.findAll();
    }

    // Get a bus by ID
    @GetMapping("/{id}")
    public ResponseEntity<Bus> getBusById(@PathVariable Long id) {
        Optional<Bus> bus = busRepository.findById(id);
        return bus.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Add a new bus
    @PostMapping
    public ResponseEntity<?> createBus(@Valid @RequestBody Bus bus, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Validation errors: " + bindingResult.getAllErrors());
        }
        try {
            Bus savedBus = busRepository.save(bus);
            return ResponseEntity.ok(savedBus);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating bus: " + e.getMessage());
        }
    }

    // Update a bus
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBus(@PathVariable Long id, @Valid @RequestBody Bus busDetails, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Validation errors: " + bindingResult.getAllErrors());
        }
        try {
            Optional<Bus> optionalBus = busRepository.findById(id);
            if (optionalBus.isPresent()) {
                Bus bus = optionalBus.get();
                bus.setBusName(busDetails.getBusName());
                bus.setSource(busDetails.getSource());
                bus.setDestination(busDetails.getDestination());
                bus.setDepartureTime(busDetails.getDepartureTime());
                bus.setArrivalTime(busDetails.getArrivalTime());
                bus.setTotalSeats(busDetails.getTotalSeats());
                bus.setFare(busDetails.getFare());
                Bus updatedBus = busRepository.save(bus);
                return ResponseEntity.ok(updatedBus);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating bus: " + e.getMessage());
        }
    }

    // Delete a bus
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBus(@PathVariable Long id) {
        if (busRepository.existsById(id)) {
            busRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
