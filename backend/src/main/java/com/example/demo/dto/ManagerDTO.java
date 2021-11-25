package com.example.demo.dto;

import java.io.Serializable;
import java.util.Objects;

public class ManagerDTO implements Serializable {
    private long id;
    private UserDTO userDTO;

    public ManagerDTO() {
    }

    public ManagerDTO(long id, UserDTO userDTO) {
        this.id = id;
        this.userDTO = userDTO;
    }

    public ManagerDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ManagerDTO that = (ManagerDTO) o;
        return id == that.id && Objects.equals(userDTO, that.userDTO);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userDTO);
    }

    @Override
    public String toString() {
        return "ManagerDTO{" +
                "id=" + id +
                ", userDTO=" + userDTO +
                '}';
    }
}
