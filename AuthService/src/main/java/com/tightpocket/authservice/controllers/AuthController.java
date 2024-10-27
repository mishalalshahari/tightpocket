package com.tightpocket.authservice.controllers;

import com.tightpocket.authservice.entities.RefreshToken;
import com.tightpocket.authservice.models.UserInfoDTO;
import com.tightpocket.authservice.responses.JwtResponseDTO;
import com.tightpocket.authservice.services.JwtService;
import com.tightpocket.authservice.services.RefreshTokenService;
import com.tightpocket.authservice.services.implementation.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class AuthController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @PostMapping("auth/v1/register")
    public ResponseEntity register(@RequestBody UserInfoDTO userInfoDTO) {
        try {
            Boolean isRegistered = userDetailsServiceImpl.registerUser(userInfoDTO);
            if(Boolean.FALSE.equals(isRegistered)) {
                return new ResponseEntity<>("Already exists!", HttpStatus.BAD_REQUEST);
            }
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(userInfoDTO.getUsername());
            String jwtToken = jwtService.GenerateToken(userInfoDTO.getUsername());
            return new ResponseEntity<>(JwtResponseDTO
                    .builder()
                    .accessToken(jwtToken)
                    .token(refreshToken
                            .getToken()
                    )
                    .build(), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Exception in User Service", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
