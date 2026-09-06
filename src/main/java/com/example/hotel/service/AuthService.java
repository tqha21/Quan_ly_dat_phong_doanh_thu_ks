package com.example.hotel.service;

import com.example.hotel.dto.RegisterRequest;

public interface AuthService {
    void registerCustomer(RegisterRequest request);
}
