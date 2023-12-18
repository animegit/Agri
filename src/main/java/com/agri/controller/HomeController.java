package com.agri.controller;

import com.agri.services.CategoryService;
import com.agri.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController {

    @Autowired
    public ProductService productService;
    @Autowired
    public CategoryService categoryService;
    @GetMapping({"/","/home"})
    public String home(Model model){
        return "index";

    }
    @GetMapping("/shop")
    public String shop(Model model){
        model.addAttribute("categories",categoryService.getcat());
        model.addAttribute("products",productService.getProducts());
        return"shop";
    }
    @GetMapping("/shop/category/{id}")
    public String shopByCategory(Model model, @PathVariable int id){
        model.addAttribute("categories",categoryService.getcat());
        model.addAttribute("products",productService.getProductsByCategory(id));
        return"shop";
    }
    @GetMapping("/shop/viewproduct/{id}")
    public String viewProduct(Model model,@PathVariable long id){
        model.addAttribute("product",productService.getProductById(id).get());
        return "viewProduct";
    }
}
