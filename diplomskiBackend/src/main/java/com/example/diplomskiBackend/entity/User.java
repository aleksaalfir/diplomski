package com.example.diplomskiBackend.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@Table(name = "user")
public class User extends DefaultUser {

    private String channelName;

    private Integer subscriptionNumber;

    @OneToOne(cascade = {CascadeType.ALL})
    private LikedVideos likedVideos;

    @OneToOne(cascade = {CascadeType.ALL})
    private WatchHistory watchHistory;

    @OneToMany(cascade = {CascadeType.ALL})
    private List<Playlist> playlist;

    @OneToOne(cascade = {CascadeType.ALL})
    private Subscription subscription;

    public User(Long id, String firstName, String lastName, String password, String username, String email, EUserRole role, Date birthDate, String channelName, Integer subscriptionNumber, LikedVideos likedVideos, WatchHistory watchHistory, List<Playlist> playlist, Subscription subscription) {
        super(id, firstName, lastName, password, username, email, role, birthDate);
        this.channelName = channelName;
        this.subscriptionNumber = subscriptionNumber;
        this.likedVideos = likedVideos;
        this.watchHistory = watchHistory;
        this.playlist = playlist;
        this.subscription = subscription;
    }
}
