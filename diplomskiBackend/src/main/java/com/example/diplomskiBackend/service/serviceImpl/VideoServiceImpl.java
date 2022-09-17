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

import java.util.*;

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
    public VideoDTO findOne(UUID id) {
        return VideoMapper.mapDTO(videoRepository.findById(id).orElse(null));
    }

    @Override
    public List<VideoDTO> findAllNonPrivateVideos() {
        List<VideoDTO> dtos = VideoMapper.mapListDTO(videoRepository.findVideosByPrivateVideoFalse());
        Collections.shuffle(dtos);
        return dtos;
    }

    @Override
    public void incrementVideoViews(UUID id) {
        Video video = videoRepository.findById(id).orElse(null);
        video.setViews(video.getViews() + 1);
        videoRepository.save(video);
    }

    @Override
    public boolean delete(UUID id) {

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
    public VideoDTO update(UUID id, VideoDTO videoDTO) {
        Video video = videoRepository.findById(id).orElse(null);
        video.setId(id);
        video.setName(videoDTO.getName());
        video.setPrivatePassword(video.getPrivatePassword());
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
    public UUID create(RequestVideoDTO requestVideoDTO) {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findUserByUsername(username);

        Video video = new Video(requestVideoDTO.getId(),
                requestVideoDTO.getName(),
                user,
                requestVideoDTO.getPrivateVideo(),
                requestVideoDTO.getAgeRestricted(),
                requestVideoDTO.getDescription(),
                requestVideoDTO.getVideoPath(),
                0,
                new Date(),
                new ArrayList<>(),
                requestVideoDTO.getPrivatePassword(),
                requestVideoDTO.getFileName());

        UUID id = videoRepository.save(video).getId();

        return id;
    }

    @Override
    public boolean liked(UUID id) {
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
    public boolean checkLiked(UUID id) {
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
        String uuidString = video.getId().toString();
        List<Playlist> playlists = playlistRepository.findPlaylistsByVideo(uuidString);
        System.out.println(playlists);
        if(playlists.size() >= 1){
            for (Playlist playlist:
                    playlists) {
                playlist.getVideos().remove(video);
                playlistRepository.save(playlist);
            }
        }
    }

    private void removeVideoFromWatchHistory(Video video){
        String uuidString = video.getId().toString();
        List<WatchHistory> watchHistories = watchHistoryRepository.findWatchHistoriesByVideo(uuidString);
        if (watchHistories.size() >= 1){
            for (WatchHistory wh:
                    watchHistories) {
                wh.getVideos().remove(video);
                watchHistoryRepository.save(wh);
            }
        }
    }


}
