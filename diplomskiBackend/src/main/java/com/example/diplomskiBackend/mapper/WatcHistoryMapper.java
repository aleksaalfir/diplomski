package com.example.diplomskiBackend.mapper;

import com.example.diplomskiBackend.dto.WatchHistoryDTO;
import com.example.diplomskiBackend.entity.WatchHistory;

public class WatcHistoryMapper {

    public static WatchHistoryDTO mapDTO(WatchHistory watchHistory){
        return WatchHistoryDTO.builder()
                .id(watchHistory.getId())
                .videos(VideoMapper.mapListDTO(watchHistory.getVideos()))
                .build();
    }
}
