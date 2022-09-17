package com.example.diplomskiBackend.service.serviceImpl;

import com.example.diplomskiBackend.dto.VideoDTO;
import com.example.diplomskiBackend.entity.User;
import com.example.diplomskiBackend.entity.Video;
import com.example.diplomskiBackend.mapper.VideoMapper;
import com.example.diplomskiBackend.repository.UserRepository;
import com.example.diplomskiBackend.repository.VideoRepository;
import com.example.diplomskiBackend.service.SubscribeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubscribeServiceImpl implements SubscribeService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VideoRepository videoRepository;

    @Override
    public List<VideoDTO> getAllSubscriptionVideos() {

        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findUserByUsername(username);
        List<Video> videos = getVideos(user);

        return VideoMapper.mapListDTO(videos);
    }

    private List<Video> getVideos(User user){
        List<Video> videos = new ArrayList<>();
        for (User u:
                user.getSubscription().getUsers()) {
            List<Video> userVideos = videoRepository.findVideosByUserIdAndPrivateVideoFalse(u.getId());
            videos.addAll(userVideos);
        }
        return videos;
    }

    @Override
    public boolean subscribe(Long id) {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User loggedUser = userRepository.findUserByUsername(username);
        User subscriptionUser = userRepository.findById(id).orElse(null);
        for (User u:
                loggedUser.getSubscription().getUsers()) {
            if(u == subscriptionUser) {
                loggedUser.getSubscription().getUsers().remove(subscriptionUser);
                subscriptionUser.setSubscriptionNumber(subscriptionUser.getSubscriptionNumber() - 1);
                userRepository.save(subscriptionUser);
                userRepository.save(loggedUser);
                return false;
            }
        }
        loggedUser.getSubscription().getUsers().add(subscriptionUser);
        subscriptionUser.setSubscriptionNumber(subscriptionUser.getSubscriptionNumber() + 1);
        userRepository.save(subscriptionUser);
        userRepository.save(loggedUser);
        return true;
    }

    @Override
    public boolean checkSubscription(Long id) {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User loggedUser = userRepository.findUserByUsername(username);
        User subscriptionUser = userRepository.findById(id).orElse(null);
        for (User u:
                loggedUser.getSubscription().getUsers()) {
            if(u == subscriptionUser) return true;
        }
        return false;
    }
}
