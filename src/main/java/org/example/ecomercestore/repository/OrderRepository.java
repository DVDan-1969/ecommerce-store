package org.example.ecomercestore.repository;



import org.example.ecomercestore.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
