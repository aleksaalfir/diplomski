package com.example.diplomskiBackend.repository;

import com.example.diplomskiBackend.entity.VideoComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoCommentRepository extends JpaRepository<VideoComment, Long> {
}
