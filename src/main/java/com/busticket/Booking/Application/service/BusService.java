package com.busticket.Booking.Application.service;

import com.busticket.Booking.Application.model.Bus;
import com.busticket.Booking.Application.repository.BusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BusService {

    @Autowired
    private BusRepository busRepository;

    // Get all buses
    public List<Bus> getAllBuses() {
        return busRepository.findAll();
    }

    // Get bus by ID
    public Optional<Bus> getBusById(Long id) {
        return busRepository.findById(id);
    }

    // Create bus
    public Bus createBus(Bus bus) {
        return busRepository.save(bus);
    }

    // Update bus
    public Bus updateBus(Long id, Bus busDetails) {
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
            return busRepository.save(bus);
        } else {
            return null;
        }
    }

    // Delete bus
    public boolean deleteBus(Long id) {
        if (busRepository.existsById(id)) {
            busRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
