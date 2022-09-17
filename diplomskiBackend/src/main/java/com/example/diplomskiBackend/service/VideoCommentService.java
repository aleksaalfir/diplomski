package com.example.diplomskiBackend.service;

import com.example.diplomskiBackend.dto.CommentRequestDTO;

public interface VideoCommentService {

    Long save(CommentRequestDTO commentRequestDTO);
}
