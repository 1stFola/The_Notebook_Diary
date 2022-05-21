package com.technophiles.thenotebook.controllers;


import com.technophiles.thenotebook.controllers.responses.ApiResponse;
import com.technophiles.thenotebook.data.models.User;
import com.technophiles.thenotebook.dtos.UserDTO;
import com.technophiles.thenotebook.dtos.requests.userCenteredRequests.AccountCreationRequest;
import com.technophiles.thenotebook.exceptions.NoteBookAppException;
import com.technophiles.thenotebook.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RestController
@RequestMapping("/ap1/v2/notebook")
public class UserController {

    private UserService userService;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping("/login")
    public String login(){
        return "Welcome to my application";
    }

    @PostMapping("/users/createUser")
    public ResponseEntity<?> createUserAccount(@RequestBody @Valid @NotNull @NotBlank AccountCreationRequest request) throws NoteBookAppException {
        request.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
            UserDTO userDTO = userService.createAccount(request);
            ApiResponse response = ApiResponse.builder()
                    .payload(userDTO)
                    .isSuccessful(true)
                    .statusCode(201)
                    .message("User created successfully")
                    .build();
            return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/users/all")
    public ResponseEntity<?> getAllUsers(){
        List<User> users = userService.getAllUsers();
        ApiResponse apiResponse = ApiResponse.builder()
                .payload(users)
                .message("user returned successfully")
                .statusCode(200)
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException exception) {
//        Map<String, String> errors = new HashMap<>();
//        exception.getBindingResult().getAllErrors().forEach((error -> {
//            String fieldName = ((FieldError) error).getField();
//            String errorMessage = error.getDefaultMessage();
//            errors.put(fieldName, errorMessage);
//        }));
//        return errors;
//    }
}
