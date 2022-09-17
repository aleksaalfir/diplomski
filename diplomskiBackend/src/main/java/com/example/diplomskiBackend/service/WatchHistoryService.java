package com.example.diplomskiBackend.service;

import com.example.diplomskiBackend.dto.WatchHistoryDTO;

public interface WatchHistoryService {

    WatchHistoryDTO getOne();
    boolean delete();
    boolean addToWatcHistory(Long id);
}
