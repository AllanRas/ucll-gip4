package com.example.demo.Services;

import com.example.demo.dao.SpelerRepository;
import com.example.demo.domain.Speler;
import org.springframework.stereotype.Service;

@Service
public class SpelerService {

    private final SpelerRepository spelerRepository;

    public SpelerService(SpelerRepository spelerRepository){
        this.spelerRepository = spelerRepository;
    }

    // Moet nog veranderen naar DTO
    public Speler createSpeler(Speler spelerDTO){
        Speler speler = spelerRepository.save(spelerDTO);
        return speler;
    }
}
