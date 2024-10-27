package com.tightpocket.authservice.services.implementation;

import com.tightpocket.authservice.entities.UserInfo;
import com.tightpocket.authservice.repositories.UserRepo;
import com.tightpocket.authservice.services.CustomUserDetailsService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Data
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private final UserRepo userRepo;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo user = userRepo.findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException(username + " not found!");
        }
        return new CustomUserDetailsService(user);
    }
}
