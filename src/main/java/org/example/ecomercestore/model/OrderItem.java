package org.example.ecomercestore.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name="orderitems")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal price;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "order_id",nullable = false)
    private Order order;
    @ManyToOne
    @JoinColumn(name="product_id",nullable = false)
    private Product product;

    public OrderItem() {}
    public OrderItem(String order, String product, BigDecimal price, int quantity) {
        this.price = price;
        this.quantity = quantity;
    }
    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public BigDecimal getPrice() {return price;}
    public void setPrice(BigDecimal price) {this.price = price;}
    public int getQuantity() {return quantity;}
    public void setQuantity(int quantity) {this.quantity = quantity;}
}
