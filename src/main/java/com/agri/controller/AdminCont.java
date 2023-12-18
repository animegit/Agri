package com.agri.controller;


import com.agri.dto.ProductDTO;
import com.agri.model.Category;
import com.agri.model.Product;
import com.agri.services.CategoryService;
import com.agri.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Controller
public class AdminCont {
    public String uploadDir=System.getProperty("user.dir")+ "/src/main/resources/static/images/productImage";

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;
    @GetMapping("/admin")
    public String adminHome(){
        return "adminHome";

    }
    @GetMapping("/admin/categories")
    public String getcat(Model model){
        model.addAttribute("categories",categoryService.getcat());
        return "categories";
    }
    @GetMapping("/admin/categories/add")
    public String getaddcat(Model model){

        model.addAttribute("category",new Category());

        return "catadd";
    }
    @PostMapping("/admin/categories/add")
    public String postaddcat(@ModelAttribute("category") Category category){
        categoryService.addcat(category);

        return "redirect:/admin/categories";
    }

    @GetMapping("/admin/categories/delete/{id}")
    public String deleteCat(@PathVariable int id){
    categoryService.deletecat(id);
        return "redirect:/admin/categories";

    }
    @GetMapping("/admin/categories/update/{id}")
    public String updateCat(@PathVariable int id,Model model){
        Optional<Category> category=categoryService.getcatbyid(id);
        if(category.isPresent()){
            model.addAttribute("category",category.get());
            return "catadd";

        }
        else{
            return "404";
        }
    }


    //Product part

    @GetMapping("/admin/products")
    public String getproducts(Model model){
        model.addAttribute("products",productService.getProducts());
        return "products";

    }
    @GetMapping("/admin/products/add")
    public String productsAddGet(Model model){
        model.addAttribute("productDTO",new ProductDTO());
        model.addAttribute("categories",categoryService.getcat());
        return "productAdd";

    }

    @PostMapping("/admin/products/add")
    public String productsAddPost(@ModelAttribute("productDTO") ProductDTO productDTO, @RequestParam("productImage")MultipartFile multipartFile,@RequestParam("imgName")String imgName) throws IOException{
        Product product=new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setCategory(categoryService.getcatbyid(productDTO.getCategoryId()).get());
        product.setPrice(productDTO.getPrice());
        product.setWeight(productDTO.getWeight());
        product.setDescription(productDTO.getDescription());
        String imguuid;
        if(!multipartFile.isEmpty()){
            imguuid=multipartFile.getOriginalFilename();
            Path fileNameandPath= Paths.get(uploadDir,imguuid);
            Files.write(fileNameandPath,multipartFile.getBytes());
        }
        else{
            imguuid=imgName;
        }
        product.setImage_Name(imguuid);
        productService.addProduct(product);
        return "redirect:/admin/products";

    }
}
