package com.example.diplomskiBackend.repository;

import com.example.diplomskiBackend.entity.Playlist;
import com.example.diplomskiBackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByPlaylist(Playlist playlist);
    User findUserByUsername(String username);

}
