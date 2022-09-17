package com.example.diplomskiBackend.mapper;

import com.example.diplomskiBackend.dto.LikedVideosDTO;
import com.example.diplomskiBackend.dto.VideoDTO;
import com.example.diplomskiBackend.entity.LikedVideos;

import java.util.List;

public class LikedVideosMapper {

    public static LikedVideosDTO mapDTO(LikedVideos likedVideos){
        List<VideoDTO> videoDTOS = VideoMapper.mapListDTO(likedVideos.getVideos());
        return LikedVideosDTO.builder()
                .id(likedVideos.getId())
                .videos(videoDTOS)
                .build();
    }
}
