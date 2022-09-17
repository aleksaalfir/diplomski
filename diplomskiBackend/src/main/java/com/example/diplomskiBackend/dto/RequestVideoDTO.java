package com.example.diplomskiBackend.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class RequestVideoDTO {

    private String name;

    private Boolean privateVideo;

    private Boolean ageRestricted;

    private String description;

    private String videoPath;

    private String privatePassword;

    private String fileName;

}
