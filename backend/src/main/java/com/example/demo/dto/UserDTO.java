package com.example.demo.dto;

import com.example.demo.domain.User;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Objects;

public class UserDTO implements Serializable {

    private long id;
    private String voornaam;
    private String achternaam;
    private String username;
    private String role;
    private String email;

    public UserDTO(){

    }

    public UserDTO(Builder builder){
        setId(builder.id);
        setVoornaam(builder.voornaam);
        setAchternaam(builder.achternaam);
        setUsername(builder.username);
        setEmail(builder.email);
        setRole(builder.role);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static final class Builder{

        private long id;
        private String voornaam;
        private String achternaam;
        private String username;
        private String role;
        private String email;

        public Builder(){

        }

        public Builder(UserDTO copy){
            this.id = copy.getId();
            this.voornaam = copy.getVoornaam();
            this.achternaam = copy.getAchternaam();
            this.username = copy.getUsername();
            this.role = copy.getRole();
            this.email = copy.getEmail();
        }

        public Builder id(Long val){
            id = val;
            return this;
        }

        public Builder voornaam(String val){
            voornaam = val;
            return this;
        }

        public Builder achternaam(String val){
            achternaam = val;
            return this;
        }

        public Builder username(String val){
            username = val;
            return this;
        }

        public Builder role(String val){
            role = val;
            return this;
        }

        public Builder email(String val){
            email = val;
            return this;
        }

        public UserDTO build(){return new UserDTO(this);}
    }
}
