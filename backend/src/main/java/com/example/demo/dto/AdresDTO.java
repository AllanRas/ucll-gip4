package com.example.demo.dto;

import com.example.demo.domain.Adres;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Objects;

public class AdresDTO implements Serializable {
    private long id;
    private String gemeente;
    private String straat;
    private String huisnummer;
    private String postcode;

    public AdresDTO(){

    }

    private AdresDTO(Builder builder){
        setId(builder.id);
        setGemeente(builder.gemeente);
        setStraat(builder.straat);
        setHuisnummer(builder.huisnummer);
        setPostcode(builder.postcode);
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

    public static final class Builder {
        private long id;
        private String gemeente;
        private String straat;
        private String huisnummer;
        private String postcode;

        public Builder() {}

        public Builder(AdresDTO copy){
            this.id = copy.getId();
            this.gemeente = copy.getGemeente();
            this.straat = copy.getStraat();
            this.huisnummer = copy.getHuisnummer();
            this.postcode = copy.getPostcode();
        }

        public Builder id(Long val){
            id = val;
            return this;
        }

        public Builder gemeente(String val){
            gemeente = val;
            return this;
        }

        public Builder straat(String val){
            straat = val;
            return this;
        }

        public Builder huisnummer(String val){
            huisnummer = val;
            return this;
        }

        public Builder postcode(String val){
            postcode = val;
            return this;
        }

        public AdresDTO build(){ return new AdresDTO(this);}
    }
}