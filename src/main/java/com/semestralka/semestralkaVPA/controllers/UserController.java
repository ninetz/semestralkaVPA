package com.semestralka.semestralkaVPA.controllers;

import com.semestralka.semestralkaVPA.security.UserPrincipal;
import com.semestralka.semestralkaVPA.services.RegisterService;
import com.semestralka.semestralkaVPA.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    RegisterService registerService;

    @GetMapping(value = "/getuser/{username}")
    public UserDetails getUserDetails(@PathVariable String username) {
        UserPrincipal userDetails = (UserPrincipal) userService.loadUserByUsername(username);
        return userService.loadUserByUsername(username);
    }
    @RequestMapping(method = RequestMethod.POST,value = "/register")
    public ResponseEntity<Object> register(@RequestParam("name") String name, @RequestParam("password") String password) {
        System.out.println("HEREEE " + name + " " + password);
       if (registerService.registerUser(name,password)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().body("Username exists already.");

    }
}
