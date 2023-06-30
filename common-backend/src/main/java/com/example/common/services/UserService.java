package com.example.common.services;

import com.example.common.models.user.User;
import com.example.common.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

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

    public boolean hasId(UUID id){
        String username = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        Optional<User> user = ((UserRepository)repository).findByEmail(username);
        return user.isPresent() && user.get().getId().equals(id.toString());
    }
}
