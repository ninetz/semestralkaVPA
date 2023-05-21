package com.semestralka.semestralkaVPA.services;

import com.semestralka.semestralkaVPA.entities.Roles;
import com.semestralka.semestralkaVPA.entities.User;
import com.semestralka.semestralkaVPA.repositories.RolesRepository;
import com.semestralka.semestralkaVPA.repositories.UserRepository;
import com.semestralka.semestralkaVPA.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RolesRepository rolesRepository;

    @Override
    public UserPrincipal loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        List<Roles> roles = rolesRepository.findAllByUser(user);
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        for (Roles role : roles
        ) {
            authorities.add(new SimpleGrantedAuthority(role.getRole()));
        }
        return new UserPrincipal(user, user.getId(), authorities);
    }
}
