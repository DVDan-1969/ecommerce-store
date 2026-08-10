package org.example.ecomercestore.dto;

import java.math.BigDecimal;
import java.util.Date;

public class OrderResponseDTO {
    private Long id;
    private Date date;
    private BigDecimal total;
    private Long userId;

    public OrderResponseDTO() {}
    public OrderResponseDTO(Long id,Date date,BigDecimal total,Long userId) {
        this.id = id;
        this.date = date;
        this.total = total;
        this.userId = userId;
    }
    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public Date getDate() {return date;}
    public void setDate(Date date) {this.date = date;}
    public BigDecimal getTotal() {return total;}
    public void setTotal(BigDecimal total) {this.total = total;}
    public Long getUserId() {return userId;}
    public void setUserId(Long userId) {this.userId = userId;}
}
