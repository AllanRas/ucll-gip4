package com.example.demo.dao;

import com.example.demo.domain.Match;
import com.example.demo.domain.Speler;
import com.example.demo.domain.SpelerMatch;
import com.example.demo.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
    Optional<List<Match>> findByTeamBlueOrTeamRed(Team team, Team team1);
    Optional<Match> findBySpelersContaining(SpelerMatch spelerMatch);
}
