package com.example.demo.dto;

import com.example.demo.domain.Match;
import com.example.demo.domain.ReserveSpelerTeam;

import java.io.Serializable;

public class TeamDTO implements Serializable {
    private long id;
    private ManagerDTO managerDTO;
    private SpelerDTO spelerDTO;
    private Match match;
    private ReserveSpelerTeam reserveSpelerTeam;
    private boolean actief;

    public TeamDTO(){}

    private TeamDTO(TeamDTO.Builder builder){
        setId(builder.id);
        setManagerDTO(builder.managerDTO);
        setActief(builder.actief);
        setMatch(builder.match);
        setSpelerDTO(builder.spelerDTO);
        setReserveSpelerTeam(builder.reserveSpelerTeam);
    }

    public long getId() {return id;}

    public void setId(long id) {this.id = id;}

    public boolean isActief() {return actief;}

    public void setActief(boolean actief) {this.actief = actief;}

    public Match getMatch() {return match;}

    public void setMatch(Match match) {this.match = match;}

    public SpelerDTO getSpelerDTO() {return spelerDTO;}

    public void setSpelerDTO(SpelerDTO spelerDTO) {this.spelerDTO = spelerDTO;}

    public ManagerDTO getManagerDTO() {return managerDTO;}

    public void setManagerDTO(ManagerDTO managerDTO) {this.managerDTO = managerDTO;}

    public void setReserveSpelerTeam(ReserveSpelerTeam reserveSpelerTeam) {this.reserveSpelerTeam = reserveSpelerTeam;}

    public ReserveSpelerTeam getReserveSpelerTeam() {return reserveSpelerTeam;}

    public static final class Builder {
        private long id;
        private ManagerDTO managerDTO;
        private SpelerDTO spelerDTO;
        private Match match;
        private ReserveSpelerTeam reserveSpelerTeam;
        private boolean actief;

        public Builder(){}

        public Builder(TeamDTO copy){
            this.id = copy.getId();
            this.managerDTO = copy.getManagerDTO();
            this.spelerDTO = copy.getSpelerDTO();
            this.actief = copy.isActief();
            this.match = copy.getMatch();
            this.reserveSpelerTeam = copy.getReserveSpelerTeam();
        }

        public TeamDTO.Builder manager(ManagerDTO val){
            managerDTO = val;
            return this;
        }

        public TeamDTO.Builder speler(SpelerDTO val){
            spelerDTO = val;
            return this;
        }

        public TeamDTO.Builder id(Long val){
            id = val;
            return this;
        }

        public TeamDTO.Builder actief(boolean val){
            actief = val;
            return this;
        }

        public TeamDTO.Builder match(Match val){
            match = val;
            return this;
        }

        public TeamDTO.Builder reserveSpelerTeam(ReserveSpelerTeam val){
            reserveSpelerTeam = val;
            return this;
        }

        public TeamDTO build(){return new TeamDTO(this);}
    }
}


