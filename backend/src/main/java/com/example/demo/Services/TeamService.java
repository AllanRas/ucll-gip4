package com.example.demo.Services;

import com.example.demo.Converter.ManagerConverter;
import com.example.demo.Converter.TeamConverter;
import com.example.demo.config.UserPrincipal;
import com.example.demo.dao.ManagerRepository;
import com.example.demo.dao.SpelerRepository;
import com.example.demo.dao.SpelerTeamRepository;
import com.example.demo.dao.TeamRepository;
import com.example.demo.domain.*;
import com.example.demo.dto.CreateTeamDTO;
import com.example.demo.dto.SpelerTeamDTO;
import com.example.demo.dto.TeamDTO;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.util.Optional;

import java.util.ArrayList;
import java.util.List;


@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final ManagerRepository managerRepository;
    private final SpelerRepository spelerRepository;
    private final SpelerTeamRepository spelerTeamRepository;

    private final TeamConverter teamConverter;
    private final ManagerConverter managerConverter;

    public TeamService(TeamRepository teamRepository, ManagerRepository managerRepository, SpelerRepository spelerRepository, SpelerTeamRepository spelerTeamRepository, TeamConverter teamConverter, ManagerConverter managerConverter) {
        this.teamRepository = teamRepository;
        this.managerRepository = managerRepository;
        this.spelerRepository = spelerRepository;
        this.spelerTeamRepository = spelerTeamRepository;
        this.teamConverter = teamConverter;
        this.managerConverter = managerConverter;
    }

    public CreateTeamDTO createTeam(CreateTeamDTO createTeamDTO, long managerId){

        Manager manager = managerRepository.findById(managerId).orElseThrow();

        System.out.println(manager.getUser().getVoornaam() + " " + manager.getUser().getAchternaam());

        createTeamDTO.setManagerDTO(managerConverter.managerDTO(manager));
        createTeamDTO.setActief(true);

        Team team = teamConverter.createDTOtoTeam(createTeamDTO);

        teamRepository.save(team);
        return teamConverter.createTeamToDTO(team);
    }

    public Team addSpelerToTeam(long spelerId, long teamId, boolean reserve, long managerId){
        Team team = teamRepository.findById(teamId).orElseThrow();
        Speler speler = spelerRepository.findById(spelerId).orElseThrow();

        boolean exists =  spelerTeamRepository.findBySpelerAndTeam(speler,team).isPresent();

        if(team.getManager().getId() != managerId || exists){
            return null;
        }

        SpelerTeam spelerTeam = new SpelerTeam();
        spelerTeam.setTeam(team);
        spelerTeam.setSpeler(speler);
        spelerTeam.setReserve(reserve);

        speler.getTeams().add(spelerTeam);
        team.getSpelers().add(spelerTeam);

        spelerTeamRepository.save(spelerTeam);
        return teamRepository.getById(teamId);
    }


    public TeamDTO updateTeamNaam(long id, TeamDTO teamDTO){

        Optional<Team> team = teamRepository.findById(id);

        if(team.isPresent()){
            Team updateTeamNaam = teamConverter.DTOtoTeam(teamDTO);
            Team newTeam = team.get();

            // Team.Naam update
            newTeam.setNaam(updateTeamNaam.getNaam());

            //Team update
            teamRepository.save(newTeam);
        }
        return teamConverter.teamToDTO(team.orElseThrow());

    public List<TeamDTO> getAllTeams(){
        return teamConverter.teamListToDTO(teamRepository.findAll());
    }

    public List<TeamDTO> getAllTeamsBySpeler(UserPrincipal userPrincipal){
            Speler speler = spelerRepository.findByUser(userPrincipal.getUser()).orElseThrow();
            List<SpelerTeam> spelerTeams = spelerTeamRepository.findBySpeler(speler).orElseThrow();
            List<Team> teamList = new ArrayList<>();
            for (SpelerTeam sp: spelerTeams) {
                teamList.add(teamRepository.findBySpelersContains(sp).orElseThrow());
            }
        return teamConverter.teamListToDTO(teamList);
    }

    public TeamDTO getTeamById(long id){
        return teamConverter.teamToDTO(teamRepository.getById(id));

    }

    public void removeSpelerFromTeam(long spelerId, long teamId){
        SpelerTeam spelerTeam = spelerTeamRepository.findBySpelerIdAndTeamId(spelerId,teamId).orElseThrow();
        spelerTeamRepository.delete(spelerTeam);
    }

    public SpelerTeam reservePromoveren(long spelerId, long teamId){
        SpelerTeam spelerTeam = spelerTeamRepository.findBySpelerIdAndTeamId(spelerId,teamId).orElseThrow();
        spelerTeam.setReserve(!spelerTeam.isReserve());
        return spelerTeamRepository.save(spelerTeam);
    }
}
