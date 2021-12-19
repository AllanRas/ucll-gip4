package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Manager", schema = "esportmanagerdb")
public class Manager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="USER_ID")
    private User user;

    @OneToMany(mappedBy = "manager")
    @JsonManagedReference("ManagerTeams")
    private Set<Team> teams = new HashSet<>();

    public Manager(){

    }

    public Manager(Builder builder){
        setId(builder.id);
        setUser(builder.user);
        setTeams(builder.teams);
    }

    @Override
    public String toString() {
        return "Manager{" +
                "id=" + id +
                ", user=" + user +
                ", teams=" + teams +
                '}';
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

    public Set<Team> getTeams() {
        return teams;
    }

    public void setTeams(Set<Team> teams) {
        this.teams = teams;
    }

    public static final class Builder {
        private long id;
        private User user;
        private Set<Team> teams = new HashSet<>();

        public Builder(){

        }

        public Builder(Manager copy){
            this.id = copy.getId();
            this.user = copy.getUser();
            this.teams = copy.getTeams();
        }

        public Builder id(Long val){
            id = val;
            return this;
        }

        public Builder user(User val){
            user = val;
            return this;
        }

        public Builder teams(Set<Team> val)    {
            teams = val;
            return this;
        }

        public Manager build(){return new Manager(this);}
    }

}
