package com.example.demo.Services;

import com.example.demo.Converter.ManagerConverter;
import com.example.demo.dao.ManagerRepository;
import com.example.demo.domain.Manager;
import com.example.demo.domain.User;
import com.example.demo.dto.ManagerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManagerService {

    private final ManagerRepository managerRepository;
    private final ManagerConverter managerConverter;

    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

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

    public ManagerDTO getManagerByUserId(User user){
        Manager manager = managerRepository.findByUser(user).orElseThrow();
        return managerConverter.managerDTO(manager);
    }

    public List<ManagerDTO> getALL(){
        List<Manager> managers = managerRepository.findAll();
        return managerConverter.listToDto(managers);
    }
}
