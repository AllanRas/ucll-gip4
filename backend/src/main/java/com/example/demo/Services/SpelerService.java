package com.example.demo.Services;

import com.example.demo.Converter.SpelerConverter;
import com.example.demo.config.UserPrincipal;
import com.example.demo.dao.SpelerRepository;
import com.example.demo.dao.SpelerTeamRepository;
import com.example.demo.domain.Speler;
import com.example.demo.domain.SpelerTeam;
import com.example.demo.dto.CreateSpelerDTO;
import com.example.demo.dto.SpelerDTO;
import com.example.demo.dto.SpelerSimpleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpelerService {
    private final SpelerRepository spelerRepository;
    private final SpelerTeamRepository spelerTeamRepository;

    private final SpelerConverter spelerConverter;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public SpelerService(SpelerRepository spelerRepository, SpelerTeamRepository spelerTeamRepository, SpelerConverter spelerConverter){
        this.spelerRepository = spelerRepository;
        this.spelerTeamRepository = spelerTeamRepository;
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

    public List<SpelerSimpleDTO> getAllSpelersSimple(){
        return spelerConverter.spelerListToSimpleDTO(spelerRepository.findAll());
    }

    public SpelerDTO getById(long id){

        Optional<Speler> speler = spelerRepository.findById(id);
        return spelerConverter.spelerToDTO(speler.orElseThrow());
    }

    public SpelerDTO getByIdAndUser(long id, UserPrincipal userPrincipal){
        Optional<Speler> speler = spelerRepository.findByUser(userPrincipal.getUser());
        return spelerConverter.spelerToDTO(speler.orElseThrow());
    }

    public SpelerDTO getByIdAndUser(UserPrincipal userPrincipal){
        Optional<Speler> speler = spelerRepository.findByUser(userPrincipal.getUser());
        return spelerConverter.spelerToDTO(speler.orElseThrow());
    }

    public SpelerDTO inActiveSpeler(long id){
        Speler speler = spelerRepository.findById(id).orElseThrow();
        List<SpelerTeam> spelerTeam = spelerTeamRepository.findBySpeler(speler).orElseThrow();

        // speler verwijderen van alle teams
        spelerTeamRepository.deleteAll(spelerTeam);
        // speler is niet meer actief dus = verwijderd
        if(speler.isActief()){
            speler.setActief(false);
        }else{
            speler.setActief(true);
        }

        spelerRepository.save(speler);
        return spelerConverter.spelerToDTO(speler);
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
