package org.example.ecomercestore.controller;

import jakarta.validation.Valid;
import org.example.ecomercestore.dto.ProductRequestDTO;
import org.example.ecomercestore.model.Product;
import org.example.ecomercestore.service.CategoryService;
import org.example.ecomercestore.service.OrderItemService;
import org.example.ecomercestore.service.OrderService;
import org.example.ecomercestore.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PageUserController {

    private final CategoryService categoryService;
    private final OrderService orderService;
    private final OrderItemService orderItemService;


    public PageUserController(CategoryService categoryService,
                              OrderService orderService, OrderItemService orderItemService) {


        this.categoryService = categoryService;
        this.orderService = orderService;
        this.orderItemService = orderItemService;

    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }



    @GetMapping("/categories-page")
    public String categories(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "categories";
    }

    @GetMapping("/orders-page")
    public String orders(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        return "orders";
    }

    @GetMapping("/order-items-page")
    public String orderItems(Model model) {
        model.addAttribute("orderItems", orderItemService.getAllOrderItems());
        return "order-items";
    }
}
