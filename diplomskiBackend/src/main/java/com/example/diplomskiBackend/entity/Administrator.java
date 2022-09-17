package com.example.diplomskiBackend.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;


import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@AllArgsConstructor
@Getter
@Setter
@ToString
@SuperBuilder
@Table(name = "administrator")
public class Administrator extends DefaultUser {


}
