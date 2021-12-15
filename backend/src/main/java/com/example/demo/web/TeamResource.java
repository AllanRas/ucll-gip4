package com.example.demo.web;


import com.example.demo.Services.ManagerService;
import com.example.demo.Services.TeamService;
import com.example.demo.config.UserPrincipal;
import com.example.demo.domain.SpelerTeam;
import com.example.demo.domain.Team;
import com.example.demo.dto.CreateTeamDTO;
import com.example.demo.dto.ManagerDTO;
import com.example.demo.dto.TeamDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/teams")
@CrossOrigin("http://localhost:3000/")
public class TeamResource {

    private final TeamService teamService;
    private final ManagerService managerService;

    public TeamResource(TeamService teamService, ManagerService managerService) {
        this.teamService = teamService;
        this.managerService = managerService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public CreateTeamDTO createTeam(@RequestBody CreateTeamDTO createteamDTO){

        // Get manager from authentication
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ManagerDTO managerDTO = managerService.getManagerByUserId(userPrincipal.getUser());

        return teamService.createTeam(createteamDTO, managerDTO.getId());
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping("/{teamId}/AddSpeler/{spelerId}/{reserve}")
    public ResponseEntity<Team> addSpelerToTeam(@PathVariable("spelerId") long spelerId, @PathVariable("teamId") long teamId, @PathVariable("reserve") boolean reserve ){

        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ManagerDTO managerDTO = managerService.getManagerByUserId(userPrincipal.getUser());

        Team team = teamService.addSpelerToTeam(spelerId,teamId,reserve,managerDTO.getId());

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(team);
    }

    @PreAuthorize("hasAnyRole('MANAGER','SPELER')")
    @GetMapping
    public List<TeamDTO> getAllTeams(){
        return teamService.getAllTeams();
    }

    @Transactional
    @PreAuthorize("hasAnyRole('MANAGER','SPELER')")
    @GetMapping(value = "/{id}/getOne")
    public ResponseEntity<TeamDTO> getById(@PathVariable("id") long id){
        return new ResponseEntity<TeamDTO>(teamService.getTeamById(id), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @DeleteMapping("/{teamId}/Delete/{spelerId}")
    public ResponseEntity removeSpelerFromTeam(@PathVariable("spelerId") long spelerId, @PathVariable("teamId") long teamId){
        teamService.removeSpelerFromTeam(spelerId,teamId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/{teamId}/ReservePromoveren/{spelerId}")
    public ResponseEntity<SpelerTeam> reservePromoveren(@PathVariable("spelerId") long spelerId, @PathVariable("teamId") long teamId){
        return ResponseEntity.status(HttpStatus.OK).body(teamService.reservePromoveren(spelerId,teamId));
    }
}