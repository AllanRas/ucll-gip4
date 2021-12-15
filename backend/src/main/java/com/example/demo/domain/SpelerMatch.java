package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;

@Entity
@Table(name = "SpelerMatch", schema = "esportmanagerdb")
public class SpelerMatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SPELER_ID")
    @JsonBackReference(value = "SpelerMatches")
    private Speler speler;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MATCH_ID")
    @JsonBackReference(value = "MatchSpelers")
    private Match match;

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

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    @Override
    public String toString() {
        return "SpelerMatch{" +
                "id=" + id +
                ", speler=" + speler +
                ", match=" + match +
                '}';
    }
}
