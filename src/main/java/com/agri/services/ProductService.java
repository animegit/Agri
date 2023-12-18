package com.agri.services;

import com.agri.model.Product;
import com.agri.repos.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ProductService {
    @Autowired
    public ProductRepo prodrepo;
    public List<Product> getProducts(){
        return prodrepo.findAll();
    }

    public void addProduct(Product product){
        prodrepo.save(product);
    }
    public void removeProduct(long id){
        prodrepo.deleteById(id);
    }
    public Optional<Product> getProductById(long id){
       return prodrepo.findById(id);
    }
    public List<Product> getProductsByCategory(int id){
        return prodrepo.findAllByCategory_Id(id);
    }

}
