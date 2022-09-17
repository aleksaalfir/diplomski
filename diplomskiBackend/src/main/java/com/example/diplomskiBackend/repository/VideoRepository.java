package com.example.diplomskiBackend.repository;

import com.example.diplomskiBackend.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VideoRepository extends JpaRepository<Video, Long> {

    List<Video> findVideosByUserIdAndPrivateVideoFalse(Long id);
    List<Video> findVideosByPrivateVideoFalse();
    List<Video> findVideosByUserId(Long id);

    List<Video> findVideosByPrivateVideoFalseAndNameContaining(String search);

}
