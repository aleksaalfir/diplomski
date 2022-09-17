package com.example.diplomskiBackend.repository;

import com.example.diplomskiBackend.entity.DefaultUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DefaultUserRepository extends JpaRepository<DefaultUser, Long> {

    DefaultUser findDefaultUserByUsername(String username);

}
