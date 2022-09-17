package com.example.diplomskiBackend.dto;

import com.example.diplomskiBackend.entity.EUserRole;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class AdministratorDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private String password;

    private String username;

    private String email;

    private EUserRole role;

    private Date birthDate;
}
