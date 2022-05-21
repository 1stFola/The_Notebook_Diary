package com.technophiles.thenotebook.controllers.responses;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthToken {
    private String token;
    private Long id;
}
