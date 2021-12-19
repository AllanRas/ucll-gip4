package com.example.demo.dto;

public class UserSimpleDTO {
    private long id;
    private String username;
    private String role;

    public UserSimpleDTO(){

    }

    public UserSimpleDTO(Builder builder){
        setId(builder.id);
        setUsername(builder.username);
        setRole(builder.role);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public static final class Builder{

        private long id;
        private String username;
        private String role;

        public Builder(){

        }

        public Builder(UserSimpleDTO copy){
            this.id = copy.getId();
            this.username = copy.getUsername();
            this.role = copy.getRole();
        }

        public Builder id(Long val){
            id = val;
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

        public UserSimpleDTO build(){return new UserSimpleDTO(this);}
    }
}
