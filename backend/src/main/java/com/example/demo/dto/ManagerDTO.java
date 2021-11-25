package com.example.demo.dto;

import java.io.Serializable;
import java.util.Objects;

public class ManagerDTO implements Serializable {
    private long id;
    private UserDTO userDTO;

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

}
