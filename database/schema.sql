-- Create database
CREATE DATABASE IF NOT EXISTS hotel_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE hotel_db;

-- 1. roles
CREATE TABLE roles (
    id VARCHAR(10) PRIMARY KEY,
    code VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 2. users
CREATE TABLE users (
    id VARCHAR(10) PRIMARY KEY,
    role_id VARCHAR(10) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    phone VARCHAR(20) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    status VARCHAR(30) NOT NULL DEFAULT 'ACTIVE',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (role_id) REFERENCES roles(id)
);

-- 3. room_types
CREATE TABLE room_types (
    id VARCHAR(10) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(15,2) NOT NULL,
    capacity INT NOT NULL,
    area DECIMAL(8,2),
    status VARCHAR(30) DEFAULT 'ACTIVE',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 4. rooms
CREATE TABLE rooms (
    id VARCHAR(10) PRIMARY KEY,
    room_type_id VARCHAR(10) NOT NULL,
    room_number VARCHAR(20) UNIQUE NOT NULL,
    floor INT,
    status VARCHAR(30) DEFAULT 'AVAILABLE',
    description VARCHAR(500),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (room_type_id) REFERENCES room_types(id)
);

-- 5. room_images
CREATE TABLE room_images (
    id VARCHAR(10) PRIMARY KEY,
    room_id VARCHAR(10) NOT NULL,
    image_url VARCHAR(500) NOT NULL,
    is_primary BOOLEAN DEFAULT FALSE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (room_id) REFERENCES rooms(id)
);

-- 6. bookings
CREATE TABLE bookings (
    id VARCHAR(10) PRIMARY KEY,
    customer_id VARCHAR(10) NOT NULL,
    booking_code VARCHAR(30) UNIQUE NOT NULL,
    check_in_date DATE NOT NULL,
    check_out_date DATE NOT NULL,
    total_amount DECIMAL(15,2),
    status VARCHAR(30) DEFAULT 'PENDING',
    note VARCHAR(500),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (customer_id) REFERENCES users(id)
);

-- 7. booking_details
CREATE TABLE booking_details (
    id VARCHAR(10) PRIMARY KEY,
    booking_id VARCHAR(10) NOT NULL,
    room_id VARCHAR(10) NOT NULL,
    price DECIMAL(15,2) NOT NULL,
    quantity INT DEFAULT 1,
    subtotal DECIMAL(15,2) NOT NULL,
    FOREIGN KEY (booking_id) REFERENCES bookings(id),
    FOREIGN KEY (room_id) REFERENCES rooms(id)
);

-- 8. payments
CREATE TABLE payments (
    id VARCHAR(10) PRIMARY KEY,
    booking_id VARCHAR(10) NOT NULL,
    amount DECIMAL(15,2) NOT NULL,
    payment_method VARCHAR(30),
    payment_status VARCHAR(30) DEFAULT 'PENDING',
    transaction_code VARCHAR(100),
    paid_at DATETIME,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (booking_id) REFERENCES bookings(id)
);

-- 9. services
CREATE TABLE services (
    id VARCHAR(10) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    price DECIMAL(15,2) NOT NULL,
    status VARCHAR(30) DEFAULT 'ACTIVE',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 10. booking_services
CREATE TABLE booking_services (
    id VARCHAR(10) PRIMARY KEY,
    booking_id VARCHAR(10) NOT NULL,
    service_id VARCHAR(10) NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(15,2) NOT NULL,
    subtotal DECIMAL(15,2) NOT NULL,
    FOREIGN KEY (booking_id) REFERENCES bookings(id),
    FOREIGN KEY (service_id) REFERENCES services(id)
);

-- 11. reviews
CREATE TABLE reviews (
    id VARCHAR(10) PRIMARY KEY,
    booking_id VARCHAR(10),
    customer_id VARCHAR(10),
    room_id VARCHAR(10),
    rating INT,
    comment VARCHAR(1000),
    status VARCHAR(30) DEFAULT 'APPROVED',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (booking_id) REFERENCES bookings(id),
    FOREIGN KEY (customer_id) REFERENCES users(id),
    FOREIGN KEY (room_id) REFERENCES rooms(id)
);

-- INSERT SEED DATA FOR ROLES
INSERT INTO roles (id, code, name, description) VALUES
('R01', 'ROLE_ADMIN', 'Administrator', 'Quản trị viên toàn hệ thống'),
('R02', 'ROLE_STAFF', 'Staff', 'Nhân viên lễ tân/vận hành'),
('R03', 'ROLE_CUSTOMER', 'Customer', 'Khách hàng đặt phòng');

-- Mật khẩu mặc định là '123456' đã được BCrypt hash ($2a$10$XQY... dummy string for now)
INSERT INTO users (id, role_id, full_name, email, phone, password, status) VALUES
('U01', 'R01', 'System Admin', 'admin@hotel.com', '0901234567', '$2a$10$3Ym.9uT0c43jF8lQkF9e8.rWc8wW9j7bV7T7.zF5Z8T5Y0', 'ACTIVE'),
('U02', 'R02', 'Receptionist 1', 'staff@hotel.com', '0912345678', '$2a$10$3Ym.9uT0c43jF8lQkF9e8.rWc8wW9j7bV7T7.zF5Z8T5Y0', 'ACTIVE'),
('U03', 'R03', 'John Doe', 'customer@gmail.com', '0987654321', '$2a$10$3Ym.9uT0c43jF8lQkF9e8.rWc8wW9j7bV7T7.zF5Z8T5Y0', 'ACTIVE');
