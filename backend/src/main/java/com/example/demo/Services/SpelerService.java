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
        Speler speler = spelerConverter.dtoToSpeler(spelerDTO);

        //  actief op true en geef de role SPELER
        speler.setActief(true);
        speler.getUser().setRole("SPELER");

        spelerRepository.save(speler);
        return spelerConverter.spelerToDTO(speler);
    }

}
