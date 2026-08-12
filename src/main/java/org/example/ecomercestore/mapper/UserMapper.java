package org.example.ecomercestore.mapper;

import org.example.ecomercestore.dto.UserRequestDTO;
import org.example.ecomercestore.dto.UserResponseDTO;
import org.example.ecomercestore.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toEntity(UserRequestDTO dto){
        User user = new User();
        user.setuserName(dto.getUserName());
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole());
        user.setPassword(dto.getPassword());
        return user;
    }
    public UserResponseDTO toResponseDTO(User user){
        return new UserResponseDTO(
                user.getId(),
                user.getEmail(),
                user.getuserName(),
                user.getRole()

        );
    }
}
