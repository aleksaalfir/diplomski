package com.example.diplomskiBackend.controller;

import com.example.diplomskiBackend.dto.VideoDTO;
import com.example.diplomskiBackend.dto.WatchHistoryDTO;
import com.example.diplomskiBackend.service.WatchHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/watch-history")
public class WatchHistoryController {

    @Autowired
    private WatchHistoryService watchHistoryService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public ResponseEntity<List<VideoDTO>> getOne(){
        WatchHistoryDTO watchHistoryDTO = watchHistoryService.getOne();
        return new ResponseEntity<>(watchHistoryDTO.getVideos(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping
    public ResponseEntity<?> delete(){
        boolean deleted = watchHistoryService.delete();
        if(deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
