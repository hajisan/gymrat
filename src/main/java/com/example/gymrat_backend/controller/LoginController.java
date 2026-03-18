package com.example.gymrat_backend.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    private final UserDetailsService userDetailsService;

    @Value("${app.auth.username}")
    private String authUsername;

    public LoginController(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @Profile("demo")
    @GetMapping(value = "/demo", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public ResponseEntity<String> demoLogin(HttpServletRequest request, HttpServletResponse response) {
        try {
            log.info("Demo login attempt for user: {}", authUsername);
            UserDetails demoUser = userDetailsService.loadUserByUsername(authUsername);
            UsernamePasswordAuthenticationToken auth =
                    UsernamePasswordAuthenticationToken.authenticated(demoUser, null, demoUser.getAuthorities());
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(auth);
            SecurityContextHolder.setContext(context);
            request.getSession(true).setAttribute(
                    HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context);
            log.info("Demo login successful, returning client-side redirect to /");
            return ResponseEntity.ok("<html><head><meta http-equiv='refresh' content='0;url=/'></head><body></body></html>");
        } catch (Exception e) {
            log.error("Demo login failed: {}", e.getMessage(), e);
            return ResponseEntity.ok("<html><head><meta http-equiv='refresh' content='0;url=/login'></head><body></body></html>");
        }
    }
}
