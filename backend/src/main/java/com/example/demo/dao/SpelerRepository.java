package com.example.demo.dao;

import com.example.demo.domain.Speler;
import com.example.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpelerRepository extends JpaRepository<Speler, Long> {
    Optional<Speler> findByUser(User user);
}
