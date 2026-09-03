package org.example.ecomercestore.controller;

import jakarta.validation.Valid;
import org.example.ecomercestore.dto.ProductRequestDTO;
import org.example.ecomercestore.service.CategoryService;
import org.example.ecomercestore.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class PageProductController {
    private final ProductService productService;
    private final CategoryService categoryService;


    public PageProductController(ProductService productService, CategoryService categoryService) {

        this.productService = productService;
        this.categoryService = categoryService;


    }

    @GetMapping("/products")
    public String products(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "products";
    }

    @GetMapping("/products/{id}")
    public String product(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        return "product-details";
    }

    @GetMapping("/products/add")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new ProductRequestDTO());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "product-form";
    }

    @PostMapping("/products/add")
    public String addProduct(@Valid @ModelAttribute("product") ProductRequestDTO product,
                             BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategories());
            return "product-form";
        }
        productService.save(product);
        return "redirect:/products";
    }
    @GetMapping("/products/search")
    public String searchProducts(@RequestParam String name, Model model) {
        model.addAttribute("products",productService.searchByName(name));
        return "products";
    }
}
