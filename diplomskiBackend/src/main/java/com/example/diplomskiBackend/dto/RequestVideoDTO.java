package com.example.diplomskiBackend.dto;

import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@ToString
public class RequestVideoDTO {

    private UUID id;

    private String name;

    private Boolean privateVideo;

    private Boolean ageRestricted;

    private String description;

    private String videoPath;

    private String privatePassword;

    private String fileName;

}
