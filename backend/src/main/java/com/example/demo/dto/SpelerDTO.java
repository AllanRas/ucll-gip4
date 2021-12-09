package com.example.demo.dto;

import java.io.Serializable;
import java.util.Date;

public class SpelerDTO implements Serializable {
    private long id;
    private UserDTO userDTO;
    private AdresDTO adresDTO;
    private boolean actief;
    private Date geboortedatum;

    public SpelerDTO(){}

    private SpelerDTO(Builder builder){
        setId(builder.id);
        setUserDTO(builder.userDTO);
        setAdresDTO(builder.adresDTO);
        setActief(builder.actief);
        setGeboortedatum(builder.geboortedatum);
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

    public Date getGeboortedatum() {
        return geboortedatum;
    }

    public void setGeboortedatum(Date geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    @Override
    public String toString() {
        return "SpelerDTO{" +
                "id=" + id +
                ", userDTO=" + userDTO +
                ", adresDTO=" + adresDTO +
                ", actief=" + actief +
                ", geboortedatum=" + geboortedatum +
                '}';
    }

    public static final class Builder {
        private long id;
        private UserDTO userDTO;
        private AdresDTO adresDTO;
        private boolean actief;
        private Date geboortedatum;

        public Builder(){}

        public Builder(SpelerDTO copy){
            this.id = copy.getId();
            this.userDTO = copy.getUserDTO();
            this.adresDTO = copy.getAdresDTO();
            this.actief = copy.isActief();
            this.geboortedatum = copy.getGeboortedatum();
        }

        public Builder user(UserDTO val){
            userDTO = val;
            return this;
        }

        public Builder adres(AdresDTO val){
            adresDTO = val;
            return this;
        }

        public Builder id(Long val){
            id = val;
            return this;
        }

        public Builder actief(boolean val){
            actief = val;
            return this;
        }

        public Builder geboortedatum(Date val){
            geboortedatum = val;
            return this;
        }

        public SpelerDTO build(){return new SpelerDTO(this);}
    }

}
