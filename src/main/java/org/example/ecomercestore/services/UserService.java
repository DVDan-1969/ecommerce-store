package org.example.ecomercestore.services;

import org.example.ecomercestore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface UserService {
    List<User> getAllUsers();

    User getUserById(Long id);


    User getUserByEmail(String email);

    User saveUser(User user);

    User updateUser(User user);


    void deleteById(Long id);


}
