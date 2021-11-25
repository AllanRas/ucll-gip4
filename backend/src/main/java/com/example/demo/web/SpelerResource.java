package com.example.demo.web;
    
import com.example.demo.Services.SpelerService;
import com.example.demo.domain.Speler;
import com.example.demo.dto.SpelerDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping
    public List<Speler> getAllSpelers(){
        return spelerService.getAllSpelers();
    }

    /*@GetMapping("/{id}/getOne")
    public Optional<Speler> getById(@PathVariable("id") long id){
        return spelerService.getById(id);
    }*/

    //Verwijderen
    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/{id}")
    public SpelerDTO inActiveSpeler(@PathVariable("id") long id){
        return spelerService.inActiveSpeler(id);
    }

    //Gegevens wijzigen van een speler
    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/{id}/update")
    public SpelerDTO updateSpeler(@PathVariable("id") long id, @RequestBody SpelerDTO spelerDTO){
        return spelerService.updateSpeler(id,spelerDTO);
    }
}
