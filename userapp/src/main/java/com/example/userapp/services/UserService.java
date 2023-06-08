package com.example.userapp.services;

import com.example.common.services.BaseService;
import com.example.userapp.models.User;
import com.example.userapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService extends BaseService<User, String> implements UserDetailsService {

    @Autowired
    public UserService(UserRepository userRepository) {
        super(userRepository);
    }

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        return ((UserRepository) repository).findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
