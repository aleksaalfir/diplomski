package com.example.diplomskiBackend.service;

import com.example.diplomskiBackend.dto.PlaylistDTO;
import com.example.diplomskiBackend.dto.RegistrationRequestDTO;
import com.example.diplomskiBackend.dto.UpdateUserDTO;
import com.example.diplomskiBackend.dto.UserDTO;

public interface UserService {

    Long register(RegistrationRequestDTO registrationRequestDTO);
    UserDTO getChannel(Long id);
    UserDTO findUserByPlaylist(PlaylistDTO playlistDTO);
    UserDTO findUserByUsername(String username);
    void update(UpdateUserDTO updateUserDTO);

    boolean checkIfAdmin ();


}
