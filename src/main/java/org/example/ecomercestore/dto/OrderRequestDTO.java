package org.example.ecomercestore.dto;


import jakarta.validation.constraints.NotNull;


import java.util.Date;

public class OrderRequestDTO {
    @NotNull
    private Date date;
    @NotNull
    private Long userId;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
