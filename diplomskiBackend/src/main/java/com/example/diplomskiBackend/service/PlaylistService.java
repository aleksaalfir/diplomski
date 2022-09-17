package com.example.diplomskiBackend.service;

import com.example.diplomskiBackend.dto.PlaylistDTO;
import com.example.diplomskiBackend.dto.PlaylistRequestDTO;

import java.util.List;
import java.util.UUID;

public interface PlaylistService {

    PlaylistDTO findOne(Long id);
    List<PlaylistDTO> findPlaylistsByUser();
    boolean delete(Long id);
    Long create(PlaylistRequestDTO playlistRequestDTO);
    boolean addVideoToPlaylist(Long playlistId, UUID videoId);

}
