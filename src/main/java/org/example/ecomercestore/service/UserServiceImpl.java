package org.example.ecomercestore.service;

import jakarta.transaction.Transactional;

import org.example.ecomercestore.dto.UserRequestDTO;
import org.example.ecomercestore.dto.UserResponseDTO;
import org.example.ecomercestore.mapper.UserMapper;
import org.example.ecomercestore.model.User;
import org.example.ecomercestore.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository repository, UserMapper userMapper) {
        this.repository = repository;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {

        return repository.findAll()
                .stream()
                .map(userMapper::toResponseDTO)
                .toList();
    }

    @Override
    public UserResponseDTO getUserById(Long id) {
        User user = repository.findById(id)
                .orElseThrow(()->new RuntimeException("User not found!"));
        return userMapper.toResponseDTO(user);
    }

    @Override
    public UserResponseDTO getUserByEmail(String email) {
        User user = repository.findByEmail(email)
                .orElseThrow(()->new RuntimeException("User not found"));
        return userMapper.toResponseDTO(user);
    }

    @Override
    public UserResponseDTO saveUser(UserRequestDTO dto) {
        User user = userMapper.toEntity(dto);
        User savedUser = repository.save(user);
        return userMapper.toResponseDTO(savedUser);

    }

    @Override
    public UserResponseDTO updateUser(Long id,UserRequestDTO dto) {
        User user=repository.findById(id)
                .orElseThrow(()->new RuntimeException("User not found!"));
        user.setuserName(dto.getUserName());
        user.setPassword(dto.getPassword());
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole());
        User updatedUser=repository.save(user);
        return userMapper.toResponseDTO(updatedUser);

    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
