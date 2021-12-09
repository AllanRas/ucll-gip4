package com.example.demo.web;


import com.example.demo.AbstractIntegrationTest;
import com.example.demo.Services.ManagerService;
import com.example.demo.Services.SpelerService;
import com.example.demo.Services.TeamService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    private MockMvc mockMvc;

    private ManagerDTO jefManager;

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
        // check user equalsS
        assertEquals(gemaaktTeam.getNaam(), teamDTO.getNaam());

    }

}
