package org.example.ecomercestore.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date data;
    private BigDecimal total;


    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;
    @OneToMany(mappedBy ="order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems=new ArrayList<>();


    public Order() {}
    public Order(Date data, BigDecimal total) {
        this.data = data;
        this.total = total;
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public Date getData() {return data;}
    public void setData(Date data) {this.data = data;}
    public BigDecimal getTotal() {return total;}
    public void setTotal(BigDecimal total) {this.total = total;}
    public User getUser() {return user;}
    public void setUser(User user) {this.user = user;}
    public List<OrderItem> getOrderItems() {return orderItems;}
    public void setOrderItems(List<OrderItem> orderItems) {this.orderItems = orderItems;}
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }
}
