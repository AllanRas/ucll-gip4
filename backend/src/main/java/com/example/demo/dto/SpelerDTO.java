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

    public SpelerDTO() {
    }

    public SpelerDTO(long id, UserDTO userDTO, AdresDTO adresDTO, boolean actief, boolean reserve, Date geboortedatum) {
        this.id = id;
        this.userDTO = userDTO;
        this.adresDTO = adresDTO;
        this.actief = actief;
        this.reserve = reserve;
        this.geboortedatum = geboortedatum;
    }

    public SpelerDTO(UserDTO userDTO, AdresDTO adresDTO, boolean actief, boolean reserve, Date geboortedatum) {
        this.userDTO = userDTO;
        this.adresDTO = adresDTO;
        this.actief = actief;
        this.reserve = reserve;
        this.geboortedatum = geboortedatum;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpelerDTO spelerDTO = (SpelerDTO) o;
        return id == spelerDTO.id && actief == spelerDTO.actief && reserve == spelerDTO.reserve && Objects.equals(userDTO, spelerDTO.userDTO) && Objects.equals(adresDTO, spelerDTO.adresDTO) && Objects.equals(geboortedatum, spelerDTO.geboortedatum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userDTO, adresDTO, actief, reserve, geboortedatum);
    }

    @Override
    public String toString() {
        return "SpelerDTO{" +
                "id=" + id +
                ", userDTO=" + userDTO +
                ", adresDTO=" + adresDTO +
                ", actief=" + actief +
                ", reserve=" + reserve +
                ", geboortedatum=" + geboortedatum +
                '}';
    }
}
