package com.example.diplomskiBackend.repository;

import com.example.diplomskiBackend.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface VideoRepository extends JpaRepository<Video, UUID> {

    List<Video> findVideosByUserIdAndPrivateVideoFalse(Long id);
    List<Video> findVideosByPrivateVideoFalse();
    List<Video> findVideosByUserId(Long id);
    List<Video> findVideosByPrivateVideoFalseAndNameContaining(String search);

}
