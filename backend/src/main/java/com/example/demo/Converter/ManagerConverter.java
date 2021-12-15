package com.example.demo.Converter;

import com.example.demo.domain.Manager;
import com.example.demo.domain.Speler;
import com.example.demo.dto.ManagerDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ManagerConverter {

    UserConverter userConverter = new UserConverter();

    //manager to DTP manager
    public ManagerDTO managerDTO(Manager manager){
        ManagerDTO managerDTO = new ManagerDTO();
        managerDTO.setId(manager.getId());
        managerDTO.setUserDTO(userConverter.userToDTO(manager.getUser()));
        return  managerDTO;
    }

    // spelerDTO to speler
    public Manager dtoToManager(ManagerDTO managerDTO){
        Manager manager = new Manager();
        manager.setId(managerDTO.getId());
        manager.setUser(userConverter.userDTOtoUser(managerDTO.getUserDTO()));
        manager.getUser().setPassword(managerDTO.getPasswoord());
        return manager;
    }

    public List<ManagerDTO> listToDto(List<Manager> managers){
        return managers.stream().map(this::managerDTO).collect(Collectors.toList());
    }
}
