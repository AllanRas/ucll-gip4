package com.example.demo.domain;

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

    @OneToMany(mappedBy = "speler")
    private Set<SpelerTeam> spelers = new HashSet<>();

    @OneToMany(mappedBy = "speler")
    private Set<SpelerMatch> matches = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "MANAGER_ID")
    private Manager manager;

    @Column(name = "ACTIEF")
    private boolean actief;

    public Team() {

    }

    private Team(Builder builder){
        setId(builder.id);
        setNaam(builder.naam);
        setSpelers(builder.spelers);
        setMatches(builder.matches);
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

    public Set<SpelerMatch> getMatches() {
        return matches;
    }

    public void setMatches(Set<SpelerMatch> matches) {
        this.matches = matches;
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
                ", matches=" + matches +
                ", manager=" + manager +
                ", actief=" + actief +
                '}';
    }

    public static final class Builder {
        private long id;
        private String naam;
        private Set<SpelerTeam> spelers = new HashSet<>();
        private Set<SpelerMatch> matches = new HashSet<>();
        private Manager manager;
        private boolean actief;

        public Builder(){

        }

        public Builder(Team copy){
            this.id = copy.getId();
            this.naam = copy.getNaam();
            this.spelers = copy.getSpelers();
            this.matches = copy.getMatches();
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

        public Builder matches(Set<SpelerMatch> val){
            matches = val;
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
