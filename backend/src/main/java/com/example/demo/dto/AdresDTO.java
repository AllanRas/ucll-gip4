package com.example.demo.dto;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Objects;

public class AdresDTO implements Serializable {
    private long id;
    private String gemeente;
    private String straat;
    private String huisnummer;
    private String postcode;

    public AdresDTO() {
    }

    public AdresDTO(String gemeente, String straat, String huisnummer, String postcode) {
        this.gemeente = gemeente;
        this.straat = straat;
        this.huisnummer = huisnummer;
        this.postcode = postcode;
    }

    public AdresDTO(long id, String gemeente, String straat, String huisnummer, String postcode) {
        this.id = id;
        this.gemeente = gemeente;
        this.straat = straat;
        this.huisnummer = huisnummer;
        this.postcode = postcode;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGemeente() {
        return gemeente;
    }

    public void setGemeente(String gemeente) {
        this.gemeente = gemeente;
    }

    public String getStraat() {
        return straat;
    }

    public void setStraat(String straat) {
        this.straat = straat;
    }

    public String getHuisnummer() {
        return huisnummer;
    }

    public void setHuisnummer(String huisnummer) {
        this.huisnummer = huisnummer;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdresDTO adresDTO = (AdresDTO) o;
        return id == adresDTO.id && Objects.equals(gemeente, adresDTO.gemeente) && Objects.equals(straat, adresDTO.straat) && Objects.equals(huisnummer, adresDTO.huisnummer) && Objects.equals(postcode, adresDTO.postcode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, gemeente, straat, huisnummer, postcode);
    }

    @Override
    public String toString() {
        return "AdresDTO{" +
                "id=" + id +
                ", gemeente='" + gemeente + '\'' +
                ", straat='" + straat + '\'' +
                ", huisnummer='" + huisnummer + '\'' +
                ", postcode='" + postcode + '\'' +
                '}';
    }
}
