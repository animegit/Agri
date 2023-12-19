package com.agri.services;

import com.agri.model.CustomUserDetails;
import com.agri.model.User;
import com.agri.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println(email);
        Optional<User> user=userRepo.findUserByEmail(email);
        user.orElseThrow(()->new UsernameNotFoundException("User not found"));
        return user.map(CustomUserDetails::new).get();

    }
}
