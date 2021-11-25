package com.example.demo.dto;

import com.example.demo.domain.Adres;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class SpelerDTO implements Serializable {
    private long id;
    private UserDTO userDTO;
    private AdresDTO adresDTO;
    private boolean actief;
    private boolean reserve;
    private Date geboortedatum;
    

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public AdresDTO getAdresDTO() {
        return adresDTO;
    }

    public void setAdresDTO(AdresDTO adresDTO) {
        this.adresDTO = adresDTO;
    }

    public boolean isActief() {
        return actief;
    }

    public void setActief(boolean actief) {
        this.actief = actief;
    }

    public boolean isReserve() {
        return reserve;
    }

    public void setReserve(boolean reserve) {
        this.reserve = reserve;
    }

    public Date getGeboortedatum() {
        return geboortedatum;
    }

    public void setGeboortedatum(Date geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

}
