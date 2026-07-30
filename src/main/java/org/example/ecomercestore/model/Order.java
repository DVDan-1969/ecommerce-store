package org.example.ecomercestore.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    String data;
    BigDecimal total;


    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;
    @OneToMany(mappedBy ="order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;


    public Order() {}
    public Order(String data, BigDecimal total) {
        this.data = data;
        this.total = total;
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public String getData() {return data;}
    public void setData(String data) {this.data = data;}
    public BigDecimal getTotalPrice() {return total;}
    public void setTotalPrice(BigDecimal totalPrice) {this.total = totalPrice;}
}
