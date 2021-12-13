package com.example.demo.dto;

import com.example.demo.domain.Speler;

import java.util.Date;
import java.util.Set;

public class CreateMatchDTO {
    private TeamDTO teamBlue;
    private TeamDTO teamRed;
    private Date datumtijd;
    private Set<Speler> spelers;

    public CreateMatchDTO() {
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

    public Set<Speler> getSpelers() {
        return spelers;
    }

    public void setSpelers(Set<Speler> spelers) {
        this.spelers = spelers;
    }
}

