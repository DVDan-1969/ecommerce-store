package org.example.ecomercestore.service;

import jakarta.transaction.Transactional;

import org.example.ecomercestore.dto.UserRequestDTO;
import org.example.ecomercestore.dto.UserResponseDTO;
import org.example.ecomercestore.exception.UserNotFoundException;
import org.example.ecomercestore.mapper.UserMapper;
import org.example.ecomercestore.model.User;
import org.example.ecomercestore.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository repository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository repository,
                           UserMapper userMapper,
                           PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.userMapper = userMapper;
        this.passwordEncoder= passwordEncoder;
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {

        logger.info("Retrieving all users");
        return repository.findAll()
                .stream()
                .map(userMapper::toResponseDTO)
                .toList();
    }

    @Override
    public UserResponseDTO getUserById(Long id) {
        logger.info("Searching for user with id {}", id);
        User user = repository.findById(id)
                .orElseThrow(()->{
                    logger.warn("User with id {} not found",id);
                    return new UserNotFoundException("User not found");
                });
        return userMapper.toResponseDTO(user);
    }

    @Override
    public UserResponseDTO getUserByEmail(String email) {
        logger.info("Searching for user by email ", email);
        User user = repository.findByEmail(email)
                .orElseThrow(()->{
                    logger.warn("User with email not found");
                    return new UserNotFoundException("User not found");
                });

        return userMapper.toResponseDTO(user);
    }

    @Override
    public UserResponseDTO saveUser(UserRequestDTO dto) {

        logger.info("Saving new user ");
        User user = userMapper.toEntity(dto);
        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        user.setPassword(encodedPassword);
        User savedUser = repository.save(user);
        logger.info("User created successfully with id {}", savedUser.getId());
        return userMapper.toResponseDTO(savedUser);

    }

    @Override
    public UserResponseDTO updateUser(Long id,UserRequestDTO dto) {

        logger.info("Updating user with id {}", id);
        User user=repository.findById(id)
                .orElseThrow(()->{
                    logger.warn("User with id {} not found",id);
                    return new UserNotFoundException("User not found");
                });
        user.setuserName(dto.getUserName());
        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        user.setPassword(encodedPassword);
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole());
        User updatedUser=repository.save(user);
        logger.info("User with id {} updated successfully", updatedUser.getId());
        return userMapper.toResponseDTO(updatedUser);

    }

    @Override
    public void deleteById(Long id) {

        logger.info("Deleting user with id {}", id);

        User user=repository.findById(id)
                .orElseThrow(()->{
                    logger.warn("User with id {} not found",id);
                    return new UserNotFoundException("User not found");
                });
        repository.delete(user);
        logger.info("User with id {} deleted successfully", id);
    }

}
