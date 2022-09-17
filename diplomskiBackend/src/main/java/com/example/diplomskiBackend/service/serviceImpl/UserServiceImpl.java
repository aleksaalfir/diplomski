package com.example.diplomskiBackend.service.serviceImpl;

import com.example.diplomskiBackend.dto.PlaylistDTO;
import com.example.diplomskiBackend.dto.RegistrationRequestDTO;
import com.example.diplomskiBackend.dto.UpdateUserDTO;
import com.example.diplomskiBackend.dto.UserDTO;
import com.example.diplomskiBackend.entity.*;
import com.example.diplomskiBackend.mapper.UserMapper;
import com.example.diplomskiBackend.repository.DefaultUserRepository;
import com.example.diplomskiBackend.repository.PlaylistRepository;
import com.example.diplomskiBackend.repository.UserRepository;
import com.example.diplomskiBackend.service.EmailSender;
import com.example.diplomskiBackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final EmailSender emailSender;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private DefaultUserRepository defaultUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Override
    public Long register(RegistrationRequestDTO registrationRequestDTO) throws IllegalArgumentException {

            List<User> users = userRepository.findAll();
            for (User user: users) {
                if(user.getEmail().equals(registrationRequestDTO.getEmail()) || user.getUsername().equals(registrationRequestDTO.getUsername())){
                    throw new IllegalArgumentException("Username or email already in use!");
                }
            }

            User user = UserMapper.mapRegistrationModel(registrationRequestDTO);
            user.setPassword(passwordEncoder.encode(registrationRequestDTO.getPassword()));
            user.setSubscriptionNumber(0);
            user.setRole(EUserRole.USER);
            user.setLikedVideos(new LikedVideos());
            user.setWatchHistory(new WatchHistory());
            user.setPlaylist(new ArrayList<>());
            user.setSubscription(new Subscription());
            emailSender.send(registrationRequestDTO.getEmail(), "Dear " + registrationRequestDTO.getFirstName() + " " + registrationRequestDTO.getLastName()
                    + ". " + "Welcome to VideoTube. We are so proud of you being our new member. We hope that you will enjoy our platform.", "Welcome to VideoTube!");
            userRepository.save(user);


            return user.getId();

    }

    @Override
    public UserDTO getChannel(Long id) {
        User user = userRepository.findById(id).orElse(null);
        List<Playlist> playlists = playlistRepository.findNonPrivatePlaylistByUser(user.getId());
        user.setPlaylist(playlists);
        return UserMapper.userChannelMapDTO(user);
    }

    @Override
    public UserDTO findUserByPlaylist(PlaylistDTO playlistDTO) {
        Playlist playlist = playlistRepository.findById(playlistDTO.getId()).orElse(null);
        User user = userRepository.findUserByPlaylist(playlist);
        return UserMapper.mapDTOWithoutPassword(user);
    }

    @Override
    public UserDTO findUserByUsername(String username) {
        return UserMapper.mapDTOWithoutPassword(userRepository.findUserByUsername(username));
    }

    @Override
    public void update(UpdateUserDTO updateUserDTO) {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findUserByUsername(username);
        user.setFirstName(updateUserDTO.getFirstName());
        user.setLastName(updateUserDTO.getLastName());
        user.setChannelName(updateUserDTO.getChannelName());
        user.setBirthDate(updateUserDTO.getBirthDate());
        userRepository.save(user);
    }

    @Override
    public boolean checkIfAdmin() {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        DefaultUser user = defaultUserRepository.findDefaultUserByUsername(username);
        if(user.getRole() == EUserRole.ADMINISTRATOR) return true;
        return false;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        DefaultUser user = defaultUserRepository.findDefaultUserByUsername(username);

        if(user == null) {
            throw new UsernameNotFoundException("There is no user with username" + username);
        }else {
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            String role = "ROLE_" + user.getRole().toString();
            grantedAuthorities.add(new SimpleGrantedAuthority(role));

            return new org.springframework.security.core.userdetails.User(user.getUsername().trim(), user.getPassword().trim(), grantedAuthorities);
        }
    }
}
