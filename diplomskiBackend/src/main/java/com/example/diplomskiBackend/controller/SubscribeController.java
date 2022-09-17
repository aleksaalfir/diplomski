package com.example.diplomskiBackend.controller;

import com.example.diplomskiBackend.dto.VideoDTO;
import com.example.diplomskiBackend.service.SubscribeService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/subscribe")
public class SubscribeController {

    @Autowired
    private SubscribeService subscribeService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public ResponseEntity<List<VideoDTO>> getAllVideosFromSubscription(){
        return new ResponseEntity<>(subscribeService.getAllSubscriptionVideos(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<Boolean> subscribe(@PathVariable("id") Long id){
        return new ResponseEntity<>(subscribeService.subscribe(id), HttpStatus.OK);
    }
}
