package com.example.diplomskiBackend.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class SubscriptionDTO {

    private Long id;

    private List<UserDTO> users;

}
