package com.example.diplomskiBackend.service.serviceImpl;

import com.example.diplomskiBackend.dto.PlaylistDTO;
import com.example.diplomskiBackend.dto.PlaylistRequestDTO;
import com.example.diplomskiBackend.entity.Playlist;
import com.example.diplomskiBackend.entity.User;
import com.example.diplomskiBackend.entity.Video;
import com.example.diplomskiBackend.mapper.PlaylistMapper;
import com.example.diplomskiBackend.repository.PlaylistRepository;
import com.example.diplomskiBackend.repository.UserRepository;
import com.example.diplomskiBackend.repository.VideoRepository;
import com.example.diplomskiBackend.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlaylistServiceImpl implements PlaylistService {

    @Autowired
    private PlaylistRepository playlistRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VideoRepository videoRepository;

    @Override
    public PlaylistDTO findOne(Long id) {
        Playlist playlist = playlistRepository.findById(id).orElse(null);
        return PlaylistMapper.mapDTO(playlist);
    }

    @Override
    public List<PlaylistDTO> findPlaylistsByUser() {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findUserByUsername(username);
        return PlaylistMapper.mapListDTO(user.getPlaylist());
    }

    @Override
    public boolean delete(Long id) {
        Playlist playlist = playlistRepository.findById(id).orElse(null);
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findUserByUsername(username);
        if(playlist != null){
            playlist.setVideos(null);
            user.getPlaylist().remove(playlist);
            userRepository.save(user);
            playlistRepository.delete(playlist);
            return true;
        }
        return false;
    }

    @Override
    public Long create(PlaylistRequestDTO playlistRequestDTO) {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findUserByUsername(username);
        Playlist playlist = new Playlist();
        playlist.setPrivatePlaylist(playlistRequestDTO.getPrivatePlaylist());
        playlist.setName(playlistRequestDTO.getName());
        playlist.setVideos(new ArrayList<>());
        user.getPlaylist().add(playlist);
        userRepository.save(user);
        return playlist.getId();
    }

    @Override
    public boolean addVideoToPlaylist(Long playlistId, Long videoId) {
        Video video = videoRepository.findById(videoId).orElse(null);
        Playlist playlist = playlistRepository.findById(playlistId).orElse(null);
        for (Video vi:
                playlist.getVideos()) {
            if(vi.getId() == videoId){
                return false;
            }
        }
        playlist.getVideos().add(video);
        playlistRepository.save(playlist);
        return true;
    }
}
