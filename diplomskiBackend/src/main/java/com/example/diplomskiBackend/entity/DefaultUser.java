package com.example.diplomskiBackend.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = "default_user")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class DefaultUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String password;

    private String username;

    private String email;

    private EUserRole role;

    private Date birthDate;

}
