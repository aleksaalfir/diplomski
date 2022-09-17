package com.example.diplomskiBackend.repository;

import com.example.diplomskiBackend.entity.LikedVideos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikedVideosRepository extends JpaRepository<LikedVideos, Long> {
}
