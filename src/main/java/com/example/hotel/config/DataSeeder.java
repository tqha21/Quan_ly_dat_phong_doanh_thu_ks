package com.example.hotel.config;

import com.example.hotel.entity.Role;
import com.example.hotel.entity.RoomType;
import com.example.hotel.entity.User;
import com.example.hotel.repository.RoleRepository;
import com.example.hotel.repository.RoomTypeRepository;
import com.example.hotel.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final RoomTypeRepository roomTypeRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Tự động tạo Role nếu DB trống
        if (roleRepository.count() == 0) {
            roleRepository.save(
                    new Role("R01", "ROLE_ADMIN", "Administrator", "Quản trị viên toàn hệ thống", null, null, null));
            roleRepository.save(new Role("R02", "ROLE_STAFF", "Staff", "Nhân viên lễ tân/vận hành", null, null, null));
            roleRepository.save(new Role("R03", "ROLE_CUSTOMER", "Customer", "Khách hàng đặt phòng", null, null, null));
            System.out.println("✅ DataSeeder: Đã tạo Role mẫu.");
        }

        Role adminRole = roleRepository.findByCode("ROLE_ADMIN").orElseThrow();
        Role staffRole = roleRepository.findByCode("ROLE_STAFF").orElseThrow();
        Role customerRole = roleRepository.findByCode("ROLE_CUSTOMER").orElseThrow();
        
        // 1. Xử lý tài khoản Admin
        Optional<User> adminOpt = userRepository.findByEmail("admin@hotel.com");
        if (adminOpt.isEmpty()) {
            User admin = new User("U01", adminRole, "System Admin", "admin@hotel.com", "0901234567",
                    passwordEncoder.encode("123456"), "ACTIVE", null, null, null);
            userRepository.save(admin);
            System.out.println("✅ DataSeeder: Đã tạo Admin mẫu.");
        } else {
            User admin = adminOpt.get();
            admin.setPassword(passwordEncoder.encode("123456"));
            admin.setRole(adminRole);
            admin.setStatus("ACTIVE");
            userRepository.save(admin);
            System.out.println("✅ DataSeeder: Đã reset mật khẩu Admin về 123456.");
        }

        // 2. Xử lý tài khoản Staff (Đảm bảo tự tạo mới hoặc reset password nếu đã có)
        Optional<User> staffOpt = userRepository.findByEmail("staff@hotel.com");
        if (staffOpt.isEmpty()) {
            User staff = new User("U02", staffRole, "Receptionist 1", "staff@hotel.com", "0912345678",
                    passwordEncoder.encode("123456"), "ACTIVE", null, null, null);
            userRepository.save(staff);
            System.out.println("✅ DataSeeder: Đã tạo Staff mẫu.");
        } else {
            User staff = staffOpt.get();
            staff.setPassword(passwordEncoder.encode("123456"));
            staff.setRole(staffRole);
            staff.setStatus("ACTIVE");
            userRepository.save(staff);
            System.out.println("✅ DataSeeder: Đã reset mật khẩu Staff về 123456.");
        }

        // 3. Xử lý tài khoản Customer
        Optional<User> customerOpt = userRepository.findByEmail("customer@gmail.com");
        if (customerOpt.isEmpty()) {
            User customer = new User("U03", customerRole, "John Doe", "customer@gmail.com", "0987654321",
                    passwordEncoder.encode("123456"), "ACTIVE", null, null, null);
            userRepository.save(customer);
            System.out.println("✅ DataSeeder: Đã tạo Customer mẫu.");
        } else {
            User customer = customerOpt.get();
            customer.setPassword(passwordEncoder.encode("123456"));
            customer.setRole(customerRole);
            customer.setStatus("ACTIVE");
            userRepository.save(customer);
            System.out.println("✅ DataSeeder: Đã reset mật khẩu Customer về 123456.");
        }

        // Tự động tạo Room Type mẫu nếu trống
        if (roomTypeRepository.count() == 0) {
            roomTypeRepository.save(new RoomType("RT01", "Standard", "Phòng tiêu chuẩn", new BigDecimal("500000"), 2,
                    new BigDecimal("25.0"), "ACTIVE", null, null, null));
            roomTypeRepository.save(new RoomType("RT02", "Deluxe", "Phòng cao cấp, có view đẹp",
                    new BigDecimal("800000"), 2, new BigDecimal("35.0"), "ACTIVE", null, null, null));
            roomTypeRepository.save(new RoomType("RT03", "Suite", "Phòng hạng sang", new BigDecimal("1500000"), 2,
                    new BigDecimal("50.0"), "ACTIVE", null, null, null));
            roomTypeRepository.save(new RoomType("RT04", "Family", "Phòng dành cho gia đình", new BigDecimal("1200000"),
                    4, new BigDecimal("45.0"), "ACTIVE", null, null, null));
            System.out.println("✅ DataSeeder: Đã tạo Room Type mẫu.");
        }
    }
}
