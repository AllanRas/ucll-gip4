package com.example.demo.domain;

import javax.persistence.*;

@Entity
@Table(name = "SpelerTeam", schema = "esportmanagerdb")
public class SpelerTeam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SPELER_ID")
    private Speler speler;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID")
    private Team team;
}
