package com.example.gymrat_backend.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    private final UserDetailsService userDetailsService;
    private final HttpSessionSecurityContextRepository securityContextRepository;

    @Value("${app.auth.username}")
    private String authUsername;

    public LoginController(UserDetailsService userDetailsService,
                           HttpSessionSecurityContextRepository securityContextRepository) {
        this.userDetailsService = userDetailsService;
        this.securityContextRepository = securityContextRepository;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @Profile("demo")
    @GetMapping("/demo")
    public String demoLogin(HttpServletRequest request, HttpServletResponse response) {
        try {
            UserDetails demoUser = userDetailsService.loadUserByUsername(authUsername);
            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(demoUser, null, demoUser.getAuthorities());
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(auth);
            SecurityContextHolder.setContext(context);
            securityContextRepository.saveContext(context, request, response);
        } catch (Exception e) {
            return "redirect:/login";
        }
        return "redirect:/";
    }
}
