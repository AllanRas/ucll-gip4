package com.example.demo.web;

import com.example.demo.AbstractIntegrationTest;
import com.example.demo.Services.MatchService;
import com.example.demo.Services.TeamService;
import com.example.demo.domain.Speler;
import com.example.demo.dto.CreateMatchDTO;
import com.example.demo.dto.ManagerDTO;
import com.example.demo.dto.MatchDTO;
import com.example.demo.dto.TeamDTO;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;

import java.text.SimpleDateFormat;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class MatchResourceTest extends AbstractIntegrationTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private MatchService matchService;

    @Autowired
    private TeamService teamService;

    private MockMvc mockMvc;
    private CreateMatchDTO match1;
    private TeamDTO team1;
    private TeamDTO team2;


    @BeforeEach
    void setUp() throws Exception{

        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
                .apply(springSecurity())
                .build();

        match1 = new CreateMatchDTO.Builder()
                .datumtijd(new SimpleDateFormat("yyyy-MM-dd").parse("2021-05-11"))
                .teamBlue(new TeamDTO.Builder()
                        .id(1L)
                        .naam("TestBlue")
                        .build())
                .teamRed(new TeamDTO.Builder()
                        .id(2L)
                        .naam("TestRed")
                        .build())
                .scoreBlueTeam(1)
                .scoreRedTeam(0)
                .build();

    }

    @Test
    void addMatch() throws Exception{
        //Given

        //When
        //Then
    }
}