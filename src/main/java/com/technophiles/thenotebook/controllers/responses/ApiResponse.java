package com.technophiles.thenotebook.controllers.responses;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ApiResponse {
    private Object payload;
    private String message;
    private boolean isSuccessful;
    private int statusCode;
}
