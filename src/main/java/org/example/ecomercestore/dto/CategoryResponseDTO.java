package org.example.ecomercestore.dto;

public class CategoryResponseDTO {
    private Long  id;
    private String categoryName;

    public CategoryResponseDTO() {}
    public CategoryResponseDTO(Long id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
    }
    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public String getCategoryName() {return categoryName;}
    public void setCategoryName(String categoryName) {this.categoryName = categoryName;}
}
