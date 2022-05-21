package com.technophiles.thenotebook.controllers;


import com.technophiles.thenotebook.controllers.responses.ApiResponse;
import com.technophiles.thenotebook.data.models.Diary;
import com.technophiles.thenotebook.data.models.User;
import com.technophiles.thenotebook.exceptions.NoteBookAppException;
import com.technophiles.thenotebook.services.DiaryService;
import com.technophiles.thenotebook.services.UserService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@Slf4j
@Controller
@RestController
@NoArgsConstructor
@RequestMapping("/api/v2/notebook/diaries")
public class DiaryController {

    private DiaryService diaryService;

    private UserService userService;

    public DiaryController(DiaryService diaryService, UserService userService) {
        this.diaryService = diaryService;
        this.userService = userService;
    }

    @PostMapping("/createDiary/{userId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<?> createDiary(@RequestParam String title, @PathVariable("userId") String userId) throws NoteBookAppException {
        try {
            User user = userService.findById(userId);
            Diary diary = diaryService.createDiary(title,user);
            Diary savedDiary = userService.addADiary(Long.valueOf(userId), diary);
            ApiResponse apiResponse = ApiResponse.builder()
                    .payload(savedDiary)
                    .isSuccessful(true)
                    .message("diary added successfully")
                    .statusCode(201)
                    .build();
            return new ResponseEntity<>(userService.addADiary(Long.valueOf(userId), diary), HttpStatus.CREATED);

        }catch (NoteBookAppException e) {
            ApiResponse apiResponse = ApiResponse.builder()
                    .message(e.getMessage())
                    .isSuccessful(false)
                    .statusCode(404)
                    .build();
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }

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
