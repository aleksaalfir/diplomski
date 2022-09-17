package com.example.diplomskiBackend.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LikedVideos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(name = "liked_videos_videos",
            joinColumns = @JoinColumn(name = "liked_videos_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "video_id", referencedColumnName = "id"))
    private List<Video> videos;
}
