package com.technophiles.thenotebook.dtos.requests.userCenteredRequests;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountCreationRequest {
    private String email;
    private String username;
    private String password;
}
