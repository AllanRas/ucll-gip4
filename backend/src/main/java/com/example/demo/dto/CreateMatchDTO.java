package com.example.demo.dto;

import com.example.demo.domain.Speler;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class CreateMatchDTO implements Serializable {

    private long id;
    private long teamBlueId;
    private long teamRedId;
    private Date datumtijd;
    public Set<SpelerMatchDTO> spelers;

    public CreateMatchDTO() {
    }

    private CreateMatchDTO(Builder builder) {
        setId(builder.id);
        setTeamBlueId(builder.teamBlueId);
        setTeamRedId(builder.teamRedId);
        setDatumtijd(builder.datumtijd);
        setSpelers(builder.spelers);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTeamBlueId() {
        return teamBlueId;
    }

    public void setTeamBlueId(long teamBlueId) {
        this.teamBlueId = teamBlueId;
    }

    public long getTeamRedId() {
        return teamRedId;
    }

    public void setTeamRedId(long teamRedId) {
        this.teamRedId = teamRedId;
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
        private long teamBlueId;
        private long teamRedId;
        private Date datumtijd;
        private Set<SpelerMatchDTO> spelers;

        public Builder() {
        }

        public Builder(CreateMatchDTO copy) {
            this.id = copy.getId();
            this.teamBlueId = copy.getTeamBlueId();
            this.teamRedId = copy.getTeamRedId();
            this.datumtijd = copy.getDatumtijd();
            this.spelers = copy.getSpelers();
        }

        public Builder teamBlue(long val) {
            teamBlueId = val;
            return this;
        }

        public Builder teamRed(long val) {
            teamRedId = val;
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

