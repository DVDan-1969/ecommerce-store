package org.example.ecomercestore.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String categoryName;

    @OneToMany(mappedBy = "category",cascade=CascadeType.ALL)
    private List<Product> products;

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public String getCategoryName() {return categoryName;}
    public void setCategoryName(String categoryName) {this.categoryName = categoryName;}

}
