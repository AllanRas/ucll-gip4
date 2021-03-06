package com.example.demo.Converter;

import com.example.demo.domain.Speler;
import com.example.demo.dto.CreateSpelerDTO;
import com.example.demo.dto.SpelerDTO;
import com.example.demo.dto.SpelerSimpleDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class SpelerConverter {

    UserConverter userConverter = new UserConverter();
    AdresConverter adresConverter = new AdresConverter();

    //speler to DTO speler
    public SpelerDTO spelerToDTO(Speler speler){
        SpelerDTO spelerDTO = new SpelerDTO();
        spelerDTO.setId(speler.getId());
        spelerDTO.setUserDTO(userConverter.userToDTO(speler.getUser()));
        spelerDTO.setAdresDTO(adresConverter.adresToDTO(speler.getAdres()));
        spelerDTO.setGeboortedatum(speler.getGeboortedatum());
        spelerDTO.setActief(speler.isActief());
        return  spelerDTO;
    }

    public List<SpelerDTO> spelerListToDTO(List<Speler> spelers){
        return spelers.stream().map(this::spelerToDTO).collect(Collectors.toList());
    }

    // spelerDTO to speler
    public Speler dtoToSpeler(SpelerDTO spelerDTO){
        Speler speler = new Speler();
        speler.setId(spelerDTO.getId());
        speler.setUser(userConverter.userDTOtoUser(spelerDTO.getUserDTO()));
        speler.setAdres(adresConverter.adresDTOtoadres(spelerDTO.getAdresDTO()));
        speler.setActief(spelerDTO.isActief());
        speler.setGeboortedatum(spelerDTO.getGeboortedatum());
        return speler;
    }

    // createSpelerDTO to speler
    public Speler createSpelerDTOToSpeler(CreateSpelerDTO createSpelerDTO){
        Speler speler = new Speler();
        speler.setId(createSpelerDTO.getId());
        speler.setUser(userConverter.userDTOtoUser(createSpelerDTO.getUserDTO()));
        speler.setAdres(adresConverter.adresDTOtoadres(createSpelerDTO.getAdresDTO()));
        speler.getUser().setPassword(createSpelerDTO.getPassword());
        speler.setActief(createSpelerDTO.isActief());
        speler.setGeboortedatum(createSpelerDTO.getGeboortedatum());
        return speler;
    }

    //speler to DTO speler
    public CreateSpelerDTO SpelerToCreateSpelerDTO(Speler speler){
        CreateSpelerDTO createSpelerDTO = new CreateSpelerDTO();
        createSpelerDTO.setId(speler.getId());
        createSpelerDTO.setPassword(speler.getUser().getPassword());
        createSpelerDTO.setUserDTO(userConverter.userToDTO(speler.getUser()));
        createSpelerDTO.setAdresDTO(adresConverter.adresToDTO(speler.getAdres()));
        createSpelerDTO.setGeboortedatum(speler.getGeboortedatum());
        createSpelerDTO.setActief(speler.isActief());
        return  createSpelerDTO;
    }

    public Set<SpelerDTO> spelerSetToDTO(Set<Speler> spelers){
        return spelers.stream().map(this::spelerToDTO).collect(Collectors.toSet());
    }


    //speler to DTO speler
    public SpelerSimpleDTO spelerToSimpleDTO(Speler speler){
        SpelerSimpleDTO spelerSimpleDTO = new SpelerSimpleDTO();

        spelerSimpleDTO.setId(speler.getId());
        spelerSimpleDTO.setUserSimpleDTO(userConverter.userToSimpleDTO(speler.getUser()));
        spelerSimpleDTO.setActief(speler.isActief());
        return  spelerSimpleDTO;
    }

    public List<SpelerSimpleDTO> spelerListToSimpleDTO(List<Speler> spelers){
        return spelers.stream().map(this::spelerToSimpleDTO).collect(Collectors.toList());
    }

}
