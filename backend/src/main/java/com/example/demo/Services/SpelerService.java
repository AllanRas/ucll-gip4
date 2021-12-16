package com.example.demo.Services;

import com.example.demo.Converter.SpelerConverter;
import com.example.demo.dao.SpelerRepository;
import com.example.demo.domain.Speler;
import com.example.demo.dto.CreateSpelerDTO;
import com.example.demo.dto.SpelerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpelerService {
    private final SpelerRepository spelerRepository;

    private final SpelerConverter spelerConverter;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public SpelerService(SpelerRepository spelerRepository, SpelerConverter spelerConverter){
        this.spelerRepository = spelerRepository;
        this.spelerConverter = spelerConverter;
    }

    public SpelerDTO createSpeler(CreateSpelerDTO createSpelerDTO){
        Speler speler = spelerConverter.createSpelerDTOToSpeler(createSpelerDTO);
        //  actief op true en geef de role SPELER
        speler.setActief(true);
        speler.getUser().setRole("SPELER");

        // password encoden
        speler.getUser().setPassword(passwordEncoder.encode(speler.getUser().getPassword()));

        spelerRepository.save(speler);
        return spelerConverter.spelerToDTO(speler);
    }

    public List<SpelerDTO> getAllSpelers(){
        return spelerConverter.spelerListToDTO(spelerRepository.findAll());
    }

    public SpelerDTO getById(long id){
        Optional<Speler> speler = spelerRepository.findById(id);
        return spelerConverter.spelerToDTO(speler.orElseThrow());
    }

    public SpelerDTO inActiveSpeler(long id){
        Optional<Speler> speler = spelerRepository.findById(id);
        // speler is niet meer actief dus = verwijderd

        if(speler.orElseThrow().isActief()){
            speler.orElseThrow().setActief(false);
        }else{
            speler.orElseThrow().setActief(true);
        }

        spelerRepository.save(speler.get());
        return spelerConverter.spelerToDTO(speler.get());
    }

    public SpelerDTO updateSpeler(long id, SpelerDTO spelerDTO){

        Optional<Speler> speler = spelerRepository.findById(id);

        if(speler.isPresent()){
            Speler spelerUpdate = spelerConverter.dtoToSpeler(spelerDTO);
            Speler newSpeler = speler.get();

            // Speler.user update
            newSpeler.getUser().setUsername(spelerUpdate.getUser().getUsername());
            newSpeler.getUser().setVoornaam(spelerUpdate.getUser().getVoornaam());
            newSpeler.getUser().setAchternaam(spelerUpdate.getUser().getAchternaam());
            newSpeler.getUser().setEmail(spelerUpdate.getUser().getEmail());

            // speler.adres update
            newSpeler.getAdres().setGemeente(spelerUpdate.getAdres().getGemeente());
            newSpeler.getAdres().setPostcode(spelerUpdate.getAdres().getPostcode());
            newSpeler.getAdres().setStraat(spelerUpdate.getAdres().getStraat());
            newSpeler.getAdres().setHuisnummer(spelerUpdate.getAdres().getHuisnummer());

            //speler update
            newSpeler.setGeboortedatum(spelerUpdate.getGeboortedatum());
            spelerRepository.save(newSpeler);
        }
        return spelerConverter.spelerToDTO(speler.orElseThrow());
    }
}
