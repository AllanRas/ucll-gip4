package com.example.demo.dto.match;

import com.example.demo.dto.SpelerMatchDTO;
import com.example.demo.dto.TeamDTO;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class MatchStatsDTO implements Serializable {

    private TeamDTO teamBlue;
    private TeamDTO teamRed;
    private int scoreBlueTeam;
    private int scoreRedTeam;
    private Date datumtijd;
    private Set<SpelerMatchDTO> spelers;

    public MatchStatsDTO() {
    }

    private MatchStatsDTO(Builder builder) {
        setTeamBlue(builder.teamBlue);
        setTeamRed(builder.teamRed);
        setScoreBlueTeam(builder.scoreBlueTeam);
        setScoreRedTeam(builder.scoreRedTeam);
        setDatumtijd(builder.datumtijd);
        setSpelers(builder.spelers);
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

    public int getScoreBlueTeam() {
        return scoreBlueTeam;
    }

    public void setScoreBlueTeam(int scoreBlueTeam) {
        this.scoreBlueTeam = scoreBlueTeam;
    }

    public int getScoreRedTeam() {
        return scoreRedTeam;
    }

    public void setScoreRedTeam(int scoreRedTeam) {
        this.scoreRedTeam = scoreRedTeam;
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
        private TeamDTO teamBlue;
        private TeamDTO teamRed;
        private int scoreBlueTeam;
        private int scoreRedTeam;
        private Date datumtijd;
        private Set<SpelerMatchDTO> spelers;

        public Builder() {
        }

        public Builder teamBlue(TeamDTO val) {
            teamBlue = val;
            return this;
        }

        public Builder teamRed(TeamDTO val) {
            teamRed = val;
            return this;
        }

        public Builder scoreBlueTeam(int val) {
            scoreBlueTeam = val;
            return this;
        }

        public Builder scoreRedTeam(int val) {
            scoreRedTeam = val;
            return this;
        }

        public Builder datumtijd(Date val) {
            datumtijd = val;
            return this;
        }

        public Builder spelers(Set<SpelerMatchDTO> val) {
            spelers = val;
            return this;
        }

        public MatchStatsDTO build() {
            return new MatchStatsDTO(this);
        }
    }
}
