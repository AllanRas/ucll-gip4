package com.example.demo.domain;

import javax.persistence.*;

@Entity
@Table(name = "SpelerMatch", schema = "esportmanagerdb")
public class SpelerMatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SPELER_ID")
    private Speler speler;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MATCH_ID")
    private Match match;
}
