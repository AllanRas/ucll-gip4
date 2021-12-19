package com.example.demo.Services;

import com.example.demo.Converter.ManagerConverter;
import com.example.demo.Converter.SpelerConverter;
import com.example.demo.Converter.TeamConverter;
import com.example.demo.config.UserPrincipal;
import com.example.demo.dao.ManagerRepository;
import com.example.demo.dao.SpelerRepository;
import com.example.demo.dao.SpelerTeamRepository;
import com.example.demo.dao.TeamRepository;
import com.example.demo.domain.*;
import com.example.demo.dto.CreateTeamDTO;
import com.example.demo.dto.SpelerDTO;
import com.example.demo.dto.TeamDTO;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;


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
    private final SpelerConverter spelerConverter;

    public TeamService(TeamRepository teamRepository, ManagerRepository managerRepository, SpelerRepository spelerRepository, SpelerTeamRepository spelerTeamRepository, TeamConverter teamConverter, ManagerConverter managerConverter, SpelerConverter spelerConverter) {
        this.teamRepository = teamRepository;
        this.managerRepository = managerRepository;
        this.spelerRepository = spelerRepository;
        this.spelerTeamRepository = spelerTeamRepository;
        this.teamConverter = teamConverter;
        this.managerConverter = managerConverter;
        this.spelerConverter = spelerConverter;
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


    public TeamDTO updateTeamNaam(long teamid, String naam, long managerid){
        Team team = teamRepository.findById(teamid).orElseThrow();

        if(team.getManager().getId() != managerid){
            return null;
        }else{
            team.setNaam(naam);
        }
        return teamConverter.teamToDTO(teamRepository.save(team));
    }

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

    public TeamDTO getTeamById(long teamid, UserPrincipal userPrincipal){
        boolean isSpelerRole = userPrincipal.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_SPELER"));

        if(isSpelerRole){
            Speler speler = spelerRepository.findByUser(userPrincipal.getUser()).orElseThrow();
            //checks if speler is in team
            SpelerTeam spelerTeam = spelerTeamRepository.findBySpelerIdAndTeamId(speler.getId(),teamid).orElseThrow();
        }
        return teamConverter.teamToDTO(teamRepository.getById(teamid));
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

    public List<SpelerDTO> getTeamSpelersById(long teamid, UserPrincipal userPrincipal){
        boolean isSpelerRole = userPrincipal.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_SPELER"));

        if(isSpelerRole){
            Speler speler = spelerRepository.findByUser(userPrincipal.getUser()).orElseThrow();
            //checks if speler is in team
            SpelerTeam sp = spelerTeamRepository.findBySpelerIdAndTeamId(speler.getId(),teamid).orElseThrow();

            // put spelers from team in list
            Team team = teamRepository.getById(teamid);
            List<SpelerTeam> spelerteams = spelerTeamRepository.findByTeam(team).orElseThrow();
            List<Speler> spelersInTeam = new ArrayList<>();
            for (SpelerTeam st: spelerteams) {
                spelersInTeam.add(st.getSpeler());
            }
            //return the team list
            return spelerConverter.spelerListToDTO(spelersInTeam);
        }

        return spelerConverter.spelerListToDTO(spelerRepository.findAll());
    }
}
