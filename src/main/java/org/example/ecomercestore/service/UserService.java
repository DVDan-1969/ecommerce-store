package org.example.ecomercestore.service;

import org.example.ecomercestore.dto.UserRequestDTO;
import org.example.ecomercestore.dto.UserResponseDTO;

import java.util.List;


public interface UserService {
    List<UserResponseDTO> getAllUsers();

    UserResponseDTO getUserById(Long id);


    UserResponseDTO getUserByEmail(String email);

    UserResponseDTO saveUser(UserRequestDTO dto);

    UserResponseDTO updateUser(Long id,UserRequestDTO dto);


    void deleteById(Long id);
}
