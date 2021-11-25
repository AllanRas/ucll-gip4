package com.example.demo.web;


import com.example.demo.Services.SpelerService;
import com.example.demo.dto.SpelerDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/spelers")
public class SpelerResource {

    private final SpelerService spelerService;

    public SpelerResource(SpelerService spelerService){
        this.spelerService = spelerService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public SpelerDTO createSpeler(@RequestBody SpelerDTO spelerDTO) {
        return spelerService.createSpeler(spelerDTO);
    }
}
