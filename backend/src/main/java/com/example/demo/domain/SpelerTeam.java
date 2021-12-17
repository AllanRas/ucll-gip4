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

    public SpelerTeam(){

    }

    public SpelerTeam(Builder builder){
        setId(builder.id);
        setSpeler(builder.speler);
        setTeam(builder.team);
        setReserve(builder.reserve);
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

    public static final class Builder {
        private long id;
        private Speler speler;
        private Team team;
        private boolean reserve;

        public Builder(){

        }

        public Builder(SpelerTeam copy){
            this.id = copy.getId();
            this.speler = copy.getSpeler();
            this.team = copy.getTeam();
            this.reserve = copy.isReserve();
        }

        public Builder id(long val){
            id = val;
            return this;
        }

        public Builder speler(Speler val){
            speler = val;
            return this;
        }

        public Builder team(Team val){
            team = val;
            return this;
        }

        public Builder reserve(boolean val){
            reserve = val;
            return this;
        }

        public SpelerTeam build(){return new SpelerTeam(this);}
    }
}
