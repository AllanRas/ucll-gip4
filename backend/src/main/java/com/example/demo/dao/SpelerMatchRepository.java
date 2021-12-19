package com.example.demo.dao;

import com.example.demo.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SpelerMatchRepository extends JpaRepository<SpelerMatch, Long> {
    Optional<List<SpelerMatch>> findBySpeler(Speler speler);
}
