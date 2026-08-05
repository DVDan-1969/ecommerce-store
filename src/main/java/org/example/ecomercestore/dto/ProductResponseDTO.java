package org.example.ecomercestore.dto;

import java.math.BigDecimal;

public class ProductResponseDTO {
    private Long id;
    private String productName;
    private Integer productQuantity;
    private String productDescription;
    private BigDecimal productPrice;
    private String productImage;

    public ProductResponseDTO() {
    }
    public ProductResponseDTO(Long id, String productName, Integer productQuantity,
                              String productDescription, BigDecimal productPrice, String productImage) {
        this.id = id;
        this.productName = productName;
        this.productQuantity = productQuantity;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
        this.productImage = productImage;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public Integer getProductQuantity() {
        return productQuantity;
    }
    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }
    public String getProductDescription() {
        return productDescription;
    }
    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }
    public BigDecimal getProductPrice() {
        return productPrice;
    }
    public void setProductPrice(BigDecimal price) {
        this.productPrice = price;
    }
    public String getProductImage() {return productImage;}
    public void setProductImage(String productImage) {this.productImage = productImage;}

}
