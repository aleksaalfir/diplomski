package com.example.diplomskiBackend.dto;

import com.example.diplomskiBackend.entity.EUserRole;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@Builder
@ToString
public class RegistrationRequestDTO {

    private String firstName;

    private String lastName;

    private String password;

    private String username;

    private String email;

    private Date birthDate;

    private String channelName;
}
