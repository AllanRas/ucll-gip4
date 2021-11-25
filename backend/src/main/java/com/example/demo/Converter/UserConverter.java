package com.example.demo.Converter;

import com.example.demo.domain.User;
import com.example.demo.dto.UserDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserConverter {


    // user to userDTO
    public UserDTO userToDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setVoornaam(user.getVoornaam());
        userDTO.setAchternaam(user.getAchternaam());
        userDTO.setRole(user.getRole());
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }

    // user lijst naar userDTO lijst
    public List<UserDTO> UserListToDTO(List<User> userList){
        return userList.stream().map(this::userToDTO).collect(Collectors.toList());
    }

    // userDTO to user
    public User userDTOtoUser(UserDTO userDTO){
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setVoornaam(userDTO.getVoornaam());
        user.setAchternaam(userDTO.getAchternaam());
        user.setRole(userDTO.getRole());
        user.setEmail(userDTO.getEmail());
        return user;
    }
}
