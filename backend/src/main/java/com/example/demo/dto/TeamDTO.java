package com.example.demo.dto;

import com.example.demo.domain.Match;
import com.example.demo.domain.ReserveSpelerTeam;
import com.example.demo.domain.SpelerTeam;

import java.io.Serializable;
import java.util.Set;

public class TeamDTO implements Serializable {
    private long id;
    private String naam;
    private ManagerDTO managerDTO;
    private Set<SpelerTeam> spelers;
    private Set<Match> match;
    private Set<ReserveSpelerTeam> reserveSpelerTeam;
    private boolean actief;

    public TeamDTO(){}

    private TeamDTO(Builder builder){
        setId(builder.id);
        setNaam(builder.naam);
        setManagerDTO(builder.managerDTO);
        setActief(builder.actief);
        setMatch(builder.match);
        setSpelerDTO(builder.spelers);
        setReserveSpelerTeam(builder.reserveSpelerTeam);
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

    public Set<SpelerTeam> getSpelers() {
        return spelers;
    }

    public void setSpelers(Set<SpelerTeam> spelers) {
        this.spelers = spelers;
    }

    public ManagerDTO getManagerDTO() {
        return managerDTO;
    }

    public void setManagerDTO(ManagerDTO managerDTO) {
        this.managerDTO = managerDTO;
    }

    public Set<SpelerTeam> getSpelerDTO() {
        return spelers;
    }

    public void setSpelerDTO(Set<SpelerTeam> spelerDTO) {
        this.spelers = spelerDTO;
    }

    public Set<Match> getMatch() {
        return match;
    }

    public void setMatch(Set<Match> match) {
        this.match = match;
    }

    public Set<ReserveSpelerTeam> getReserveSpelerTeam() {
        return reserveSpelerTeam;
    }

    public void setReserveSpelerTeam(Set<ReserveSpelerTeam> reserveSpelerTeam) {
        this.reserveSpelerTeam = reserveSpelerTeam;
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
                ", match=" + match +
                ", reserveSpelerTeam=" + reserveSpelerTeam +
                ", actief=" + actief +
                '}';
    }

    public static final class Builder {
        private long id;
        private String naam;
        private ManagerDTO managerDTO;
        private Set<SpelerTeam> spelers;
        private Set<Match> match;
        private Set<ReserveSpelerTeam> reserveSpelerTeam;
        private boolean actief;

        public Builder(){}

        public Builder(TeamDTO copy){
            this.id = copy.getId();
            this.naam = copy.getNaam();
            this.managerDTO = copy.getManagerDTO();
            this.spelers = copy.getSpelerDTO();
            this.actief = copy.isActief();
            this.match = copy.getMatch();
            this.reserveSpelerTeam = copy.getReserveSpelerTeam();
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

        public Builder speler(Set<SpelerTeam> val){
            spelers = val;
            return this;
        }

        public Builder actief(boolean val){
            actief = val;
            return this;
        }

        public Builder match(Set<Match> val){
            match = val;
            return this;
        }

        public Builder reserveSpelerTeam(Set<ReserveSpelerTeam> val){
            reserveSpelerTeam = val;
            return this;
        }

        public TeamDTO build(){return new TeamDTO(this);}
    }
}


