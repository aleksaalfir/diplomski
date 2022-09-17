package com.example.diplomskiBackend.dto;

import com.example.diplomskiBackend.entity.EUserRole;
import lombok.Data;

import java.util.Date;

@Data
public class UpdateUserDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private Date birthDate;

    private String channelName;

}
