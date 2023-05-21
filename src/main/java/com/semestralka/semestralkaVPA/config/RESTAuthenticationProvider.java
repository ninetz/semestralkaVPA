package com.semestralka.semestralkaVPA.config;

import com.semestralka.semestralkaVPA.repositories.RolesRepository;
import com.semestralka.semestralkaVPA.security.UserPrincipal;
import com.semestralka.semestralkaVPA.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class RESTAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    UserService userService;
    @Autowired
    RolesRepository rolesRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String plainTextPassword = authentication.getCredentials().toString();

        UserPrincipal user = userService.loadUserByUsername(name);
        if (user != null && BCrypt.checkpw(plainTextPassword, user.getPassword())) {
            return new UsernamePasswordAuthenticationToken(
                    user,
                    user.getPassword(), user.getAuthorities());
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
