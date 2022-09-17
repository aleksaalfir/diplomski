package com.example.diplomskiBackend.repository;

import com.example.diplomskiBackend.entity.WatchHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WatchHistoryRepository extends JpaRepository<WatchHistory, Long> {

    @Query(value = "SELECT watch_history.id FROM watch_history, watch_history_videos\n" +
            "where watch_history_videos.video_id = :videoId",nativeQuery = true)
    List<WatchHistory> findWatchHistoriesByVideo(@Param("videoId") Long videoId);

}
