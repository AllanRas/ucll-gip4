package com.example.demo.dto;

public class SpelerSimpleDTO {
    private long id;
    private UserSimpleDTO UserSimpleDTO;
    private boolean actief;

    public SpelerSimpleDTO(){}

    private SpelerSimpleDTO(Builder builder){
        setId(builder.id);
        setActief(builder.actief);
        setUserSimpleDTO(builder.userSimpleDTO);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserSimpleDTO getUserSimpleDTO() {
        return UserSimpleDTO;
    }

    public void setUserSimpleDTO(UserSimpleDTO userSimpleDTO) {
        UserSimpleDTO = userSimpleDTO;
    }

    public boolean isActief() {
        return actief;
    }

    public void setActief(boolean actief) {
        this.actief = actief;
    }

    public static final class Builder {
        private long id;
        private UserSimpleDTO userSimpleDTO;
        private boolean actief;

        public Builder(){}

        public Builder(SpelerSimpleDTO copy) {
            this.id = copy.getId();
            this.actief = copy.isActief();
            this.userSimpleDTO = copy.getUserSimpleDTO();
        }

        public Builder user(UserSimpleDTO val){
            userSimpleDTO = val;
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

        public SpelerSimpleDTO build(){return new SpelerSimpleDTO(this);}
    }
}
