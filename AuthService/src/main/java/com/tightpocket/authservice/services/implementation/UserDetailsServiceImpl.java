package com.tightpocket.authservice.services.implementation;

import com.tightpocket.authservice.entities.UserInfo;
import com.tightpocket.authservice.eventProducer.UserInfoProducer;
import com.tightpocket.authservice.models.UserInfoDTO;
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

import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;

@Component
@AllArgsConstructor
@Data
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private final UserRepo userRepo;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final UserInfoProducer userInfoProducer;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo user = userRepo.findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException(username + " not found!");
        }
        return new CustomUserDetailsService(user);
    }

    public UserInfo checkIfUserAlreadyExists(UserInfoDTO userInfoDTO) {
        return userRepo.findByUsername(userInfoDTO.getUsername());
    }

    public Boolean registerUser(UserInfoDTO userInfoDTO) {
        //define a function in utils to validate if userEmail and password is correct to register i.e. format, length, password strength
        userInfoDTO.setPassword(passwordEncoder.encode(userInfoDTO.getPassword()));
        if(Objects.nonNull(checkIfUserAlreadyExists(userInfoDTO))) {
            return false;
        }
        String userId = UUID.randomUUID().toString();
        userRepo.save(new UserInfo(userId, userInfoDTO.getUsername(), userInfoDTO.getPassword(), new HashSet<>()));

        //push event to queue
        userInfoProducer.sendEventToKafka(userInfoDTO);

        return true;
    }


}
