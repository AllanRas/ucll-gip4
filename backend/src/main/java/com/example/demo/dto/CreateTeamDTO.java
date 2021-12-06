package com.example.demo.dto;

import com.example.demo.domain.Match;
import com.example.demo.domain.ReserveSpelerTeam;

public class CreateTeamDTO {
    private long id;
    private ManagerDTO managerDTO;
    private SpelerDTO spelerDTO;
    private Match match;
    private ReserveSpelerTeam reserveSpelerTeam;
    private boolean actief;

    public CreateTeamDTO(){}

    private CreateTeamDTO(Builder builder){
        setId(builder.id);
        setManagerDTO(builder.managerDTO);
        setActief(builder.actief);
        setMatch(builder.match);
        setSpelerDTO(builder.spelerDTO);
        setReserveSpelerTeam(builder.reserveSpelerTeam);
    }


    public SpelerDTO getSpelerDTO() {return spelerDTO;}

    public void setSpelerDTO(SpelerDTO spelerDTO) {this.spelerDTO = spelerDTO;}

    public long getId() {return id;}

    public void setId(long id) {this.id = id;}

    public void setReserveSpelerTeam(ReserveSpelerTeam reserveSpelerTeam) {this.reserveSpelerTeam = reserveSpelerTeam;}

    public ReserveSpelerTeam getReserveSpelerTeam() {return reserveSpelerTeam;}

    public void setMatch(Match match) {this.match = match;}

    public Match getMatch() {return match;}

    public ManagerDTO getManagerDTO() {return managerDTO;}

    public void setManagerDTO(ManagerDTO managerDTO) {this.managerDTO = managerDTO;}

    public boolean isActief() {return actief;}

    public void setActief(boolean actief) {this.actief = actief;}

    @Override
    public String toString() {
        return "CreateSpelerDTO{" +
                "id=" + id +
                ", managerDTO=" + managerDTO +
                ", spelerDTO=" + spelerDTO +
                ", reserveSpelerTeam=" + reserveSpelerTeam +
                ", match='" + match + '\'' +
                ", actief=" + actief +
                '}';
    }


    public static final class Builder {
        private long id;
        private ManagerDTO managerDTO;
        private SpelerDTO spelerDTO;
        private Match match;
        private ReserveSpelerTeam reserveSpelerTeam;
        private boolean actief;

        public Builder(){}

        public Builder(CreateTeamDTO copy){
            this.id = copy.getId();
            this.managerDTO = copy.getManagerDTO();
            this.spelerDTO = copy.getSpelerDTO();
            this.actief = copy.isActief();
            this.match = copy.getMatch();
            this.reserveSpelerTeam = copy.getReserveSpelerTeam();
        }

        public CreateTeamDTO.Builder manager(ManagerDTO val){
            managerDTO = val;
            return this;
        }

        public CreateTeamDTO.Builder speler(SpelerDTO val){
            spelerDTO = val;
            return this;
        }

        public CreateTeamDTO.Builder id(Long val){
            id = val;
            return this;
        }

        public CreateTeamDTO.Builder actief(boolean val){
            actief = val;
            return this;
        }

        public CreateTeamDTO.Builder match(Match val){
            match = val;
            return this;
        }

        public CreateTeamDTO.Builder reserveSpelerTeam(ReserveSpelerTeam val){
            reserveSpelerTeam = val;
            return this;
        }

        public CreateTeamDTO build(){return new CreateTeamDTO(this);}
    }
}
