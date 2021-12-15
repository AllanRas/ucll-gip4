package com.example.demo.dto;

import com.example.demo.domain.Speler;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class CreateMatchDTO implements Serializable {

    private long id;
    private TeamDTO teamBlue;
    private TeamDTO teamRed;
    private Date datumtijd;
    public Set<SpelerMatchDTO> spelers;

    public CreateMatchDTO() {
    }

    private CreateMatchDTO(Builder builder) {
        setId(builder.id);
        setTeamBlue(builder.teamBlue);
        setTeamRed(builder.teamRed);
        setDatumtijd(builder.datumtijd);
        setSpelers(builder.spelers);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TeamDTO getTeamBlue() {
        return teamBlue;
    }

    public void setTeamBlue(TeamDTO teamBlue) {
        this.teamBlue = teamBlue;
    }

    public TeamDTO getTeamRed() {
        return teamRed;
    }

    public void setTeamRed(TeamDTO teamRed) {
        this.teamRed = teamRed;
    }

    public Date getDatumtijd() {
        return datumtijd;
    }

    public void setDatumtijd(Date datumtijd) {
        this.datumtijd = datumtijd;
    }

    public Set<SpelerMatchDTO> getSpelers() {
        return spelers;
    }

    public void setSpelers(Set<SpelerMatchDTO> spelers) {
        this.spelers = spelers;
    }

    public static final class Builder {
        private long id;
        private TeamDTO teamBlue;
        private TeamDTO teamRed;
        private Date datumtijd;
        private Set<SpelerMatchDTO> spelers;



        public Builder() {
        }

        public Builder(CreateMatchDTO copy) {
            this.id = copy.getId();
            this.teamBlue = copy.getTeamBlue();
            this.teamRed = copy.getTeamRed();
            this.datumtijd = copy.getDatumtijd();
            this.spelers = copy.getSpelers();
        }

        public Builder teamBlue(TeamDTO val) {
            teamBlue = val;
            return this;
        }

        public Builder teamRed(TeamDTO val) {
            teamRed = val;
            return this;
        }

        public Builder datumtijd(Date val) {
            datumtijd = val;
            return this;
        }

        public Builder spelers(Set<SpelerMatchDTO> val){
            spelers = val;
            return this;
        }

        public CreateMatchDTO build() {
            return new CreateMatchDTO(this);
        }
    }
}

