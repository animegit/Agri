package com.agri.controller;


import com.agri.model.Role;
import com.agri.model.User;
import com.agri.repos.RoleRepo;
import com.agri.repos.UserRepo;
import com.agri.services.CustomUserDetailsService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {
    @Autowired
    UserRepo userRepo;
    @Autowired
    RoleRepo roleRepo;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @GetMapping("/register")
    public String registerGet(){
        return "Register";
    }
    @PostMapping("/register")
    public String registerPost(@ModelAttribute("user") User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));


        List<Role> roles=new ArrayList<>();
        roles.add(roleRepo.findById(2).get());
        user.setRoles(roles);

        userRepo.save(user);

        // Authenticate the user after registration
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getEmail());
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, user.getPassword(), userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        return "redirect:/";
    }

}
