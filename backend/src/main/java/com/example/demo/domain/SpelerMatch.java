package com.example.demo.domain;

import com.example.demo.dto.SpelerMatchDTO;
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

    private SpelerMatch(Builder builder) {
        setId(builder.id);
        setMatch(builder.match);
        setSpeler(builder.speler);
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

    @Override
    public String toString() {
        return "SpelerMatch{" +
                "id=" + id +
                ", speler=" + speler +
                ", match=" + match +
                '}';
    }

    public static final class Builder {
        private long id;
        private Speler speler;
        private Match match;

        public Builder(){

        }

        public Builder(SpelerMatch copy){
            this.id = copy.getId();
            this.speler = copy.getSpeler();
            this.match = copy.getMatch();
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

        public SpelerMatch build(){return new SpelerMatch(this);}
    }
}
