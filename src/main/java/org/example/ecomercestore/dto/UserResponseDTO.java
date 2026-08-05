package org.example.ecomercestore.dto;

public class UserResponseDTO {
    private Long userId;
    private String email;
    private String userName;
    private String role;
    public UserResponseDTO() {
    }
    public UserResponseDTO(Long userId, String email, String userName, String role) {
        this.userId = userId;
        this.email = email;
        this.userName = userName;
        this.role = role;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
}
