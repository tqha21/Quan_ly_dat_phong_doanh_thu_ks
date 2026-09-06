# BTL Quản Lý Đặt Phòng và Doanh Thu Khách Sạn

Dự án Hệ thống Quản lý Đặt phòng và Doanh thu Khách sạn (Hotel Management System) là một ứng dụng Web toàn diện hỗ trợ khách hàng tìm kiếm, đặt phòng trực tuyến và giúp quản trị viên quản lý phòng, doanh thu hiệu quả.

## 🌟 Tính Năng Nổi Bật

### Dành cho Khách hàng (Customer)
* **Giao diện hiện đại**: Thiết kế theo chuẩn Booking.com mang lại trải nghiệm chuyên nghiệp, trực quan.
* **Tìm kiếm phòng thông minh**: Lọc phòng theo ngày nhận/trả, số lượng người, tiện ích.
* **Đặt phòng trực tuyến**: Nhanh chóng, an toàn. Xem lịch sử đặt phòng cá nhân.
* **Đánh giá & Phản hồi (Review)**: Xem và để lại đánh giá cho từng loại phòng.

### Dành cho Quản trị viên (Admin)
* **Quản lý Phòng & Loại phòng**: Thêm, sửa, xóa thông tin phòng, giá tiền, tiện nghi.
* **Quản lý Đơn đặt phòng**: Xét duyệt, theo dõi trạng thái booking.
* **Thống kê Doanh thu**: Báo cáo trực quan về doanh thu theo ngày/tháng/năm.
* **Quản lý Dịch vụ**: Cung cấp các dịch vụ đi kèm (nhà hàng, spa, đưa đón...).
* **Phân quyền & Bảo mật**: Quản lý tài khoản và phân quyền bằng Spring Security.

## 🛠️ Công Nghệ Sử Dụng

* **Backend**: Java 21, Spring Boot 3.x, Spring Data JPA, Spring Security.
* **Frontend**: Thymeleaf, Bootstrap 5, HTML/CSS/JavaScript (Vanilla).
* **Database**: MySQL.
* **Build Tool**: Maven.
* **Kiến trúc**: MVC (Model-View-Controller) / 3-Tier Architecture.

## 👥 Đội Ngũ Phát Triển & Phân Công Nhiệm Vụ

Dưới đây là bảng phân công công việc chi tiết cho các thành viên trong nhóm:

| STT | Họ và Tên | Vai trò | Mô tả Công việc (Nhiệm vụ chính) |
|:---:|:---|:---|:---|
| **1** | **Tạ Quang Hà** (Leader) | System Architect & Backend Core | - Thiết kế kiến trúc tổng thể, sơ đồ cơ sở dữ liệu (ERD).<br>- Cấu hình project, Spring Security (phân quyền Auth).<br>- Review code, merge code và quản lý Git/GitHub.<br>- Hỗ trợ giải quyết bug khó cho các thành viên. |
| **2** | **Dương Thanh Tâm** | Backend Developer (Booking & Room) | - Viết API/Logic Quản lý Phòng (Room/RoomType).<br>- Xử lý thuật toán tìm kiếm phòng trống theo ngày.<br>- Logic Đặt phòng (Booking), xử lý trùng lặp đặt phòng. |
| **3** | **Nguyễn Hữu Tú** | Frontend Developer (Customer) | - Code giao diện người dùng (Trang chủ, Tìm kiếm).<br>- Trang Chi tiết phòng, Lịch sử đặt phòng.<br>- Clone giao diện chuẩn UI/UX của Booking.com. |
| **4** | **Nguyễn Quốc Dũng** | Fullstack Developer (Admin Panel) | - Xây dựng trang Dashboard quản trị cho Admin.<br>- Quản lý doanh thu, vẽ biểu đồ thống kê.<br>- Giao diện Quản lý User và duyệt Đơn đặt phòng. |
| **5** | **Nguyễn Đăng Huân** | Backend & Integration | - Quản lý Dịch vụ đi kèm (Hotel Services).<br>- Xử lý chức năng Đánh giá (Review/Rating).<br>- (Tùy chọn) Tích hợp gửi Email tự động khi đặt phòng. |

## 🚀 Hướng Dẫn Cài Đặt và Chạy Dự Án

### Yêu cầu hệ thống:
* Java 21 hoặc mới hơn.
* Maven 3.8+
* MySQL Server (phiên bản 8.0+)

### Các bước cài đặt:

1. **Clone repository về máy:**
   ```bash
   git clone https://github.com/tqha21/Quan_ly_dat_phong_doanh_thu_ks.git
   ```

2. **Cấu hình Database:**
   * Tạo một database trong MySQL (ví dụ: `hotel_management_db`).
   * Mở file `src/main/resources/application.properties` và sửa lại thông tin kết nối:
     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/hotel_management_db?useSSL=false&serverTimezone=UTC
     spring.datasource.username=root
     spring.datasource.password=your_password
     ```

3. **Chạy ứng dụng:**
   * Sử dụng Maven wrapper hoặc IDE (IntelliJ IDEA, Eclipse) để chạy project.
   * Hoặc qua dòng lệnh:
     ```bash
     mvn spring-boot:run
     ```

4. **Truy cập ứng dụng:**
   * Mở trình duyệt và truy cập: `http://localhost:8080`

---
*Dự án Bài Tập Lớn - Chúc toàn đội hoàn thành xuất sắc nhiệm vụ!*
