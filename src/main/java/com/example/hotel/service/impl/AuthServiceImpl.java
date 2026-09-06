package com.example.hotel.service.impl;

import com.example.hotel.dto.RegisterRequest;
import com.example.hotel.entity.Role;
import com.example.hotel.entity.User;
import com.example.hotel.exception.UserAlreadyExistsException;
import com.example.hotel.repository.RoleRepository;
import com.example.hotel.repository.UserRepository;
import com.example.hotel.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void registerCustomer(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Email đã được sử dụng!");
        }
        if (userRepository.existsByPhone(request.getPhone())) {
            throw new UserAlreadyExistsException("Số điện thoại đã được sử dụng!");
        }
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new IllegalArgumentException("Mật khẩu xác nhận không khớp!");
        }

        Role customerRole = roleRepository.findByCode("ROLE_CUSTOMER")
                .orElseThrow(() -> new RuntimeException("Lỗi hệ thống: Không tìm thấy quyền Customer"));

        User user = new User();
        // Sinh ID tự động ngẫu nhiên (ví dụ U + 7 ký tự ngẫu nhiên) để fit với bảng VARCHAR(10)
        user.setId("U" + UUID.randomUUID().toString().substring(0, 7).toUpperCase());
        user.setRole(customerRole);
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setStatus("ACTIVE");

        userRepository.save(user);
    }
}
