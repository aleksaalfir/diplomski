package com.example.diplomskiBackend.repository;

import com.example.diplomskiBackend.entity.Playlist;
import com.example.diplomskiBackend.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

    @Query(value = "SELECT playlist.id, playlist.name, playlist.private_playlist FROM  playlist_video, playlist\n" +
            "where playlist_video.playlist_id = playlist.id\n" +
            "and playlist_video.video_id = :videoId ", nativeQuery = true)
    public List<Playlist> findPlaylistsByVideo(@Param("videoId") Long videoId);

    @Query(value = "SELECT playlist.id, playlist.name, playlist.private_playlist FROM playlist, user_playlist\n" +
            "where user_playlist.playlist_id = playlist.id \n" +
            "and user_playlist.user_id = :userId\n" +
            "and playlist.private_playlist = false", nativeQuery = true)
    public List<Playlist> findNonPrivatePlaylistByUser(@Param("userId") Long userId);




}
