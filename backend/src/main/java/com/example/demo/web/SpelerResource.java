package com.example.demo.web;
    
import com.example.demo.Services.SpelerService;
import com.example.demo.dto.CreateSpelerDTO;
import com.example.demo.dto.SpelerDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/spelers")
@CrossOrigin("http://localhost:3000/")
public class SpelerResource {

    private final SpelerService spelerService;

    public SpelerResource(SpelerService spelerService){
        this.spelerService = spelerService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public SpelerDTO createSpeler(@RequestBody CreateSpelerDTO createSpelerDTO) {
        System.out.println(createSpelerDTO.toString());
        return spelerService.createSpeler(createSpelerDTO);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping
    public List<SpelerDTO> getAllSpelers(){
        return spelerService.getAllSpelers();
    }

    @PreAuthorize("hasRole('MANAGER') or hasRole('SPELER')")
    @GetMapping("/{id}/getOne")
    public SpelerDTO getById(@PathVariable("id") long id){
        return spelerService.getById(id);
    }

    //Verwijderen
    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/{id}")
    public SpelerDTO inActiveSpeler(@PathVariable("id") long id){
        return spelerService.inActiveSpeler(id);
    }

    //Gegevens wijzigen van een speler
    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/{id}/update")
    public CreateSpelerDTO updateSpeler(@PathVariable("id") long id, @RequestBody CreateSpelerDTO createSpelerDTO){
        return spelerService.updateSpeler(id, createSpelerDTO);
    }
}
