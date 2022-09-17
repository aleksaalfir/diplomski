package com.example.diplomskiBackend.controller;

import com.example.diplomskiBackend.dto.CommentRequestDTO;
import com.example.diplomskiBackend.service.VideoCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/video-comment")
public class VideoCommentController {

    @Autowired
    private VideoCommentService videoCommentService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<UUID> saveComment(@RequestBody CommentRequestDTO commentRequestDTO){
        UUID id = videoCommentService.save(commentRequestDTO);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
