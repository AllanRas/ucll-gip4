package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="Team", schema = "esportmanagerdb")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "NAAM")
    private String naam;

    @OneToMany(mappedBy = "team")
    @JsonManagedReference(value = "TeamSpeler")
    private Set<SpelerTeam> spelers = new HashSet<>();

    @OneToMany(mappedBy = "teamBlue")
    private Set<Match> matchesteamBlue = new HashSet<>();

    @OneToMany(mappedBy = "teamRed")
    private Set<Match> matchesteamRed = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "MANAGER_ID")
    @JsonBackReference("ManagerTeams")
    private Manager manager;

    @Column(name = "ACTIEF")
    private boolean actief;

    public Team() {

    }

    private Team(Builder builder){
        setId(builder.id);
        setNaam(builder.naam);
        setSpelers(builder.spelers);
        setMatchesteamBlue(builder.matchesteamBlue);
        setMatchesteamRed(builder.matchesteamRed);
        setManager(builder.manager);
        setActief(builder.actief);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public Set<SpelerTeam> getSpelers() {
        return spelers;
    }

    public void setSpelers(Set<SpelerTeam> spelers) {
        this.spelers = spelers;
    }

    public Set<Match> getMatchesteamBlue() {
        return matchesteamBlue;
    }

    public void setMatchesteamBlue(Set<Match> matchesteamBlue) {
        this.matchesteamBlue = matchesteamBlue;
    }

    public Set<Match> getMatchesteamRed() {
        return matchesteamRed;
    }

    public void setMatchesteamRed(Set<Match> matchesteamRed) {
        this.matchesteamRed = matchesteamRed;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public boolean isActief() {
        return actief;
    }

    public void setActief(boolean actief) {
        this.actief = actief;
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", naam='" + naam + '\'' +
                ", spelers=" + spelers +
                ", matchesteamBlue=" + matchesteamBlue +
                ", matchesteamRed=" + matchesteamRed +
                ", manager=" + manager +
                ", actief=" + actief +
                '}';
    }

    public static final class Builder {
        private long id;
        private String naam;
        private Set<SpelerTeam> spelers = new HashSet<>();
        private Set<Match> matchesteamBlue = new HashSet<>();
        private Set<Match> matchesteamRed = new HashSet<>();
        private Manager manager;
        private boolean actief;

        public Builder(){

        }

        public Builder(Team copy){
            this.id = copy.getId();
            this.naam = copy.getNaam();
            this.spelers = copy.getSpelers();
            this.matchesteamBlue = copy.getMatchesteamBlue();
            this.matchesteamRed = copy.getMatchesteamRed();
            this.manager = copy.getManager();
            this.actief = copy.isActief();
        }

        public Builder id(long val){
            id = val;
            return this;
        }

        public Builder naam(String val){
            naam = val;
            return this;
        }

        public Builder spelers(Set<SpelerTeam> val){
            spelers = val;
            return this;
        }

        public Builder matchesteamBlue(Set<Match> val){
            matchesteamBlue = val;
            return this;
        }

        public Builder matchesteamRed(Set<Match> val){
            matchesteamRed = val;
            return this;
        }

        public Builder manager(Manager val){
            manager = val;
            return this;
        }

        public Builder actief(boolean val){
            actief = val;
            return this;
        }

        public Team build(){return new Team(this);}
    }
}
