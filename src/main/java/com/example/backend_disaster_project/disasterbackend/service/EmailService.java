package com.example.backend_disaster_project.disasterbackend.service;

import com.example.backend_disaster_project.disasterbackend.entities.VictimDB;

public interface EmailService {
    void sendEmailVerificationEmail(VictimDB userDto, String appBaseUrl);

    void sendPasswordResetEmail(VictimDB userDto, String token);
}
