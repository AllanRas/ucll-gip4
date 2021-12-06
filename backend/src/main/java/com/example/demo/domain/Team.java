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
}
