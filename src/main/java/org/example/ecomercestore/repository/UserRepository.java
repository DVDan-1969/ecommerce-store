package org.example.ecomercestore.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.example.ecomercestore.model.User;

public interface UserRepository extends JpaRepository<User,Long> {
}
