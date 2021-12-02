package com.example.demo.dto;

import java.io.Serializable;

public class ManagerDTO implements Serializable {
    private long id;
    private UserDTO userDTO;
    private String passwoord;

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

    public String getPasswoord() {
        return passwoord;
    }

    public void setPasswoord(String passwoord) {
        this.passwoord = passwoord;
    }

    @Override
    public String toString() {
        return "ManagerDTO{" +
                "id=" + id +
                ", userDTO=" + userDTO +
                ", passwoord='" + passwoord + '\'' +
                '}';
    }

    public ManagerDTO(){

    }

    public ManagerDTO(Builder builder){
        setId(builder.id);
        setUserDTO(builder.userDTO);
        setPasswoord(builder.passwoord);
    }

    public static final class Builder {
        private long id;
        private UserDTO userDTO;
        private String passwoord;

        public Builder(){

        }

        public Builder(ManagerDTO copy){
            this.id = copy.getId();
            this.userDTO = copy.getUserDTO();
            this.passwoord = copy.getPasswoord();
        }

        public Builder id(Long val){
            id = val;
            return this;
        }

        public Builder userDTO(UserDTO val){
            userDTO = val;
            return this;
        }

        public Builder passwoord(String val){
            passwoord = val;
            return this;
        }

        public ManagerDTO build(){return new ManagerDTO(this);}
    }
}
