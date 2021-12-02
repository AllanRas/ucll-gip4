package com.example.demo.web;


import com.example.demo.Services.ManagerService;
import com.example.demo.dto.ManagerDTO;
import com.example.demo.dto.SpelerDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manager")
public class ManagerResource {

    private final ManagerService managerService;

    public ManagerResource(ManagerService managerService) {
        this.managerService = managerService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ManagerDTO createManager(@RequestBody ManagerDTO managerDTO){
        return managerService.createManager(managerDTO);
    }
}
