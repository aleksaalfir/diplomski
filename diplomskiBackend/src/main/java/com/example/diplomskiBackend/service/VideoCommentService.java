package com.example.diplomskiBackend.service;

import com.example.diplomskiBackend.dto.CommentRequestDTO;

import java.util.UUID;

public interface VideoCommentService {

    UUID save(CommentRequestDTO commentRequestDTO);
}
