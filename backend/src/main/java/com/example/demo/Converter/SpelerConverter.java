package com.example.demo.Converter;

import com.example.demo.domain.Speler;
import com.example.demo.domain.User;
import com.example.demo.dto.CreateSpelerDTO;
import com.example.demo.dto.SpelerDTO;
import com.example.demo.dto.UserDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
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

    public List<SpelerDTO> SpelerListToDTO(List<Speler> spelers){
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

}
