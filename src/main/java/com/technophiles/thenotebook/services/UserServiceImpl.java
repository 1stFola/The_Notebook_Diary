package com.technophiles.thenotebook.services;

import com.sun.istack.NotNull;
import com.technophiles.thenotebook.data.models.Diary;
import com.technophiles.thenotebook.data.models.Role;
import com.technophiles.thenotebook.data.models.User;
import com.technophiles.thenotebook.data.repositories.UserRepository;
import com.technophiles.thenotebook.dtos.UserDTO;
import com.technophiles.thenotebook.dtos.requests.userCenteredRequests.AccountCreationRequest;
import com.technophiles.thenotebook.exceptions.NoteBookAppException;
import com.technophiles.thenotebook.exceptions.UserInExistenceException;
import com.technophiles.thenotebook.exceptions.UserNotFoundException;
import com.technophiles.thenotebook.security.ApplicationSecurityConfig;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class UserServiceImpl implements UserService, UserDetailsService {
    
    private UserRepository userRepository;

    private ModelMapper mapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.mapper = new ModelMapper();
    }

    @Override
    public UserDTO createAccount(AccountCreationRequest request) throws NoteBookAppException {
        Optional<User> user = userRepository.findUserByEmail(request.getEmail());
        if(user.isPresent()){
            throw new NoteBookAppException("User already exists");
        }
        User newUser = new User(request.getEmail(), request.getUsername() , request.getPassword());
        User savedUser = userRepository.save(newUser);
        return mapper.map(savedUser, UserDTO.class);
    }

    @Override
    public Diary addADiary(@NotNull Long id, @NotNull Diary diary) throws NoteBookAppException {
        Optional<User> user = Optional.ofNullable(userRepository.findById(id).orElseThrow(() -> new NoteBookAppException("User not found")));
        user.get().addDiary(diary);
        userRepository.save(user.get());
        return diary;
    }

    @SneakyThrows
    @Override
    public User findById(String userId) {
        return userRepository.findById(Long.valueOf(userId)).orElseThrow(() -> new NoteBookAppException("user does not exist"));

//        return userRepository.findById(Long.valueOf(userId)).get();
    }

    @Override
    public boolean deleteUser(User user) {
        userRepository.delete(user);
        return true;
    }

    @Override
    public User findUserByEmail(String email) throws UserNotFoundException {
        return userRepository.findUserByEmail(email).orElseThrow(()-> new UserNotFoundException("User does not exist"));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(email).orElseThrow(()-> new UserNotFoundException("User does not exist"));
        org.springframework.security.core.userdetails.User returnedUser = new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthorities(user.getRoles()));
        return returnedUser;
//        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Set<Role> roles) {
        Collection<? extends SimpleGrantedAuthority> authorities = roles.stream().map(
                role -> new SimpleGrantedAuthority(role.getRoleType().name())
        ).collect(Collectors.toSet());
        return authorities;
    }

}
