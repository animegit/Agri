package com.agri.model;

import jakarta.persistence.*;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Data
@Table(name="roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(nullable = false,unique = true)
    @NotEmpty
    private String name;
    @ManyToMany(mappedBy = "roles")
    private List<User> user;
}
