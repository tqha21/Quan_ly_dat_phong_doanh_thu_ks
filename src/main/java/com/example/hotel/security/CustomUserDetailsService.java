package com.example.hotel.security;

import com.example.hotel.entity.User;
import com.example.hotel.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy tài khoản với email: " + email));
        
        // Eager load role to avoid LazyInitializationException
        String roleCode = user.getRole().getCode();
        System.out.println("DEBUG LOGIN: User " + email + " logged in with role " + roleCode);
                
        return new CustomUserDetails(user);
    }
}
