package com.technophiles.thenotebook.dtos.requests.userCenteredRequests;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequest {

    private String email;
    private String password;

}

