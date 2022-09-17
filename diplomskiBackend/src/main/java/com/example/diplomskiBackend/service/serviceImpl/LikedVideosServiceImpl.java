package com.example.diplomskiBackend.service.serviceImpl;

import com.example.diplomskiBackend.dto.VideoDTO;
import com.example.diplomskiBackend.entity.User;
import com.example.diplomskiBackend.mapper.VideoMapper;
import com.example.diplomskiBackend.repository.UserRepository;
import com.example.diplomskiBackend.service.LikedVideosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikedVideosServiceImpl implements LikedVideosService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<VideoDTO> getAllByLoggedUser() {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findUserByUsername(username);
        return VideoMapper.mapListDTO(user.getLikedVideos().getVideos());
    }
}
