package com.example.hotel.security;

import com.example.hotel.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Trả về role từ CSDL, code luôn có dạng ROLE_XXX (ví dụ: ROLE_ADMIN)
        return Collections.singleton(new SimpleGrantedAuthority(user.getRole().getCode()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail(); // Dùng email để làm username đăng nhập
    }
    
    // Thêm các getter phụ để lấy thông tin hiển thị lên giao diện Thymeleaf
    public String getFullName() {
        return user.getFullName();
    }
    
    public String getId() {
        return user.getId();
    }
    
    public String getRoleCode() {
        return user.getRole().getCode();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // Chỉ cho phép đăng nhập nếu tài khoản ACTIVE
        return "ACTIVE".equals(user.getStatus());
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return "ACTIVE".equals(user.getStatus());
    }
}
