package com.example.demo.domain;
import javax.persistence.*;

@Entity
@Table(name="Team", schema="esportmanagerdb")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long teamid;

    @Column(name = "TEAMNAAM")
    private String teamnaam;

    @Column(name = "Manager")
    private String manager;

    @Column(name = "Match")
    private String match;

    @Column(name = "Spelers")
    private String speler;

    @Column(name = "ReserveSpeler")
    private String reserveSpeler;


    public Team(){}

    private Team(Builder builder){
        setTeamid(builder.teamid);
        setTeamnaam(builder.teamnaam);
        setManager(builder.manager);
        setMatch(builder.match);
        setSpeler(builder.speler);
        setReserveSpeler(builder.reserveSpeler);

    }

    public long getTeamid() {return teamid;}

    public void setTeamid(long teamid) {this.teamid = teamid;}

    public void setTeamnaam(String teamnaam) {this.teamnaam = teamnaam;}

    public String getTeamnaam() {return teamnaam;}

    public String getManager() {return manager;}

    public void setManager(String manager) {this.manager = manager;}

    public String getMatch() {return match;}

    public void setMatch(String match) {this.match = match;}

    public String getSpeler() {return speler;}

    public void setSpeler(String speler) {this.speler = speler;}

    public String getReserveSpeler() {return reserveSpeler;}

    public void setReserveSpeler(String reserveSpeler) {this.reserveSpeler = reserveSpeler;}

    public static final class Builder {
        private long teamid;
        private String teamnaam;
        private String manager;
        private String speler;
        private String match;
        private String reserveSpeler;


        public Builder(){}

        public Builder(Team copy){
            this.teamid = copy.getTeamid();
            this.teamnaam = copy.getTeamnaam();
            this.manager= copy.getManager();
            this.speler = copy.getSpeler();
            this.match = copy.getMatch();
            this.reserveSpeler = copy.getReserveSpeler();
        }

        public Team.Builder teamid(Long val){
            teamid = val;
            return this;
        }

        public Team.Builder teamnaam(String val){
            teamnaam = val;
            return this;
        }

        public Team.Builder manager(String val){
            manager = val;
            return this;
        }

        public Team.Builder speler(String val){
            speler = val;
            return this;
        }

        public Team.Builder match(String val){
            match = val;
            return this;
        }

        public Team.Builder reserveSpeler(String val){
            reserveSpeler = val;
            return this;
        }

        public Team build(){return new Team(this);}
    }

}
