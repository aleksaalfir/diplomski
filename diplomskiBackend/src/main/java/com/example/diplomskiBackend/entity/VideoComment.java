package com.example.diplomskiBackend.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class VideoComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String comment;

    @ManyToOne
    private User user;

    private Date date;

    public VideoComment(String comment, User user, Date date) {
        this.comment = comment;
        this.user = user;
        this.date = date;
    }
}
