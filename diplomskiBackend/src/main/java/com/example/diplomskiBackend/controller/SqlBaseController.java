package com.example.diplomskiBackend.controller;

import com.example.diplomskiBackend.entity.*;
import com.example.diplomskiBackend.repository.AdministratorRepository;
import com.example.diplomskiBackend.repository.PlaylistRepository;
import com.example.diplomskiBackend.repository.UserRepository;
import com.example.diplomskiBackend.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/api/sql")
public class SqlBaseController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AdministratorRepository administratorRepository;


    @GetMapping
    void createBase(){

        System.out.println("BASE CREATED:");

        Playlist playlist1 = new Playlist(1L, "Travel", false, new ArrayList<>());
        Playlist playlist2 = new Playlist(2L, "Creative playlist", false, new ArrayList<>());
        Playlist playlist3 = new Playlist(3L, "Dogs", true, new ArrayList<>());

        User user1 = new User(1L, "Aleksa", "Alfirovic", passwordEncoder.encode("123"), "aleksa", "aleksa@gmail.com", EUserRole.USER, new Date(), "Aleksa's channel", 0, new LikedVideos(), new WatchHistory(), new ArrayList<>() ,new Subscription());
        User user2 = new User(2L, "Vuk", "Alfirovic", passwordEncoder.encode("123"), "vuk", "vuk@gmail.com", EUserRole.USER, new Date(), "VukClips", 0, new LikedVideos(), new WatchHistory(), new ArrayList<>() ,new Subscription());


        user1.getPlaylist().add(playlist1);
        user1.getPlaylist().add(playlist2);
        user1.getPlaylist().add(playlist3);

        userRepository.save(user1);
        userRepository.save(user2);


        Video video1 = new Video(UUID.randomUUID(), "Funny dog", user1, false, false, "Funny white puppy.",
                "https://firebasestorage.googleapis.com/v0/b/diplomski-c2903.appspot.com/o/videos%2Fwhitedogvideo.mp4?alt=media&token=09267062-8a21-4d71-b8e0-8e50dfa5cc40",
                0, new Date(), new ArrayList<VideoComment>(), "", null, new ArrayList<>());

        Video video2 = new Video(UUID.randomUUID(), "Italy in one minute", user1, false, false, "See Italy in one minute. All his beauty and land.",
                "https://firebasestorage.googleapis.com/v0/b/diplomski-c2903.appspot.com/o/videos%2Fitalyvideo.mp4?alt=media&token=8ac6b5af-239b-48ba-94db-b9aaee050d05",
                0,new Date(), new ArrayList<VideoComment>(), "", null, new ArrayList<>());

        Video video3 = new Video(UUID.randomUUID(), "Creative video", user1, false, false, "Creative video made for school.",
                "https://firebasestorage.googleapis.com/v0/b/diplomski-c2903.appspot.com/o/videos%2Fcreativevideo.mp4?alt=media&token=40dacf7f-24ea-4e28-8cac-337dc6bedb5c",
                0,new Date(), new ArrayList<VideoComment>(), "", null, new ArrayList<>());

        Video video4 = new Video(UUID.randomUUID(), "One minute timer", user1, false, true, "Bomb with clock. One minute timer.",
                "https://firebasestorage.googleapis.com/v0/b/diplomski-c2903.appspot.com/o/videos%2Fbombvideo.mp4?alt=media&token=d87f5b46-2b2a-461e-9e18-1ebbe8e1345e",
                0,new Date(), new ArrayList<VideoComment>(), "", null, new ArrayList<>());

        Video video5 = new Video(UUID.randomUUID(), "Welcome to Miami", user1, true, false, "Miami,Florida,America shown in one minute",
                "https://firebasestorage.googleapis.com/v0/b/diplomski-c2903.appspot.com/o/videos%2Fmiamivideo.mp4?alt=media&token=7262b571-891a-43a3-817a-0a3bbf9a7511",
                0,new Date(), new ArrayList<VideoComment>(), "123", null, new ArrayList<>());

        Video video6 = new Video(UUID.randomUUID(), "iPhone 14 Pro Introduction", user1, false, false, "New Apple iPhone 14 Pro with new features...",
                "https://firebasestorage.googleapis.com/v0/b/diplomski-c2903.appspot.com/o/videos%2FIntroducing%20iPhone%2014%20Pro%20_%20Apple.mp4?alt=media&token=46c8dd86-6486-40e0-b1c9-b9921453e0b0",
                0,new Date(), new ArrayList<VideoComment>(), "", null, new ArrayList<>());

        Video video7 = new Video(UUID.randomUUID(), "Alan Walker - Faded", user2, false, false, "Alan Walker - Faded. Purchase my album on my personal website.",
                "https://firebasestorage.googleapis.com/v0/b/diplomski-c2903.appspot.com/o/videos%2Fbranko%2FAlan%20Walker%20-%20Faded.mp4?alt=media&token=0e16a8ee-3efd-40ed-b910-fb9e9946c5bc",
                0,new Date(), new ArrayList<VideoComment>(), "", null, new ArrayList<>());

        Video video8 = new Video(UUID.randomUUID(), "Chocolate cookies recipe", user2, false, false, "Fast and cheap way to make sweet cookies. Follow for more recipes.",
                "https://firebasestorage.googleapis.com/v0/b/diplomski-c2903.appspot.com/o/videos%2Fbranko%2FChocolate%20cookies%20recipe.mp4?alt=media&token=c44e8ae1-0f3b-44ad-94d5-8ea0435df3aa",
                0,new Date(), new ArrayList<VideoComment>(), "", null, new ArrayList<>());

        Video video9 = new Video(UUID.randomUUID(), "Life on Earth - BBC Earth", user2, false, false, "Life on Planet Earth was always impressive and full of surprises. Follow us for more videos.",
                "https://firebasestorage.googleapis.com/v0/b/diplomski-c2903.appspot.com/o/videos%2Fbranko%2FLife%20on%20earth%20%20-%20BBC%20Earth.mp4?alt=media&token=96c2c473-dd4e-4126-a54d-3d348733e948",
                0,new Date(), new ArrayList<VideoComment>(), "", null, new ArrayList<>());

        Video video10 = new Video(UUID.randomUUID(), "STORY - Short movie", user2, false, false, "Short movie. 1 minute movie. VideoTube video.",
                "https://firebasestorage.googleapis.com/v0/b/diplomski-c2903.appspot.com/o/videos%2Fbranko%2FSTORY%20-%20Short%20movie.mp4?alt=media&token=1bd4bc68-3389-441e-9181-aeddf864b9f5",
                0,new Date(), new ArrayList<VideoComment>(), "", null, new ArrayList<>());

        Video video11 = new Video(UUID.randomUUID(), "The Lord of the Rings The Two Towers (2002) Official Trailer #2 - Orlando Bloom Movie HD", user2, false, false, "Most famous trilogy in the world.",
                "https://firebasestorage.googleapis.com/v0/b/diplomski-c2903.appspot.com/o/videos%2Fbranko%2FLordOfTheRingsTwoTowers.mp4?alt=media&token=c30c10bf-e80d-4351-bdce-466af87cb9f9",
                0,new Date(), new ArrayList<VideoComment>(), "", null, new ArrayList<>());

        videoRepository.save(video1);
        videoRepository.save(video2);
        videoRepository.save(video3);
        videoRepository.save(video4);
        videoRepository.save(video5);
        videoRepository.save(video6);
        videoRepository.save(video7);
        videoRepository.save(video8);
        videoRepository.save(video9);
        videoRepository.save(video10);
        videoRepository.save(video11);

        playlist1.getVideos().add(video2);
        playlist1.getVideos().add(video5);
        playlist3.getVideos().add(video1);
        playlist2.getVideos().add(video3);
        playlist2.getVideos().add(video4);
        playlist2.getVideos().add(video1);


        playlistRepository.save(playlist1);
        playlistRepository.save(playlist2);
        playlistRepository.save(playlist3);

        Administrator administrator = new Administrator();
        administrator.setId(2L);
        administrator.setFirstName("Branko");
        administrator.setLastName("Brankovic");
        administrator.setEmail("branko@gmail.com");
        administrator.setBirthDate(new Date());
        administrator.setRole(EUserRole.ADMINISTRATOR);
        administrator.setUsername("branko");
        administrator.setPassword(passwordEncoder.encode("branko"));
        administratorRepository.save(administrator);

    }

}
