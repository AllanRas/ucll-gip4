package com.example.demo.Services;

import com.example.demo.Converter.SpelerConverter;
import com.example.demo.dao.SpelerRepository;
import com.example.demo.domain.Speler;
import com.example.demo.dto.SpelerDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public List<SpelerDTO> getAllSpelers(){
        return spelerConverter.SpelerListToDTO(spelerRepository.findAll());
    }

    public SpelerDTO getById(long id){
        Optional<Speler> speler = spelerRepository.findById(id);
        if(speler.isPresent()){
            return spelerConverter.spelerToDTO(speler.get());
        }else{
            throw new NullPointerException();
        }
    }

    public SpelerDTO inActiveSpeler(long id){
        Optional<Speler> speler = spelerRepository.findById(id);
        // speler is niet meer actief dus = verwijderd
        speler.orElseThrow().setActief(false);

        spelerRepository.save(speler.get());
        return spelerConverter.spelerToDTO(speler.get());
    }

    public SpelerDTO updateSpeler(long id, SpelerDTO spelerDTO){
        Optional<Speler> speler = Optional.ofNullable(spelerConverter.dtoToSpeler(spelerDTO));
        speler = spelerRepository.findById(id);

        spelerRepository.save(speler.orElseThrow());
        return spelerConverter.spelerToDTO(speler.orElseThrow());
    }
}
