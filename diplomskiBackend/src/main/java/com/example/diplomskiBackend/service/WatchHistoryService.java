package com.example.diplomskiBackend.service;

import com.example.diplomskiBackend.dto.WatchHistoryDTO;

import java.util.UUID;

public interface WatchHistoryService {

    WatchHistoryDTO getOne();
    boolean delete();
    boolean addToWatcHistory(UUID id);
}
