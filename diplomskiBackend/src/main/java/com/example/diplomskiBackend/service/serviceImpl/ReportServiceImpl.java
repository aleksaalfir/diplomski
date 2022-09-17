package com.example.diplomskiBackend.service.serviceImpl;

import com.example.diplomskiBackend.entity.Playlist;
import com.example.diplomskiBackend.entity.Report;
import com.example.diplomskiBackend.entity.Video;
import com.example.diplomskiBackend.entity.WatchHistory;
import com.example.diplomskiBackend.repository.PlaylistRepository;
import com.example.diplomskiBackend.repository.ReportRepository;
import com.example.diplomskiBackend.repository.VideoRepository;
import com.example.diplomskiBackend.repository.WatchHistoryRepository;
import com.example.diplomskiBackend.service.ReportService;
import com.example.diplomskiBackend.service.WatchHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private WatchHistoryRepository watchHistoryRepository;

    @Autowired
    private PlaylistRepository playlistRepository;

    @Override
    public List<Report> findAll() {
        return reportRepository.findAll();
    }

    @Override
    public void acceptReport(Long id) {
        Report report = reportRepository.findById(id).orElse(null);
        Video video = videoRepository.findById(report.getVideoId()).orElse(null);
        removeVideoFromWatchHistory(video);
        removeVideoFromPlaylists(video);
        deleteReportsWithVideoId(report.getVideoId());
        videoRepository.delete(video);
    }

    @Override
    public void rejectReport(Long id) {
        Report report = reportRepository.findById(id).orElse(null);
        reportRepository.delete(report);
    }

    @Override
    public Report save(Report report) {
        return reportRepository.save(report);
    }

    private void deleteReportsWithVideoId(Long videoId){
        List<Report> reports = reportRepository.findAllByVideoId(videoId);
        for (Report rep:
                reports) {
            reportRepository.delete(rep);
            }
        }

    private void removeVideoFromPlaylists(Video video){
        List<Playlist> playlists = playlistRepository.findPlaylistsByVideo(video.getId());
        if(playlists.size() >= 1){
            for (Playlist playlist:
                    playlists) {
                playlist.getVideos().remove(video);
                playlistRepository.save(playlist);
            }
        }
    }

    private void removeVideoFromWatchHistory(Video video){
        List<WatchHistory> watchHistories = watchHistoryRepository.findWatchHistoriesByVideo(video.getId());
        if (watchHistories.size() >= 1){
            for (WatchHistory wh:
                    watchHistories) {
                wh.getVideos().remove(video);
                watchHistoryRepository.save(wh);
            }
        }
    }
}
