package com.agri.repos;

import com.agri.model.Category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface CategoryRepo extends JpaRepository<Category, Integer> {
}
