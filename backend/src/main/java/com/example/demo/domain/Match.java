package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Date;
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

    @Column(name = "DATUMTIJD")
    private Date datumtijd;

    @OneToMany(mappedBy = "match")
    @JsonManagedReference(value = "MatchSpelers")
    private Set<SpelerMatch> spelers = new HashSet<>();
}
