package com.example.demo.dao;

import com.example.demo.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpelerMatchRepository extends JpaRepository<SpelerMatch, Long> {
}
