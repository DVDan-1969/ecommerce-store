package org.example.ecomercestore.controller;

import org.example.ecomercestore.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PageProductController {
    private final ProductService service;

    public PageProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping("/products")
    public String products(Model model) {
        model.addAttribute("products", service.getAllProducts());
        return "products";
    }

    @GetMapping("/products/{id}")
    public String product(@PathVariable Long id, Model model) {
        model.addAttribute("product", service.getProductById(id));
        return "product-details";
    }
}
