package com.semestralka.semestralkaVPA.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class SecurityUtils {
    public static final String ADMINISTRATOR_ROLE = "Administrator";
    public static boolean isUserAnonymous(Authentication auth) {
        return auth.getPrincipal().toString().toLowerCase().contains("anon".toLowerCase());
    }

    public static boolean isUserInRole(Authentication auth, String role) {
        for (GrantedAuthority grantedAuthority : auth.getAuthorities()
        ) {
            if (grantedAuthority.getAuthority().equals(role)) {
                return true;
            }
        }
        return false;
    }
}
