package com.example.demo.dao;
import com.example.demo.domain.SpelerTeam;
import com.example.demo.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    Optional<Team> findBySpelersContains(SpelerTeam spelerTeam);
}
