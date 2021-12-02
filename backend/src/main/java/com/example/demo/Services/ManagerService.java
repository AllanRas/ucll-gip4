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
        Manager manager = managerConverter.dtoToManager(managerDTO);

        managerRepository.save(manager);
        return managerConverter.managerDTO(manager);
    }
}
