package com.example.diplomskiBackend.service;

import com.example.diplomskiBackend.dto.PlaylistDTO;
import com.example.diplomskiBackend.dto.PlaylistRequestDTO;

import java.util.List;

public interface PlaylistService {

    PlaylistDTO findOne(Long id);
    List<PlaylistDTO> findPlaylistsByUser();
    boolean delete(Long id);
    Long create(PlaylistRequestDTO playlistRequestDTO);
    boolean addVideoToPlaylist(Long playlistId, Long videoId);

}
