package com.example.diplomskiBackend.service;

import com.example.diplomskiBackend.dto.VideoDTO;

import java.util.List;

public interface LikedVideosService {

    List<VideoDTO> getAllByLoggedUser();

}
