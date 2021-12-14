package com.example.demo.dto;

import com.example.demo.domain.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class TeamDTO implements Serializable {
    private long id;
    private String naam;
    private ManagerDTO managerDTO;
    private Set<SpelerTeamDTO> spelers;
    private Set<Match> matchesteamBlue = new HashSet<>();
    private Set<Match> matchesteamRed = new HashSet<>();
    private boolean actief;

    public TeamDTO(){}

    private TeamDTO(Builder builder){
        setId(builder.id);
        setNaam(builder.naam);
        setManagerDTO(builder.managerDTO);
        setActief(builder.actief);
        setMatchesteamBlue(builder.matchesteamBlue);
        setMatchesteamRed(builder.matchesteamRed);
        setSpelerDTO(builder.spelers);
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

    public Set<SpelerTeamDTO> getSpelerDTO() {
        return spelers;
    }

    public void setSpelerDTO(Set<SpelerTeamDTO> spelerDTO) {
        this.spelers = spelerDTO;
    }

    public Set<Match> getMatchesteamBlue() {
        return matchesteamBlue;
    }

    public void setMatchesteamBlue(Set<Match> matchesteamBlue) {
        this.matchesteamBlue = matchesteamBlue;
    }

    public Set<Match> getMatchesteamRed() {
        return matchesteamRed;
    }

    public void setMatchesteamRed(Set<Match> matchesteamRed) {
        this.matchesteamRed = matchesteamRed;
    }

    public boolean isActief() {
        return actief;
    }

    public void setActief(boolean actief) {
        this.actief = actief;
    }

    @Override
    public String toString() {
        return "TeamDTO{" +
                "id=" + id +
                ", naam='" + naam + '\'' +
                ", managerDTO=" + managerDTO +
                ", spelers=" + spelers +
                ", matchesteamBlue=" + matchesteamBlue +
                ", matchesteamRed=" + matchesteamRed +
                ", actief=" + actief +
                '}';
    }

    public static final class Builder {
        private long id;
        private String naam;
        private ManagerDTO managerDTO;
        private Set<SpelerTeamDTO> spelers;
        private Set<Match> matchesteamBlue = new HashSet<>();
        private Set<Match> matchesteamRed = new HashSet<>();
        private boolean actief;

        public Builder(){}

        public Builder(TeamDTO copy){
            this.id = copy.getId();
            this.naam = copy.getNaam();
            this.managerDTO = copy.getManagerDTO();
            this.spelers = copy.getSpelerDTO();
            this.actief = copy.isActief();
            this.matchesteamBlue = copy.getMatchesteamBlue();
            this.matchesteamRed = copy.getMatchesteamRed();
        }

        public Builder id(Long val){
            id = val;
            return this;
        }

        public Builder naam(String val){
            naam = val;
            return this;
        }

        public Builder manager(ManagerDTO val){
            managerDTO = val;
            return this;
        }

        public Builder speler(Set<SpelerTeamDTO> val){
            spelers = val;
            return this;
        }

        public Builder actief(boolean val){
            actief = val;
            return this;
        }

        public Builder matchesteamBlue(Set<Match> val){
            matchesteamBlue = val;
            return this;
        }

        public Builder matchesteamRed(Set<Match> val){
            matchesteamRed = val;
            return this;
        }


        public TeamDTO build(){return new TeamDTO(this);}
    }
}


