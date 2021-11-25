package com.example.demo.web;


import com.example.demo.Services.SpelerService;
import com.example.demo.dao.SpelerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import com.example.demo.domain.Speler;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/spelers")
public class SpelerResource {

    private final SpelerService spelerService;

    public SpelerResource(SpelerService spelerService){
        this.spelerService = spelerService;
    }

    // Moet nog verander naar DTO
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Speler createSpeler(@RequestBody Speler speler) {
        return spelerService.createSpeler(speler);
    }
}
