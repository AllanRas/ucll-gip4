package com.example.demo.domain;

import javax.persistence.*;

@Entity
@Table(name = "ReserveSpelerTeam", schema = "esportmanagerdb")
public class ReserveSpelerTeam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SPELER_ID")
    private Speler reservespeler;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID")
    private Team team;

}
