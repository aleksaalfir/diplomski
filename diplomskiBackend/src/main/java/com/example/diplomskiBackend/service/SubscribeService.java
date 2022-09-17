package com.example.diplomskiBackend.service;

import com.example.diplomskiBackend.dto.VideoDTO;

import java.util.List;

public interface SubscribeService {

    List<VideoDTO> getAllSubscriptionVideos();

    boolean subscribe(Long id);

    boolean checkSubscription(Long id);
}
