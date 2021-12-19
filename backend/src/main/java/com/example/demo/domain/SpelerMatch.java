package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

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

    @Column(name = "TEAM_ID")
    private long teamid;

    private SpelerMatch(Builder builder) {
        setId(builder.id);
        setMatch(builder.match);
        setSpeler(builder.speler);
        setTeamid(builder.teamid);
    }

    public SpelerMatch() {

    }

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

    public long getTeamid() {
        return teamid;
    }

    public void setTeamid(long teamid) {
        this.teamid = teamid;
    }


    @Override
    public String toString() {
        return "SpelerMatch{" +
                "id=" + id +
                ", speler=" + speler +
                ", match=" + match +
                ", teamid=" + teamid +
                '}';
    }

    public static final class Builder {
        private long id;
        private Speler speler;
        private Match match;
        private long teamid;

        public Builder(){

        }

        public Builder(SpelerMatch copy){
            this.id = copy.getId();
            this.speler = copy.getSpeler();
            this.match = copy.getMatch();
            this.teamid = copy.getTeamid();
        }

        public Builder id(long val){
            id = val;
            return this;
        }

        public Builder speler(Speler val){
            speler = val;
            return this;
        }

        public Builder match(Match val){
            match = val;
            return this;
        }

        public Builder teamid(long val){
            teamid = val;
            return this;
        }

        public SpelerMatch build(){return new SpelerMatch(this);}
    }
}
