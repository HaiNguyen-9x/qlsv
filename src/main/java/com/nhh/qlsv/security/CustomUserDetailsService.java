package com.nhh.qlsv.security;

import com.nhh.qlsv.exception.UserNotFoundException;
import com.nhh.qlsv.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        com.nhh.qlsv.entity.User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new UserNotFoundException("User", "email", email));
        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());
        return new User(user.getEmail(),
                user.getPassword(),
                Collections.singleton(authority));
    }
}
