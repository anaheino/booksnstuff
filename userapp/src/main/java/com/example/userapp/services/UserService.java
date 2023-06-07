package com.example.userapp.services;

import com.example.common.services.AbstractBaseService;
import com.example.userapp.models.User;
import com.example.userapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService extends AbstractBaseService<User, String> {

    @Autowired
    public UserService(UserRepository userRepository) {
        super(userRepository);
    }
}
