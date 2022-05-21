package com.technophiles.thenotebook.services;

import com.technophiles.thenotebook.data.models.User;
import com.technophiles.thenotebook.data.repositories.UserRepository;
import com.technophiles.thenotebook.dtos.requests.userCenteredRequests.AccountCreationRequest;
import com.technophiles.thenotebook.exceptions.NoteBookAppException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceMockTest {

    @Mock
    private UserRepository userRepository;

    private UserService userService;
    private AccountCreationRequest request;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;


    @BeforeEach
    void beforeEachTest() {
        this.userService = new UserServiceImpl(userRepository);
        request = AccountCreationRequest.builder()
                .email("wild@gmail.com")
                .username("Wild Cat")
                .password("dccomics")
                .build();
    }

    @Test
    void testThatCanCreateAUser() throws NoteBookAppException {

        when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.empty()); // Our Repo can confirm that user does not exist
        when(userRepository.save(any(User.class))).thenReturn(new User()); // Our Repo can save a User class and return what was saved
        userService.createAccount(request); // Our Service has a method that can process a create account request
        verify(userRepository, times(1)).findUserByEmail("wild@gmail.com"); // Verify that Repo was touched only 1 time with this email
        verify(userRepository, times(1)).save(userArgumentCaptor.capture());// Verify that Repo saved what was given only once
        User user1 = userArgumentCaptor.getValue(); // Puke what was given here for us to confirm
        assertThat(user1.getEmail()).isEqualTo(request.getEmail()); // Let's confirm that the email inside this is the same as what was given prior
        assertThat(user1.getPassword()).isEqualTo(request.getPassword()); // Let's confirm that the password inside this is the same as what was given prior

    }

    @AfterEach
    void tearDown() {
        userService = null;
    }
}
