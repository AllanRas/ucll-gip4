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

    @OneToMany
    private Set<SpelerTeam> spelers = new HashSet<>();

    @OneToMany
    private Set<ReserveSpelerTeam> reservespelers = new HashSet<>();

    @OneToMany
    private Set<Match> matches = new HashSet<>();

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
        setReservespelers(builder.reservespelers);
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

    public Set<ReserveSpelerTeam> getReservespelers() {
        return reservespelers;
    }

    public void setReservespelers(Set<ReserveSpelerTeam> reservespelers) {
        this.reservespelers = reservespelers;
    }

    public Set<Match> getMatches() {
        return matches;
    }

    public void setMatches(Set<Match> matches) {
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
                ", reservespelers=" + reservespelers +
                ", matches=" + matches +
                ", manager=" + manager +
                ", actief=" + actief +
                '}';
    }

    public static final class Builder {
        private long id;
        private String naam;
        private Set<SpelerTeam> spelers = new HashSet<>();
        private Set<ReserveSpelerTeam> reservespelers = new HashSet<>();
        private Set<Match> matches = new HashSet<>();
        private Manager manager;
        private boolean actief;

        public Builder(){

        }

        public Builder(Team copy){
            this.id = copy.getId();
            this.naam = copy.getNaam();
            this.spelers = copy.getSpelers();
            this.reservespelers = copy.getReservespelers();
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

        public Builder reservespelers(Set<ReserveSpelerTeam> val){
            reservespelers = val;
            return this;
        }

        public Builder matches(Set<Match> val){
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
