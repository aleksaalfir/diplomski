package com.example.diplomskiBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class PlaylistRequestDTO {

    private String name;

    private Boolean privatePlaylist;

}
