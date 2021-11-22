package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.demo.domain.Adres;
import com.example.demo.domain.Speler;
import com.example.demo.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.text.SimpleDateFormat;

public class SpelerResourceTest extends AbstractIntegrationTest{

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    void maaktNieuweSpeler() throws Exception {
        // Given
        Speler josPatat = new Speler.Builder()
                .actief(true)
                .reserve(false)
                .geboortedatum(new SimpleDateFormat("yyyy-MM-dd").parse("1990-05-11"))
                .user(new User.Builder()
                        .voornaam("Jos")
                        .achternaam("Patat")
                        .username("JPatat")
                        .password("password")
                        .role("SPELER")
                        .email("Jpatat@gmail.com").build())
                .adres(new Adres.Builder()
                        .gemeente("Leuven")
                        .straat("straat in leuven")
                        .huisnummer("42")
                        .postcode("3000")
                        .build())

                .build();

        // When
        ResultActions perform = this.mockMvc.perform(MockMvcRequestBuilders.post("/spelers")
                .content(toJson(josPatat))
                .contentType(MediaType.APPLICATION_JSON));
        ResultActions resultActions = perform
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists());
        MvcResult result = resultActions.andReturn();
        MockHttpServletResponse response = result.getResponse();
        String json = response.getContentAsString();
        Speler gemaakteSpeler = fromJson(json, Speler.class);

        // Then
        assertEquals(gemaakteSpeler.getUser().getVoornaam(), josPatat.getUser().getVoornaam());
        assertEquals(gemaakteSpeler.getUser().getAchternaam(), josPatat.getUser().getAchternaam());
    }

}
