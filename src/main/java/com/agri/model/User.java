package com.agri.model;

import jakarta.persistence.*;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;


@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotEmpty
    @Column(nullable = false)
    private String firstName;
    private String lastName;
    @NotEmpty
    @Column(nullable = false,unique = true)
    @Email(message = "{error.invalid_email}")
    private String email;

    private String password;

    @ManyToMany(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
    joinColumns = {@JoinColumn(name = "USER_ID",referencedColumnName = "ID")},
            inverseJoinColumns={@JoinColumn(name = "ROLE_ID",referencedColumnName = "ID")}

    )
    private List<Role> roles;

    public User(User user){
        this.firstName=user.firstName;
        this.lastName= user.lastName;;
        this.email= user.getEmail();
        this.password= user.getPassword();
        this.roles= user.getRoles();
    }
    public User (){

    }


}
