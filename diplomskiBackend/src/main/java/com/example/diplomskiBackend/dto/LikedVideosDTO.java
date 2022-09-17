package com.example.diplomskiBackend.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class LikedVideosDTO {

    private Long id;

    private List<VideoDTO> videos;
}
