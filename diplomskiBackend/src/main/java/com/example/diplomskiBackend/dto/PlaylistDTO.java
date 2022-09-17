package com.example.diplomskiBackend.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class PlaylistDTO {

    private Long id;

    private String name;

    private Boolean privatePlaylist;

    private List<VideoDTO> videos;

}
