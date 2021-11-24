package com.example.demo.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="Match", schema = "esportmanagerdb")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="TEAM_BLUE_ID")
    private Team teamBlue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="TEAM_RED_ID")
    private Team teamRed;

    @Column(name = "TEAM_BLUE_SCORE")
    private int scoreBlueTeam;

    @Column(name = "TEAM_RED_SCORE")
    private int scoreRedTeam;

    @OneToMany
    private Set<SpelerMatch> spelers = new HashSet<>();
}
