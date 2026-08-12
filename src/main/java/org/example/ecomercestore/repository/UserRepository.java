package org.example.ecomercestore.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.example.ecomercestore.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
