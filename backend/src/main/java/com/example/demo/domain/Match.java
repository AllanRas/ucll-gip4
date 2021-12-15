package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="Match", schema = "esportmanagerdb")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="TEAM_BLUE_ID")
    private Team teamBlue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="TEAM_RED_ID")
    private Team teamRed;

    @Column(name = "TEAM_BLUE_SCORE")
    private int scoreBlueTeam;

    @Column(name = "TEAM_RED_SCORE")
    private int scoreRedTeam;

    @Column(name = "DATUMTIJD")
    private Date datumtijd;


    @OneToMany(mappedBy = "match")
    @JsonManagedReference(value = "MatchSpelers")
    private Set<SpelerMatch> spelers = new HashSet<>();
    @OneToMany
    private Set<Speler> spelers = new HashSet<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Team getTeamBlue() {
        return teamBlue;
    }

    public void setTeamBlue(Team teamBlue) {
        this.teamBlue = teamBlue;
    }

    public Team getTeamRed() {
        return teamRed;
    }

    public void setTeamRed(Team teamRed) {
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

    public Set<Speler> getSpelers() {
        return spelers;
    }

    public void setSpelers(Set<Speler> spelers) {
        this.spelers = spelers;
    }
}
