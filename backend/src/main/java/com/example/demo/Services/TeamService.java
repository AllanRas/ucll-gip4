package com.example.demo.Services;
import com.example.demo.Converter.ManagerConverter;
import com.example.demo.Converter.TeamConverter;
import com.example.demo.dao.ManagerRepository;
import com.example.demo.dao.SpelerRepository;
import com.example.demo.dao.SpelerTeamRepository;
import com.example.demo.dao.TeamRepository;
import com.example.demo.domain.*;
import com.example.demo.dto.CreateTeamDTO;
import com.example.demo.dto.TeamDTO;
import org.springframework.stereotype.Service;

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

        System.out.println(team.toString());
        teamRepository.save(team);
        return teamConverter.createTeamToDTO(team);
    }

    public SpelerTeam addSpelerToTeam(long spelerId, long teamId, boolean reserve, long managerId){
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

        return spelerTeamRepository.save(spelerTeam);
    }

    public List<TeamDTO> getAllTeams(){
        return teamConverter.teamListToDTO(teamRepository.findAll());
    }

    public TeamDTO getTeamById(long id){
        return teamConverter.teamToDTO(teamRepository.getById(id));
    }
}
