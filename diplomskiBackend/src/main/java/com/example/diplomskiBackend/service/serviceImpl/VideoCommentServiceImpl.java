package com.example.diplomskiBackend.service.serviceImpl;

import com.example.diplomskiBackend.dto.CommentRequestDTO;
import com.example.diplomskiBackend.entity.User;
import com.example.diplomskiBackend.entity.Video;
import com.example.diplomskiBackend.entity.VideoComment;
import com.example.diplomskiBackend.repository.UserRepository;
import com.example.diplomskiBackend.repository.VideoCommentRepository;
import com.example.diplomskiBackend.repository.VideoRepository;
import com.example.diplomskiBackend.service.UserService;
import com.example.diplomskiBackend.service.VideoCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class VideoCommentServiceImpl implements VideoCommentService {

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UUID save(CommentRequestDTO commentRequestDTO) {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findUserByUsername(username);
        Video video = videoRepository.findById(commentRequestDTO.getVideoId()).orElse(null);
        VideoComment videoComment = new VideoComment(commentRequestDTO.getComment(), user, new Date());
        video.getVideoComments().add(videoComment);
        videoRepository.save(video);
        return video.getId();
    }
}
