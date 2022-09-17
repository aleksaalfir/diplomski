package com.example.diplomskiBackend.controller;

import com.example.diplomskiBackend.dto.PlaylistDTO;
import com.example.diplomskiBackend.dto.PlaylistRequestDTO;
import com.example.diplomskiBackend.dto.UserDTO;
import com.example.diplomskiBackend.service.PlaylistService;
import com.example.diplomskiBackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/api/playlist")
public class PlaylistController {

    @Autowired
    private UserService userService;

    @Autowired
    private PlaylistService playlistService;

    @GetMapping("/{id}")
    public ResponseEntity<Map> findOne(@PathVariable("id") Long id){
        PlaylistDTO playlistDTO = playlistService.findOne(id);
        UserDTO userDTO = userService.findUserByPlaylist(playlistDTO);
        Map<String, Object> response = new HashMap<>();
        response.put("channel", userDTO);
        response.put("playlist", playlistDTO);
        return new ResponseEntity<Map>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public ResponseEntity<List<PlaylistDTO>> getPlaylistsByUser(){
        List<PlaylistDTO> playlistDTOS = playlistService.findPlaylistsByUser();
        return new ResponseEntity<>(playlistDTOS, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<Long> create(@RequestBody PlaylistRequestDTO playlistRequestDTO){
        Long id = playlistService.create(playlistRequestDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Long id){
        boolean deleted = playlistService.delete(id);
        if(deleted){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping(value = "/{playlistId}/add-video/{videoId}")
    public ResponseEntity<Boolean> addVideoToPlaylist(@PathVariable("playlistId") Long playlistId, @PathVariable("videoId") UUID videoId){
        boolean added = playlistService.addVideoToPlaylist(playlistId, videoId);
        if(added){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

}
