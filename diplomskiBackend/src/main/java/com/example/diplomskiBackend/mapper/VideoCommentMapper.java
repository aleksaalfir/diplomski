package com.example.diplomskiBackend.mapper;

import com.example.diplomskiBackend.dto.UserDTO;
import com.example.diplomskiBackend.dto.VideoCommentDTO;
import com.example.diplomskiBackend.entity.VideoComment;

import java.util.ArrayList;
import java.util.List;

public class VideoCommentMapper {

    public static VideoCommentDTO mapDTO(VideoComment videoComment){

        return VideoCommentDTO.builder()
                .id(videoComment.getId())
                .comment(videoComment.getComment())
                .user(UserMapper.mapDTOWithoutPassword(videoComment.getUser()))
                .date(videoComment.getDate())
                .build();
    }

    public static List<VideoCommentDTO> mapListDTO(List<VideoComment> videoComments){
        List<VideoCommentDTO> dtos = new ArrayList<>();
        for (VideoComment videoComment:
             videoComments) {
            dtos.add(mapDTO(videoComment));
        }
        return dtos;
    }
}
