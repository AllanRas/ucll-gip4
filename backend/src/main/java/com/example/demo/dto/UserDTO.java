package com.example.demo.dto;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Objects;

public class UserDTO implements Serializable {

    private long id;
    private String voornaam;
    private String achternaam;
    private String username;
    private String password;
    private String role;
    private String email;

    public UserDTO() {
    }

    public UserDTO(String voornaam, String achternaam, String username, String password, String role, String email) {
        this.voornaam = voornaam;
        this.achternaam = achternaam;
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
    }

    public UserDTO(long id, String voornaam, String achternaam, String username, String password, String role, String email) {
        this.id = id;
        this.voornaam = voornaam;
        this.achternaam = achternaam;
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return id == userDTO.id && Objects.equals(voornaam, userDTO.voornaam) && Objects.equals(achternaam, userDTO.achternaam) && Objects.equals(username, userDTO.username) && Objects.equals(password, userDTO.password) && Objects.equals(role, userDTO.role) && Objects.equals(email, userDTO.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, voornaam, achternaam, username, password, role, email);
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", voornaam='" + voornaam + '\'' +
                ", achternaam='" + achternaam + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
