package com.example.diplomskiBackend.dto;

import lombok.*;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class VideoDTO {

    private Long id;

    private String name;

    private UserDTO user;

    private Boolean privateVideo;

    private Boolean ageRestricted;

    private String description;

    private String videoPath;

    private Integer views;

    private Date date;

    private List<VideoCommentDTO> videoComments;

    private String privatePassword;

    private String fileName;

    public VideoDTO(String name, Boolean privateVideo, Boolean ageRestricted, String description, String privatePassword, String fileName) {
        this.name = name;
        this.privateVideo = privateVideo;
        this.ageRestricted = ageRestricted;
        this.description = description;
        this.privatePassword = privatePassword;
        this.fileName = fileName;
    }
}
