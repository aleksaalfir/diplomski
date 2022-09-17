package com.example.diplomskiBackend.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Builder
@Data
@ToString
public class CommentRequestDTO {

    Long videoId;

    String comment;

}
