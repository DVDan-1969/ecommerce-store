package org.example.ecomercestore.service;

import org.example.ecomercestore.model.User;

import java.util.List;


public interface UserService {
    List<User> getAllUsers();

    User getUserById(Long id);


    User getUserByEmail(String email);

    User saveUser(User user);

    User updateUser(User user);


    void deleteById(Long id);


}
