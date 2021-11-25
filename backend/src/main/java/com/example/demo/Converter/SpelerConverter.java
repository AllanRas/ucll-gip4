package com.example.demo.Converter;

import com.example.demo.domain.Speler;
import com.example.demo.dto.SpelerDTO;
import org.springframework.stereotype.Component;

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
}
