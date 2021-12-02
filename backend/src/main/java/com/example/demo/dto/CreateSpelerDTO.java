package com.example.demo.dto;

import java.util.Date;

public class CreateSpelerDTO {

    private long id;
    private UserDTO userDTO;
    private AdresDTO adresDTO;
    private String password;
    private boolean actief;
    private Date geboortedatum;

    public CreateSpelerDTO(){}

    private CreateSpelerDTO(Builder builder){
        setId(builder.id);
        setUserDTO(builder.userDTO);
        setAdresDTO(builder.adresDTO);
        setActief(builder.actief);
        setGeboortedatum(builder.geboortedatum);
        setPassword(builder.password);
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
        return "CreateSpelerDTO{" +
                "id=" + id +
                ", userDTO=" + userDTO +
                ", adresDTO=" + adresDTO +
                ", password='" + password + '\'' +
                ", actief=" + actief +
                ", geboortedatum=" + geboortedatum +
                '}';
    }

    public static final class Builder {
        private long id;
        private UserDTO userDTO;
        private AdresDTO adresDTO;
        private String password;
        private boolean actief;
        private Date geboortedatum;

        public Builder(){}

        public Builder(CreateSpelerDTO copy){
            this.id = copy.getId();
            this.userDTO = copy.getUserDTO();
            this.adresDTO = copy.getAdresDTO();
            this.password = copy.getPassword();
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

        public Builder password(String val){
            password = val;
            return this;
        }

        public CreateSpelerDTO build(){return new CreateSpelerDTO(this);}
    }

}
