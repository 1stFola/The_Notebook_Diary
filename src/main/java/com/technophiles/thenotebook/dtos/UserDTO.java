package com.technophiles.thenotebook.dtos;


import com.technophiles.thenotebook.data.models.Diary;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;
    private String email;
    private String username;
    private Set<Diary> diaries;
}
