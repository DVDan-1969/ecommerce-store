package org.example.ecomercestore.service;
import org.example.ecomercestore.dto.UserRequestDTO;
import org.example.ecomercestore.dto.UserResponseDTO;
import org.example.ecomercestore.exception.UserNotFoundException;
import org.example.ecomercestore.mapper.UserMapper;
import org.example.ecomercestore.model.User;
import org.example.ecomercestore.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;
    private User user;
    private UserResponseDTO userResponseDTO;
    private UserRequestDTO userRequestDTO;


    @BeforeEach
    public void setUp() {
        user=new User();
        user.setId(1L);
        user.setuserName("Mihai");
        user.setRole("user");
        user.setEmail("mihai@yahoo.com");
        userResponseDTO=new UserResponseDTO();
        userResponseDTO.setUserId(1L);
        userResponseDTO.setUserName("Mihai");
        userResponseDTO.setRole("user");
        userResponseDTO.setEmail("mihai@yahoo.com");

        userRequestDTO=new UserRequestDTO();
        userRequestDTO.setUserName("Mihai");
        userRequestDTO.setEmail("mihai@yahoo.com");
        userRequestDTO.setPassword("1234");
    }

    @Test
    void shouldReturnUserWhenUserFound(){
        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));
        when(userMapper.toResponseDTO(user))
                .thenReturn(userResponseDTO);
        UserResponseDTO result=userService.getUserById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(1L);
        assertThat(result.getUserName()).isEqualTo("Mihai");

    }
    @Test
    void shouldThrowExceptionWhenIdNotFound(){
        Long id=999L;

        when(userRepository.findById(id))
                .thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(
                UserNotFoundException.class,
                () -> userService.getUserById(id)
        );
        assertEquals("User not found", exception.getMessage());
    }
    @Test
    void shouldReturnUserWhenEmailFound(){
        when(userRepository.findByEmail("mihai@yahoo.com"))
                .thenReturn(Optional.of(user));
        when(userMapper.toResponseDTO(user))
                .thenReturn(userResponseDTO);

        UserResponseDTO result=userService.getUserByEmail("mihai@yahoo.com");

        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo("mihai@yahoo.com");
    }

    @Test
    void shouldThrowExceptionWhenEmailNotFound(){
       String email="unknown@yahoo.com";

        when(userRepository.findByEmail(email))
                .thenReturn(Optional.empty());
        UserNotFoundException exception = assertThrows(
                UserNotFoundException.class,
                () -> userService.getUserByEmail(email)
        );
        assertEquals("User not found", exception.getMessage());

    }
    @Test
    void shouldSaveAndReturnUser(){
        when(userMapper.toEntity(userRequestDTO)).thenReturn(user);
        when(passwordEncoder.encode("1234")).thenReturn("encodedPassword");
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toResponseDTO(user)).thenReturn(userResponseDTO);

        UserResponseDTO result=userService.saveUser(userRequestDTO);

        assertThat(result).isNotNull();
        assertThat(user.getPassword()).isEqualTo("encodedPassword");
        assertThat(result.getUserName()).isEqualTo("Mihai");
    }
    @Test
    void shouldReturnListOfUsers(){
        User user2=new User();
        user2.setId(2L);
        user2.setuserName("Andrei");
        user2.setRole("user");
        user2.setEmail("andrei@yahoo.com");

        UserResponseDTO response2=new UserResponseDTO();
        response2.setUserId(2L);
        response2.setUserName("Andrei");
        response2.setRole("user");
        response2.setEmail("andrei@yahoo.com");



        when(userRepository.findAll())
                .thenReturn(List.of(user,user2));
        when(userMapper.toResponseDTO(user))
                .thenReturn(userResponseDTO);
        when(userMapper.toResponseDTO(user2))
                .thenReturn(response2);

        List<UserResponseDTO> result=userService.getAllUsers();

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getUserName()).isEqualTo("Mihai");
        assertThat(result.get(1).getUserName()).isEqualTo("Andrei");
    }

    @Test
    void shouldUpdateAndReturnUser(){

        userResponseDTO.setUserName("Andrei");
        userResponseDTO.setEmail("andrei@yahoo.com");
        userResponseDTO.setRole("admin");
        userRequestDTO.setUserName("Andrei");
        userRequestDTO.setEmail("andrei@yahoo.com");
        userRequestDTO.setPassword("5678");
        userRequestDTO.setRole("admin");

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.encode("5678"))
                .thenReturn("encodedPassword");
        when(userRepository.save(user))
                .thenReturn(user);
        when(userMapper.toResponseDTO(user))
                .thenReturn(userResponseDTO);

        UserResponseDTO result=userService.updateUser(1L,userRequestDTO);

        assertThat(result).isNotNull();
        assertThat(result.getUserName()).isEqualTo("Andrei");
        assertThat(user.getuserName()).isEqualTo("Andrei");
        assertThat(user.getPassword()).isEqualTo("encodedPassword");
        assertThat(user.getEmail()).isEqualTo("andrei@yahoo.com");
        assertThat(user.getRole()).isEqualTo("admin");
    }

    @Test
    void shouldThrowExceptionWhenUpdateUserNotFound(){
        Long id=999L;
        when(userRepository.findById(id))
                .thenReturn(Optional.empty());
        UserNotFoundException exception=assertThrows(
                UserNotFoundException.class,
                ()-> userService.updateUser(id,userRequestDTO)
        );
        assertEquals("User not found", exception.getMessage());
    }
    @Test
    void shouldDeleteUser(){
        Long id=1L;

        when(userRepository.findById(id))
                .thenReturn(Optional.of(user));
        userService.deleteById(id);

        verify(userRepository).delete(user);
    }

    @Test
    void shouldThrowExceptionWhenDeletingUserNotFound(){
        Long id=999L;

        when(userRepository.findById(id))
                .thenReturn(Optional.empty());

        UserNotFoundException exception=assertThrows(UserNotFoundException.class,
                ()-> userService.deleteById(id));
        assertEquals("User not found", exception.getMessage());
    }

}

