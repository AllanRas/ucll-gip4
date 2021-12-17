package com.example.demo.web;


import com.example.demo.AbstractIntegrationTest;
import com.example.demo.Converter.SpelerConverter;
import com.example.demo.Services.ManagerService;
import com.example.demo.Services.SpelerService;
import com.example.demo.Services.TeamService;
import com.example.demo.dao.SpelerRepository;
import com.example.demo.domain.SpelerTeam;
import com.example.demo.domain.Team;
import com.example.demo.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class TeamResourceTest extends AbstractIntegrationTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private ManagerService managerService;

    @Autowired
    private SpelerService spelerService;

    @Autowired
    private TeamService teamService;

    private MockMvc mockMvc;

    private ManagerDTO jefManager;
    private CreateSpelerDTO josPatat;


    @BeforeEach
    void setUp() throws Exception {

        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
                .apply(springSecurity())
                .build();

        jefManager = new ManagerDTO.Builder()
                .passwoord("password")
                .userDTO(new UserDTO.Builder()
                        .voornaam("Jef")
                        .achternaam("De Manager")
                        .username("JefkeM")
                        .email("JefkeM@gmail.com").build())
                .build();

        josPatat = new CreateSpelerDTO.Builder()
                .actief(true)
                .geboortedatum(new SimpleDateFormat("yyyy-MM-dd").parse("1990-05-11"))
                .password("password")
                .user(new UserDTO.Builder()
                        .voornaam("Jos")
                        .achternaam("Patat")
                        .username("JPatat")
                        .role("SPELER")
                        .email("Jpatat@gmail.com").build())
                .adres(new AdresDTO.Builder()
                        .gemeente("Leuven")
                        .straat("straat in leuven")
                        .huisnummer("42")
                        .postcode("3000")
                        .build())
                .build();
    }

    @Test
    void createTeam() throws Exception {
        // Given
        ManagerDTO managerDTO = managerService.createManager(jefManager);

        TeamDTO teamDTO = new TeamDTO.Builder()
                .naam("teamtest")
                .build();

        System.out.println(managerDTO.toString());
        System.out.println(teamDTO.toString());

        // When
        ResultActions perform = this.mockMvc.perform(MockMvcRequestBuilders.post("/teams")
                .with(httpBasic(jefManager.getUserDTO().getUsername(),jefManager.getPasswoord()))
                .content(toJson(teamDTO))
                .contentType(MediaType.APPLICATION_JSON));
        MvcResult result = perform
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists()).andReturn();

        TeamDTO gemaaktTeam = fromMvcResult(result, TeamDTO.class);

        // Then
        // check user equals
        assertEquals(gemaaktTeam.getNaam(), teamDTO.getNaam());
    }

    @Test
    void changeTeamNaam() throws Exception {
        // Given
        ManagerDTO managerDTO = managerService.createManager(jefManager);

        CreateTeamDTO createTeamDTO = new CreateTeamDTO.Builder()
                .naam("voor update")
                .build();

        CreateTeamDTO team = teamService.createTeam(createTeamDTO, managerDTO.getId());

        String naupdate = "na update";

        ResultActions perform = this.mockMvc.perform(MockMvcRequestBuilders.put("/teams/{id}/update/{teamNaam}", team.getId(),naupdate)
                .with(httpBasic(jefManager.getUserDTO().getUsername(),jefManager.getPasswoord()))
        );

        MvcResult result = perform
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists()).andReturn();

        TeamDTO updatedTeam = fromMvcResult(result, TeamDTO.class);

        assertEquals(updatedTeam.getNaam(),naupdate);

    }

    @Test
    void addSpelerToTeam() throws Exception{
        // Given
        SpelerDTO josPatatCreated = spelerService.createSpeler(josPatat);
        ManagerDTO managerDTO = managerService.createManager(jefManager);

        CreateTeamDTO team = new CreateTeamDTO.Builder()
                .naam("testnaam2")
                .managerDTO(jefManager)
                .build();

        CreateTeamDTO createdTeam = teamService.createTeam(team,managerDTO.getId());

        //When
        ResultActions perform = this.mockMvc.perform(MockMvcRequestBuilders.post("/teams/{teamId}/AddSpeler/{spelerId}/{reserve}",createdTeam.getId(), josPatatCreated.getId(), false)
                .with(httpBasic(jefManager.getUserDTO().getUsername(),jefManager.getPasswoord())));

        MvcResult result = perform
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id").exists()).andReturn();

        Team teamResponse = fromMvcResult(result,Team.class);

    }

    @Test
    void getAllTeams() throws Exception{
        SpelerDTO josPatatCreated = spelerService.createSpeler(josPatat);
        ManagerDTO managerDTO = managerService.createManager(jefManager);

        // manager auth return 400 : Ok
        this.mockMvc.perform(MockMvcRequestBuilders.get("/teams")
                        .with(httpBasic(josPatatCreated.getUserDTO().getUsername(),josPatat.getPassword()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // speler auth returns 403 : Forbidden
        this.mockMvc.perform(MockMvcRequestBuilders.get("/teams")
                        .with(httpBasic(managerDTO.getUserDTO().getUsername(), jefManager.getPasswoord()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // foute auth returns 401 : Unauthorized
        this.mockMvc.perform(MockMvcRequestBuilders.get("/teams")
                        .with(httpBasic("",""))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getTeamById() throws Exception {

        // Given
        ManagerDTO managerDTO = managerService.createManager(jefManager);

        CreateTeamDTO team = new CreateTeamDTO.Builder()
                .naam("testnaam2")
                .managerDTO(jefManager)
                .build();

        CreateTeamDTO createdTeam = teamService.createTeam(team,managerDTO.getId());

        //When
        ResultActions perform = this.mockMvc.perform(MockMvcRequestBuilders.get("/teams/{Id}/getOne",createdTeam.getId())
                .with(httpBasic(jefManager.getUserDTO().getUsername(),jefManager.getPasswoord())));

        //then
        MvcResult result = perform
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id").exists()).andReturn();

        TeamDTO teamResponse = fromMvcResult(result,TeamDTO.class);

        assertEquals(teamResponse.getNaam(), createdTeam.getNaam());
        assertEquals(teamResponse.getId(), createdTeam.getId());
    }

    @Test
    void deleteSpelerFromTeam() throws Exception{
        // Given
        SpelerDTO josPatatCreated = spelerService.createSpeler(josPatat);
        ManagerDTO managerDTO = managerService.createManager(jefManager);

        CreateTeamDTO team = new CreateTeamDTO.Builder()
                .naam("testnaam2")
                .managerDTO(jefManager)
                .build();

        CreateTeamDTO createdTeam = teamService.createTeam(team,managerDTO.getId());
        teamService.addSpelerToTeam(josPatatCreated.getId(), createdTeam.getId(),false, managerDTO.getId());

        //When
        ResultActions perform = this.mockMvc.perform(MockMvcRequestBuilders.delete("/teams/{teamId}/Delete/{spelerId}",createdTeam.getId(), josPatatCreated.getId())
                .with(httpBasic(jefManager.getUserDTO().getUsername(),jefManager.getPasswoord())));

        //Then
        perform.andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void reservePromoveren() throws Exception {
        //Given
        SpelerDTO josPatatCreated = spelerService.createSpeler(josPatat);
        ManagerDTO managerDTO = managerService.createManager(jefManager);

        CreateTeamDTO team = new CreateTeamDTO.Builder()
                .naam("testnaam2")
                .managerDTO(jefManager)
                .build();


        CreateTeamDTO createdTeam = teamService.createTeam(team,managerDTO.getId());
        teamService.addSpelerToTeam(josPatatCreated.getId(), createdTeam.getId(),true, managerDTO.getId());

        //When
        ResultActions perform = this.mockMvc.perform(MockMvcRequestBuilders.put("/teams/{teamId}/ReservePromoveren/{spelerId}",createdTeam.getId(), josPatatCreated.getId())
                .with(httpBasic(jefManager.getUserDTO().getUsername(),jefManager.getPasswoord())));

        //Then
        MvcResult result = perform
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists()).andReturn();

        SpelerTeam response = fromMvcResult(result,SpelerTeam.class);

        assertFalse(response.isReserve());
    }

}
