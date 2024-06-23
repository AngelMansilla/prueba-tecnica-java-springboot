package com.mercadona.eanservice.service.impl;

import com.mercadona.eanservice.service.UserService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collections;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if ("user".equals(username)) {
            return new User("user", new BCryptPasswordEncoder().encode("password"), Collections.emptyList());
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}
