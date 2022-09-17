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
public class WatchHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(name = "watch_history_videos",
            joinColumns = @JoinColumn(name = "watch_history_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "video_id", referencedColumnName = "id"))
    private List<Video> videos;

}
