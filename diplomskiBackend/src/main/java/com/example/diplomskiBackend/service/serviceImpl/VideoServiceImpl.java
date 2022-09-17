package com.example.diplomskiBackend.service.serviceImpl;

import com.example.diplomskiBackend.dto.RequestVideoDTO;
import com.example.diplomskiBackend.dto.VideoDTO;
import com.example.diplomskiBackend.entity.*;
import com.example.diplomskiBackend.mapper.VideoMapper;
import com.example.diplomskiBackend.repository.PlaylistRepository;
import com.example.diplomskiBackend.repository.UserRepository;
import com.example.diplomskiBackend.repository.VideoRepository;
import com.example.diplomskiBackend.repository.WatchHistoryRepository;
import com.example.diplomskiBackend.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.util.ArrayUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private WatchHistoryRepository watchHistoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<VideoDTO> getAll() {
        List<Video> videos = videoRepository.findAll();
        return VideoMapper.mapListDTOForCard(videos);
    }

    @Override
    public List<VideoDTO> findVideosByUserIdAndPrivateVideoFalse(Long id) {
        List<Video> videos = videoRepository.findVideosByUserIdAndPrivateVideoFalse(id);
        return VideoMapper.mapListDTO(videos);
    }

    @Override
    public VideoDTO findOne(Long id) {
        return VideoMapper.mapDTO(videoRepository.findById(id).orElse(null));
    }

    @Override
    public List<VideoDTO> findAllNonPrivateVideos() {
        List<VideoDTO> dtos = VideoMapper.mapListDTO(videoRepository.findVideosByPrivateVideoFalse());
        Collections.shuffle(dtos);
        return dtos;
    }

    @Override
    public void incrementVideoViews(Long id) {
        Video video = videoRepository.findById(id).orElse(null);
        video.setViews(video.getViews() + 1);
        videoRepository.save(video);
    }

    @Override
    public boolean delete(Long id) {

        Video video = videoRepository.findById(id).orElse(null);
        if(video != null){
            removeVideoFromPlaylists(video);
            removeVideoFromWatchHistory(video);
            videoRepository.delete(video);
            return true;
        }
        return false;
    }

    @Override
    public VideoDTO update(Long id, VideoDTO videoDTO) {
        Video video = videoRepository.findById(id).orElse(null);
        video.setId(id);
        video.setName(videoDTO.getName());
        video.setDescription(videoDTO.getDescription());
        video.setAgeRestricted(videoDTO.getAgeRestricted());
        video.setPrivateVideo(videoDTO.getPrivateVideo());
        video.setPrivatePassword(video.getPrivatePassword());
        videoRepository.save(video);
        return videoDTO;
    }

    @Override
    public List<VideoDTO> findVideosByUserId(Long id) {
        List<Video> videos = videoRepository.findVideosByUserId(id);
        return VideoMapper.mapListDTO(videos);
    }

    @Override
    public Long create(RequestVideoDTO requestVideoDTO) {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findUserByUsername(username);

        Video video = new Video(requestVideoDTO.getName(),
                user, requestVideoDTO.getPrivateVideo(),
                requestVideoDTO.getAgeRestricted(),
                requestVideoDTO.getDescription(),
                requestVideoDTO.getVideoPath(),
                0,
                new Date(),
                new ArrayList<>(),
                requestVideoDTO.getPrivatePassword(),
                requestVideoDTO.getFileName());

        Long id = videoRepository.save(video).getId();

        return id;
    }

    @Override
    public boolean liked(Long id) {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findUserByUsername(username);
        Video video = videoRepository.findById(id).orElse(null);
        for (Video vi:
             user.getLikedVideos().getVideos()) {
            if(vi.getId() == id){
                user.getLikedVideos().getVideos().remove(video);
                userRepository.save(user);
                return false;
            }
        }
        user.getLikedVideos().getVideos().add(video);
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean checkLiked(Long id) {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findUserByUsername(username);
        Video video = videoRepository.findById(id).orElse(null);
        for (Video vi:
                user.getLikedVideos().getVideos()) {
            if(vi.getId() == id){
                return true;
            }
        }
        return false;
    }

    @Override
    public List<VideoDTO> findVideosByPrivateVideoFalseAndNameContains(String search) {
        return VideoMapper.mapListDTO(videoRepository.findVideosByPrivateVideoFalseAndNameContaining(search));
    }


    private void removeVideoFromPlaylists(Video video){
        List<Playlist> playlists = playlistRepository.findPlaylistsByVideo(video.getId());
        if(playlists.size() >= 1){
            for (Playlist playlist:
                    playlists) {
                playlist.getVideos().remove(video);
                playlistRepository.save(playlist);
            }
        }
    }

    private void removeVideoFromWatchHistory(Video video){
        List<WatchHistory> watchHistories = watchHistoryRepository.findWatchHistoriesByVideo(video.getId());
        if (watchHistories.size() >= 1){
            for (WatchHistory wh:
                    watchHistories) {
                wh.getVideos().remove(video);
                watchHistoryRepository.save(wh);
            }
        }
    }


}
