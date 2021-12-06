package com.example.demo.Converter;

import com.example.demo.domain.Adres;
import com.example.demo.dto.AdresDTO;
import org.springframework.stereotype.Component;

@Component
public class AdresConverter {

    // adres to adresDTO
    public AdresDTO adresToDTO(Adres adres){
        AdresDTO adresDTO = new AdresDTO();
        adresDTO.setId(adres.getId());
        adresDTO.setGemeente(adres.getGemeente());
        adresDTO.setPostcode(adres.getPostcode());
        adresDTO.setStraat(adres.getStraat());
        adresDTO.setHuisnummer(adres.getHuisnummer());
        return adresDTO;
    }


    // adresDTO to adres
    public Adres adresDTOtoadres(AdresDTO adresDTO){
        Adres adres = new Adres();
        adres.setId(adresDTO.getId());
        adres.setGemeente(adresDTO.getGemeente());
        adres.setPostcode(adresDTO.getPostcode());
        adres.setStraat(adresDTO.getStraat());
        adres.setHuisnummer(adresDTO.getHuisnummer());
        return adres;
    }
}
