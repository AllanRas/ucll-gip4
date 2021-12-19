package com.example.demo.dao;

import com.example.demo.domain.Speler;
import com.example.demo.domain.SpelerTeam;
import com.example.demo.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SpelerTeamRepository extends JpaRepository<SpelerTeam, Long> {
    Optional<SpelerTeam> findBySpelerAndTeam(Speler speler, Team team);
    Optional<SpelerTeam> findBySpelerIdAndTeamId(long spelerId, long teamId);
    Optional<List<SpelerTeam>> findBySpeler(Speler speler);

    Optional<List<SpelerTeam>> findByTeam(Team team);
}
