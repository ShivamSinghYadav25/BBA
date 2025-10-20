package com.busticket.Booking.Application.repository;

import com.busticket.Booking.Application.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Find a user by email (used for login)
    Optional<User> findByEmail(String email);

    // Check if email already exists (used for registration validation)
    boolean existsByEmail(String email);
}
