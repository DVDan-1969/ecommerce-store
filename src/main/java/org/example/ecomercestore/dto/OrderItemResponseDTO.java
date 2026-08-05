package org.example.ecomercestore.dto;


import java.math.BigDecimal;

public class OrderItemResponseDTO {
    private Long id;
    private Long orderId;
    private Long productId;
    private BigDecimal price;
    private Integer quantity;

    public OrderItemResponseDTO() {}

    public OrderItemResponseDTO(Long id,Long orderId,Long productId, BigDecimal price, Integer quantity) {
        this.id = id;
        this.price = price;
        this.quantity = quantity;
        this.orderId = orderId;
        this.productId = productId;
    }
    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public Long getOrderId() {return orderId;}
    public void setOrderId(Long orderId) {this.orderId = orderId;}
    public Long getProductId() {return productId;}
    public void setProductId(Long productId) {this.productId = productId;}
    public BigDecimal getPrice() {return price;}
    public void setPrice(BigDecimal price) {this.price = price;}
    public Integer getQuantity() {return quantity;}
    public void setQuantity(Integer quantity) {this.quantity = quantity;}

}
