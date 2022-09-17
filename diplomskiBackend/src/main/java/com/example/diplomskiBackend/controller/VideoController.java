package com.example.diplomskiBackend.controller;

import com.example.diplomskiBackend.dto.PlaylistDTO;
import com.example.diplomskiBackend.dto.RequestVideoDTO;
import com.example.diplomskiBackend.dto.UserDTO;
import com.example.diplomskiBackend.dto.VideoDTO;
import com.example.diplomskiBackend.entity.User;
import com.example.diplomskiBackend.service.PlaylistService;
import com.example.diplomskiBackend.service.UserService;
import com.example.diplomskiBackend.service.VideoService;
import com.example.diplomskiBackend.service.WatchHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/videos")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @Autowired
    private WatchHistoryService watchHistoryService;

    @Autowired
    private PlaylistService playlistService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<VideoDTO>> getAll(@RequestParam(required=false, name="search") String search){
        List<VideoDTO> videoDTOS = new ArrayList<>();
        videoDTOS = videoService.findVideosByPrivateVideoFalseAndNameContains(search);
        if(search.equals("null")){
            videoDTOS = videoService.findAllNonPrivateVideos();
            return new ResponseEntity<>(videoDTOS, HttpStatus.OK);
        }
        return new ResponseEntity<>(videoDTOS, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map> getOne(@PathVariable("id") Long id){

        VideoDTO videoDTO = videoService.findOne(id);
        List<VideoDTO>  videos = videoService.findAllNonPrivateVideos().stream().limit(10).collect(Collectors.toList());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        videoService.incrementVideoViews(id);

        Map<String, Object> response = new HashMap<>();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            boolean admin = userService.checkIfAdmin();
            if(!admin) {
                boolean liked = videoService.checkLiked(id);
                response.put("liked", liked);
                watchHistoryService.addToWatcHistory(id);
                List<PlaylistDTO> playlistDTOS = playlistService.findPlaylistsByUser();
                response.put("myPlaylists", playlistDTOS);
            }
        }
        response.put("video", videoDTO);
        response.put("videos", videos);

        return new ResponseEntity<Map>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Long id){
        boolean deleted = videoService.delete(id);
        if(deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{id}")
    public ResponseEntity<VideoDTO> update(@PathVariable("id") Long id, @RequestBody VideoDTO videoDTO){
        videoService.update(id, videoDTO);
        return new ResponseEntity<>(videoDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<Long> create(@RequestBody RequestVideoDTO requestVideoDTO){
        Long id = videoService.create(requestVideoDTO);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/like/{id}")
    public ResponseEntity<Boolean> likeVideo(@PathVariable("id") Long id){
        boolean liked = videoService.liked(id);
        return new ResponseEntity<>(liked, HttpStatus.OK);
    }

}
