package com.example.diplomskiBackend.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class VideoCommentDTO {

    private Long id;

    private String comment;

    private UserDTO user;

    private Date date;

}
