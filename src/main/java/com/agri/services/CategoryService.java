package com.agri.services;

import com.agri.model.Category;
import com.agri.repos.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    CategoryRepo catrepo;


    public void addcat(Category cat){
        catrepo.save(cat);



    }
    public List<Category> getcat(){
        return catrepo.findAll();
    }
    public void deletecat(int id){
        catrepo.deleteById(id);
    }

    public Optional<Category> getcatbyid(int id){
        return catrepo.findById(id);
    }

}
