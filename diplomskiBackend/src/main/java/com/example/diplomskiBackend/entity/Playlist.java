package com.example.diplomskiBackend.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Boolean privatePlaylist;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "playlist_video",
            joinColumns = @JoinColumn(name = "playlist_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "video_id", referencedColumnName = "id"))
    private List<Video> videos;
}
