package com.example.diplomskiBackend.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToOne
    private User user;

    private Boolean privateVideo;

    private Boolean ageRestricted;

    private String description;

    private String videoPath;

    private Integer views;

    private Date date;

    @OneToMany(cascade = CascadeType.ALL)
    private List<VideoComment> videoComments;

    private String privatePassword;

    private String fileName;

    public Video(String name, User user, Boolean privateVideo, Boolean ageRestricted, String description, String videoPath, Integer views, Date date, List<VideoComment> videoComments, String privatePassword, String fileName) {
        this.name = name;
        this.user = user;
        this.privateVideo = privateVideo;
        this.ageRestricted = ageRestricted;
        this.description = description;
        this.videoPath = videoPath;
        this.views = views;
        this.date = date;
        this.videoComments = videoComments;
        this.privatePassword = privatePassword;
        this.fileName = fileName;
    }
}