package com.agri.model;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category",referencedColumnName = "category")
    private Category category;
    private double price;
    private double weight;
    private String description;
    private String image_Name;


}
