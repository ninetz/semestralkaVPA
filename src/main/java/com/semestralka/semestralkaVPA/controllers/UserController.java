package com.semestralka.semestralkaVPA.controllers;

import com.semestralka.semestralkaVPA.security.UserPrincipal;
import com.semestralka.semestralkaVPA.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    UserService userService;
    @GetMapping(value = "/getuser/{username}")
    public UserDetails getUserDetails(@PathVariable String username) {
        UserPrincipal userDetails = (UserPrincipal) userService.loadUserByUsername(username);
        System.out.println(userDetails.getUsername());
     return userService.loadUserByUsername(username);
    }
}
