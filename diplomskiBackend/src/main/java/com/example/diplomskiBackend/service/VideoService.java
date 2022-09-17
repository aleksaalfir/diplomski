package com.example.diplomskiBackend.service;

import com.example.diplomskiBackend.dto.RequestVideoDTO;
import com.example.diplomskiBackend.dto.VideoDTO;
import com.example.diplomskiBackend.entity.Video;

import java.util.List;
import java.util.UUID;

public interface VideoService {

    List<VideoDTO> getAll();
    List<VideoDTO> findVideosByUserIdAndPrivateVideoFalse(Long id);
    VideoDTO findOne(UUID id);
    List<VideoDTO> findAllNonPrivateVideos();
    void incrementVideoViews(UUID id);
    boolean delete(UUID id);
    VideoDTO update(UUID id, VideoDTO videoDTO);
    List<VideoDTO> findVideosByUserId(Long id);
    UUID create(RequestVideoDTO requestVideoDTO);
    boolean liked(UUID id);
    boolean checkLiked(UUID id);
    List<VideoDTO> findVideosByPrivateVideoFalseAndNameContains(String search);


}
