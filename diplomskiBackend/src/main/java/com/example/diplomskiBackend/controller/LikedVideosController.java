package com.example.diplomskiBackend.controller;

import com.example.diplomskiBackend.dto.VideoDTO;
import com.example.diplomskiBackend.service.LikedVideosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/liked-videos")
public class LikedVideosController {

    @Autowired
    private LikedVideosService likedVideosService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public ResponseEntity<List<VideoDTO>> getAll(){
        return new ResponseEntity<>(likedVideosService.getAllByLoggedUser(), HttpStatus.OK);
    };


}
