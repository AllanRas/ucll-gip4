package com.example.demo.Services;

import com.example.demo.Converter.ManagerConverter;
import com.example.demo.dao.ManagerRepository;
import com.example.demo.domain.Manager;
import com.example.demo.dto.ManagerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ManagerService {

    private final ManagerRepository managerRepository;
    private final ManagerConverter managerConverter;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public ManagerService(ManagerRepository managerRepository, ManagerConverter managerConverter) {
        this.managerRepository = managerRepository;
        this.managerConverter = managerConverter;
    }

    public ManagerDTO createManager(ManagerDTO managerDTO){
        Manager manager = managerConverter.dtoToManager(managerDTO);

        // set role
        manager.getUser().setRole("MANAGER");

        // password encoden
        manager.getUser().setPassword(passwordEncoder.encode(manager.getUser().getPassword()));

        System.out.println(manager.getUser().toString());
        managerRepository.save(manager);
        return managerConverter.managerDTO(manager);
    }
}
