package com.example.diplomskiBackend.mapper;

import com.example.diplomskiBackend.dto.PlaylistDTO;
import com.example.diplomskiBackend.entity.Playlist;

import java.util.ArrayList;
import java.util.List;

public class PlaylistMapper {

    public static PlaylistDTO mapDTO(Playlist playlist){
        return PlaylistDTO.builder()
                .id(playlist.getId())
                .privatePlaylist(playlist.getPrivatePlaylist())
                .name(playlist.getName())
                .videos(VideoMapper.mapListDTO(playlist.getVideos()))
                .build();
    }

    public static List<PlaylistDTO> mapListDTO(List<Playlist> playlists){
        List<PlaylistDTO> dtos = new ArrayList<>();
        for (Playlist playlist:
             playlists) {
            dtos.add(mapDTO(playlist));
        }
        return dtos;
    }

}
