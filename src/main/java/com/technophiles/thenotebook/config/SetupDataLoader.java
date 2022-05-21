package com.technophiles.thenotebook.config;

import com.technophiles.thenotebook.data.models.Role;
import com.technophiles.thenotebook.data.models.RoleType;
import com.technophiles.thenotebook.data.models.User;
import com.technophiles.thenotebook.data.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (userRepository.findUserByEmail("adminuser@gmail.com").isEmpty()){
            User user = new User("adminuser@gmail.com", passwordEncoder.encode("password1234#"));
            user.addRole(new Role(RoleType.ROLE_ADMIN));
            userRepository.save(user);
        }
    }
}