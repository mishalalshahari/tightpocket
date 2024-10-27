package com.tightpocket.authservice.models;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.tightpocket.authservice.entities.UserInfo;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserInfoDTO extends UserInfo {

    private String firstName;

    private String lastName;

    private Long phoneNumber;

    private String email;
}