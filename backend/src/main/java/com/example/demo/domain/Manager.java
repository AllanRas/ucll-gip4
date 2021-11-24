package com.example.demo.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Manager", schema = "esportmanagerdb")
public class Manager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name="USER_ID")
    private User user;

    @OneToMany
    private Set<Team> teams = new HashSet<>();
}
