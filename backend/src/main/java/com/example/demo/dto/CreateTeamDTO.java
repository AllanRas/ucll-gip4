package com.example.demo.dto;

import liquibase.pro.packaged.B;

public class CreateTeamDTO {

    private long id;
    private String naam;
    private ManagerDTO managerDTO;
    private boolean actief;

    public CreateTeamDTO(){

    }

    public CreateTeamDTO(Builder builder){
        setId(builder.id);
        setNaam(builder.naam);
        setManagerDTO(builder.managerDTO);
        setActief(builder.actief);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public ManagerDTO getManagerDTO() {
        return managerDTO;
    }

    public void setManagerDTO(ManagerDTO managerDTO) {
        this.managerDTO = managerDTO;
    }

    public boolean isActief() {
        return actief;
    }

    public void setActief(boolean actief) {
        this.actief = actief;
    }

    @Override
    public String toString() {
        return "CreateTeamDTO{" +
                "id=" + id +
                ", naam='" + naam + '\'' +
                ", managerDTO=" + managerDTO +
                ", actief=" + actief +
                '}';
    }

    public static final class Builder {
        private long id;
        private String naam;
        private ManagerDTO managerDTO;
        private boolean actief;

        public Builder(){}

        public Builder(CreateTeamDTO copy){
            id = copy.getId();
            naam = copy.getNaam();
            managerDTO = copy.getManagerDTO();
            actief = copy.isActief();
        }

        public Builder id(Long val){
            id = val;
            return this;
        }

        public Builder naam(String val){
            naam = val;
            return this;
        }

        public Builder managerDTO(ManagerDTO val){
            managerDTO = val;
            return this;
        }

        public Builder actief(boolean val){
            actief = val;
            return this;
        }

        public CreateTeamDTO build(){return new CreateTeamDTO(this);}
    }
}
