package com.technophiles.thenotebook.controllers;


import com.technophiles.thenotebook.controllers.responses.AuthToken;
import com.technophiles.thenotebook.data.models.User;
import com.technophiles.thenotebook.dtos.requests.userCenteredRequests.LoginRequest;
import com.technophiles.thenotebook.exceptions.UserNotFoundException;
import com.technophiles.thenotebook.security.jwt.TokenProvider;
import com.technophiles.thenotebook.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/api/v2/notebook/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider tokenProvider;


    @PostMapping("/login")
    public ResponseEntity<?> loginToPage(@RequestBody LoginRequest request) throws UserNotFoundException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = tokenProvider.generateJWTToken(authentication);
        User user = userService.findUserByEmail(request.getEmail());
        return new ResponseEntity<>(new AuthToken(token, user.getId()), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        }));
        return errors;
    }

}





/*
- Auth Controller --> CONTROLLER
        - Global(Diary Exception Handler) --> CONTROLLER

        - Account Creation Request --> DTOs
        - Login Request --> DTOs

        USER SERVICE IMPLEMENTATION EXTENDS 2 PARENTS
        * - public class UserServiceImpl implements UserService, UserDetailsService {

        - API Error  --> EXCEPTION
        - UserInExistenceException --> EXCEPTION
        - UserNotFoundException --> EXCEPTION

        IMPORT THE LAST METHOD ON THIS PAGE FOR THE GLOBAL EXCEPTION HANDLER

        UNDER USER SERVICE IMPLEMENTATION
          private Collection<? extends GrantedAuthority> getAuthorities(Set<Role> roles

        https://www.javatpoint.com/spring-security-features
        - ** --> SECURITY
        Configuration Points
        - Link to the controller endpoints */
