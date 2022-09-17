package com.example.diplomskiBackend.mapper;

import com.example.diplomskiBackend.dto.RegistrationRequestDTO;
import com.example.diplomskiBackend.dto.UserDTO;
import com.example.diplomskiBackend.entity.Playlist;
import com.example.diplomskiBackend.entity.User;

public class UserMapper {

    public static User mapModel(UserDTO userDTO){
        return User.builder()
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .password(userDTO.getPassword())
                .username(userDTO.getUsername())
                .email(userDTO.getEmail())
                .role(userDTO.getRole())
                .birthDate(userDTO.getBirthDate())
                .channelName(userDTO.getChannelName())
                .subscriptionNumber(userDTO.getSubscriptionNumber())
                .build();
    }

    public static User mapRegistrationModel(RegistrationRequestDTO registrationRequestDTO){
        return User.builder()
                .firstName(registrationRequestDTO.getFirstName())
                .lastName(registrationRequestDTO.getLastName())
                .password(registrationRequestDTO.getPassword())
                .username(registrationRequestDTO.getUsername())
                .email(registrationRequestDTO.getEmail())
                .birthDate(registrationRequestDTO.getBirthDate())
                .channelName(registrationRequestDTO.getChannelName())
                .build();
    }

    public static UserDTO mapDTOWithoutPassword(User user){
        return  UserDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .username(user.getUsername())
                .subscriptionNumber(user.getSubscriptionNumber())
                .birthDate(user.getBirthDate())
                .channelName(user.getChannelName())
                .build();
    }

    public static UserDTO mapDTO(User user){
        return  null;
    }

    public static UserDTO userChannelMapDTO(User user){
        return UserDTO.builder()
                .id(user.getId())
                .channelName(user.getChannelName())
                .subscriptionNumber(user.getSubscriptionNumber())
                .username(user.getUsername())
                .playlist(PlaylistMapper.mapListDTO(user.getPlaylist()))
                .build();
    }

}
