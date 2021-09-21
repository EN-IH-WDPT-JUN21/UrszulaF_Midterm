package com.ironhack.midterm.service.impl;

import com.ironhack.midterm.Security.CustomUserDetails;
import com.ironhack.midterm.dao.user.User;
import com.ironhack.midterm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if(!user.isPresent()){
            throw new UsernameNotFoundException("User not found with username" + username);
        }
        CustomUserDetails customUserDetails = new CustomUserDetails(user.get());
        return customUserDetails;
    }
}
