package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import liquibase.pro.packaged.B;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="Speler", schema="esportmanagerdb")
public class Speler {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="USER_ID")
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="ADRES_ID")
    private Adres adres;

    @Column(name = "ACTIEF")
    private boolean actief;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm a z")
    @Column(name = "GEBOORTEDATUM")
    private Date geboortedatum;

    @OneToMany(mappedBy = "speler")
    @JsonManagedReference(value = "SpelerTeam")
    private Set<SpelerTeam> teams = new HashSet<>();
    @OneToMany(mappedBy = "speler")
    @JsonManagedReference(value = "SpelerMatches")
    private Set<SpelerMatch> matches = new HashSet<>();

    public Speler(){}

    private Speler(Builder builder){
        setId(builder.id);
        setUser(builder.user);
        setAdres(builder.adres);
        setActief(builder.actief);
        setGeboortedatum(builder.geboortedatum);
        setTeams(builder.teams);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Adres getAdres() {
        return adres;
    }

    public void setAdres(Adres adres) {
        this.adres = adres;
    }

    public boolean isActief() {
        return actief;
    }

    public void setActief(boolean actief) {
        this.actief = actief;
    }

    public Date getGeboortedatum() {
        return geboortedatum;
    }

    public void setGeboortedatum(Date geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    public Set<SpelerTeam> getTeams() {
        return teams;
    }

    public void setTeams(Set<SpelerTeam> teams) {
        this.teams = teams;
    }

    public Set<SpelerMatch> getMatches() {
        return matches;
    }



    public static final class Builder {
        private long id;
        private User user;
        private Adres adres;
        private boolean actief;
        private Date geboortedatum;
        private Set<SpelerTeam> teams = new HashSet<>();
        private Set<SpelerMatch> match = new HashSet<>();

        public Builder(){}

        public Builder(Speler copy){
            this.id = copy.getId();
            this.user = copy.getUser();
            this.adres = copy.getAdres();
            this.actief = copy.isActief();
            this.geboortedatum = copy.getGeboortedatum();
            this.teams = copy.getTeams();
            this.match = copy.getMatches();
        }

        public Builder user(User val){
            user = val;
            return this;
        }

        public Builder adres(Adres val){
            adres = val;
            return this;
        }

        public Builder id(Long val){
            id = val;
            return this;
        }

        public Builder actief(boolean val){
            actief = val;
            return this;
        }

        public Builder geboortedatum(Date val){
            geboortedatum = val;
            return this;
        }

        public Builder teams(Set<SpelerTeam> val){
            teams = val;
            return this;
        }


        public Speler build(){return new Speler(this);}
    }

}
