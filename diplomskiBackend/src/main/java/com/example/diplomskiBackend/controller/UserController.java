package com.example.diplomskiBackend.controller;

import com.example.diplomskiBackend.dto.RegistrationRequestDTO;
import com.example.diplomskiBackend.dto.UpdateUserDTO;
import com.example.diplomskiBackend.dto.UserDTO;
import com.example.diplomskiBackend.dto.VideoDTO;
import com.example.diplomskiBackend.entity.User;
import com.example.diplomskiBackend.service.SubscribeService;
import com.example.diplomskiBackend.service.UserService;
import com.example.diplomskiBackend.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@CrossOrigin
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private VideoService videoService;

    @Autowired
    private SubscribeService subscribeService;

    @PostMapping("/register")
    public ResponseEntity<Long> register(@RequestBody RegistrationRequestDTO registrationRequestDTO){
        try{
            Long id = userService.register(registrationRequestDTO);
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
            return ResponseEntity.created(uri).build();
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

    }

    @GetMapping(value = "/channel/{id}")
    public ResponseEntity<?> getChannel(@PathVariable("id") Long id){
        UserDTO userDTO = userService.getChannel(id);
        List<VideoDTO> videoDTOS = videoService.findVideosByUserIdAndPrivateVideoFalse(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> response = new HashMap<>();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            boolean subscribed = subscribeService.checkSubscription(id);
            response.put("subscribed", subscribed);
        }
        response.put("channel", userDTO);
        response.put("videos", videoDTOS);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/profile")
    public ResponseEntity<?> getProfile(){
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        UserDTO userDTO = userService.findUserByUsername(username);
        List<VideoDTO> videoDTOS = videoService.findVideosByUserId(userDTO.getId());
        Map<String, Object> response = new HashMap<>();
        response.put("channel", userDTO);
        response.put("videos", videoDTOS);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping
    public ResponseEntity<Void> update(@RequestBody UpdateUserDTO updateUserDTO){
        userService.update(updateUserDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
