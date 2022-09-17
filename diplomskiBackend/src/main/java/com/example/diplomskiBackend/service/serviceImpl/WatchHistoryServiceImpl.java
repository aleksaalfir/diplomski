package com.example.diplomskiBackend.service.serviceImpl;

import com.example.diplomskiBackend.dto.WatchHistoryDTO;
import com.example.diplomskiBackend.entity.User;
import com.example.diplomskiBackend.entity.Video;
import com.example.diplomskiBackend.mapper.WatcHistoryMapper;
import com.example.diplomskiBackend.repository.UserRepository;
import com.example.diplomskiBackend.repository.VideoRepository;
import com.example.diplomskiBackend.repository.WatchHistoryRepository;
import com.example.diplomskiBackend.service.VideoService;
import com.example.diplomskiBackend.service.WatchHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class WatchHistoryServiceImpl implements WatchHistoryService {

    @Autowired
    private WatchHistoryRepository watchHistoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VideoRepository videoRepository;


    @Override
    public WatchHistoryDTO getOne() {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findUserByUsername(username);
        return WatcHistoryMapper.mapDTO(user.getWatchHistory());
    }

    @Override
    public boolean delete() {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findUserByUsername(username);
        if(user != null){
            user.getWatchHistory().setVideos(null);
            watchHistoryRepository.save(user.getWatchHistory());
            return true;
        }
        return false;
    }

    @Override
    public boolean addToWatcHistory(UUID id) {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findUserByUsername(username);
        Video video = videoRepository.findById(id).orElse(null);
        for (Video v:
             user.getWatchHistory().getVideos()) {
            if(video.getId() == v.getId()){
                return false;
            };
        }
        user.getWatchHistory().getVideos().add(video);
        watchHistoryRepository.save(user.getWatchHistory());
        return true;
    }
}
