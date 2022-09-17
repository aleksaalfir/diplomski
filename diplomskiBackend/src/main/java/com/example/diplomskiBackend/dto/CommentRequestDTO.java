package com.example.diplomskiBackend.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.UUID;

@Builder
@Data
@ToString
public class CommentRequestDTO {

    UUID videoId;

    String comment;

}
