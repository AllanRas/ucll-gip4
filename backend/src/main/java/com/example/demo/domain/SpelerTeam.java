package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;

@Entity
@Table(name = "SpelerTeam", schema = "esportmanagerdb")
public class SpelerTeam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SPELER_ID")
    @JsonBackReference(value = "SpelerTeam")
    private Speler speler;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID")
    @JsonBackReference(value = "TeamSpeler")
    private Team team;

    @Column(name = "Reserve")
    private boolean reserve;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Speler getSpeler() {
        return speler;
    }

    public void setSpeler(Speler speler) {
        this.speler = speler;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public boolean isReserve() {
        return reserve;
    }

    public void setReserve(boolean reserve) {
        this.reserve = reserve;
    }

    @Override
    public String toString() {
        return "SpelerTeam{" +
                "id=" + id +
                ", speler=" + speler +
                ", team=" + team +
                ", reserve=" + reserve +
                '}';
    }
}
