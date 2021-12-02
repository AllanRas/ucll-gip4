package com.example.demo.Services;

import com.example.demo.Converter.ManagerConverter;
import com.example.demo.dao.ManagerRepository;
import com.example.demo.domain.Manager;
import com.example.demo.dto.ManagerDTO;
import org.springframework.stereotype.Service;

@Service
public class ManagerService {

    private final ManagerRepository managerRepository;
    private final ManagerConverter managerConverter;

    public ManagerService(ManagerRepository managerRepository, ManagerConverter managerConverter) {
        this.managerRepository = managerRepository;
        this.managerConverter = managerConverter;
    }

    public ManagerDTO createManager(ManagerDTO managerDTO){
        System.out.println(managerDTO.toString());
        System.out.println(managerDTO.getUserDTO().toString());
        Manager manager = managerConverter.dtoToManager(managerDTO);

        manager.getUser().setRole("MANAGER");
        System.out.println(manager.getUser().toString());
        managerRepository.save(manager);
        return managerConverter.managerDTO(manager);
    }
}
