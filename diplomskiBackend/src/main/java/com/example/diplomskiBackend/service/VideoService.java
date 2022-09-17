package com.example.diplomskiBackend.service;

import com.example.diplomskiBackend.dto.RequestVideoDTO;
import com.example.diplomskiBackend.dto.VideoDTO;
import com.example.diplomskiBackend.entity.Video;

import java.util.List;

public interface VideoService {

    List<VideoDTO> getAll();
    List<VideoDTO> findVideosByUserIdAndPrivateVideoFalse(Long id);
    VideoDTO findOne(Long id);
    List<VideoDTO> findAllNonPrivateVideos();
    void incrementVideoViews(Long id);
    boolean delete(Long id);
    VideoDTO update(Long id, VideoDTO videoDTO);
    List<VideoDTO> findVideosByUserId(Long id);
    Long create(RequestVideoDTO requestVideoDTO);
    boolean liked(Long id);
    boolean checkLiked(Long id);
    List<VideoDTO> findVideosByPrivateVideoFalseAndNameContains(String search);


}
