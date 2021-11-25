package com.example.demo.Services;

import com.example.demo.Converter.SpelerConverter;
import com.example.demo.dao.SpelerRepository;
import com.example.demo.domain.Speler;
import com.example.demo.dto.SpelerDTO;
import org.springframework.stereotype.Service;

@Service
public class SpelerService {

    private final SpelerRepository spelerRepository;

    private final SpelerConverter spelerConverter;

    public SpelerService(SpelerRepository spelerRepository, SpelerConverter spelerConverter){
        this.spelerRepository = spelerRepository;
        this.spelerConverter = spelerConverter;
    }

    public SpelerDTO createSpeler(SpelerDTO spelerDTO){
        Speler speler = spelerRepository.save(spelerConverter.dtoToSpeler(spelerDTO));
        return spelerConverter.spelerToDTO(speler);
    }

}
