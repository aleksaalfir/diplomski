package com.example.diplomskiBackend.service;

public interface EmailSender {

    void send(String to, String email, String subject);

}
