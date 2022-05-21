package com.technophiles.thenotebook.services;

import com.sun.istack.NotNull;
import com.technophiles.thenotebook.data.models.Diary;
import com.technophiles.thenotebook.data.models.User;
import com.technophiles.thenotebook.dtos.UserDTO;
import com.technophiles.thenotebook.dtos.requests.userCenteredRequests.AccountCreationRequest;
import com.technophiles.thenotebook.exceptions.NoteBookAppException;
import com.technophiles.thenotebook.exceptions.UserNotFoundException;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface UserService {
    UserDTO createAccount(AccountCreationRequest request) throws NoteBookAppException;
    
    Diary addADiary(Long id, Diary diary) throws NoteBookAppException;


    User findById(String userId);

    boolean deleteUser(User user);

    User findUserByEmail(String email) throws UserNotFoundException;

    List<User> getAllUsers();

}
