package com.example.diplomskiBackend.mapper;

import com.example.diplomskiBackend.dto.UserDTO;
import com.example.diplomskiBackend.dto.VideoCommentDTO;
import com.example.diplomskiBackend.dto.VideoDTO;
import com.example.diplomskiBackend.entity.User;
import com.example.diplomskiBackend.entity.Video;
import com.example.diplomskiBackend.entity.VideoComment;

import java.util.ArrayList;
import java.util.List;

public class VideoMapper {

    public static Video mapModel(VideoDTO videoDTO){
        User user = UserMapper.mapModel(videoDTO.getUser());

        return Video.builder()
                .name(videoDTO.getName())
                .user(user)
                .privateVideo(videoDTO.getPrivateVideo())
                .ageRestricted(videoDTO.getAgeRestricted())
                .description(videoDTO.getDescription())
                .videoPath(videoDTO.getVideoPath())
                .views(videoDTO.getViews())
                .date(videoDTO.getDate())
//                .videoComments()
                .build();
    }

    public static VideoDTO mapDTO(Video video){
        List<VideoCommentDTO> videoCommentDTOS = VideoCommentMapper.mapListDTO(video.getVideoComments());
        return VideoDTO.builder()
                .id(video.getId())
                .name(video.getName())
                .user(UserMapper.mapDTOWithoutPassword(video.getUser()))
                .privateVideo(video.getPrivateVideo())
                .ageRestricted(video.getAgeRestricted())
                .description(video.getDescription())
                .videoPath(video.getVideoPath())
                .views(video.getViews())
                .date(video.getDate())
                .privatePassword(video.getPrivatePassword())
                .fileName(video.getFileName())
                .videoComments(videoCommentDTOS)
                .build();
    }

    public static List<VideoDTO> mapListDTO(List<Video> videos){
        List<VideoDTO> dtos = new ArrayList<>();
        for (Video video:
             videos) {
            dtos.add(mapDTO(video));
        }
        return dtos;
    }

    public static List<VideoDTO> mapListDTOForCard(List<Video> videos){
        List<VideoDTO> dtos = new ArrayList<>();
        for (Video video:
                videos) {
            dtos.add(mapDTO(video));
        }
        return dtos;
    }

}
