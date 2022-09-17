package com.example.diplomskiBackend.dto;

import com.example.diplomskiBackend.entity.EUserRole;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class UserDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private String password;

    private String username;

    private String email;

    private EUserRole role;

    private Date birthDate;

    private String channelName;

    private Integer subscriptionNumber;

    private LikedVideosDTO likedVideos;

    private WatchHistoryDTO watchHistory;

    private List<PlaylistDTO> playlist;

    private SubscriptionDTO subscription;
}
