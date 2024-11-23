CREATE DATABASE IF NOT EXISTS WEB_FILM_DATABASE;
USE WEB_FILM_DATABASE;

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET TIME_ZONE = "+00:00";

-- Tất cả kiểu dữ liệu số nên đặt là Long
-- Bảng Cinemas lưu trữ các thông tin liên quan đến 1 rạp: id, tên, địa chỉ 
DROP TABLE IF EXISTS `cinemas`;
CREATE TABLE `cinemas` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `cinema_name` VARCHAR(255) NOT NULL,
    `cinema_address` VARCHAR(255) NOT NULL,
    `createddate` TIMESTAMP NULL,
    `modifieddate` TIMESTAMP NULL,
    `createdby` VARCHAR(255) NULL,
    `modifiedby` VARCHAR(255) NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

INSERT INTO `cinemas` (`id`, `cinema_name`, `cinema_address`) VALUES
(1, 'Beta Mỹ Đình', 'Hà Nội'),
(2, 'Beta Thanh Xuân', 'Hà Nội'),
(3, 'Beta Đan Phượng', 'Hà Nội'),
(4, 'Beta Thái Nguyên', 'Hà Nội'),
(5, 'Beta Biên Hòa', 'Hà Nội'),
(6, 'Beta Long Khánh', 'Hà Nội'),
(7, 'Beta Long Thành', 'Hà Nội'),
(8, 'CGV Thái Hà', 'Hà Nội'),
(9, 'Beta Giải Phóng', 'Hà Nội');

-- Bảng phân quyền: Có 2 quyền: Quản trị viên/ User
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
    `id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `code` VARCHAR(255) NOT NULL UNIQUE,
    `name` VARCHAR(255) NOT NULL UNIQUE,
    `createddate` TIMESTAMP NULL,
    `modifieddate` TIMESTAMP NULL,
    `createdby` VARCHAR(255) NULL,
    `modifiedby` VARCHAR(255) NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

INSERT INTO `roles` (`code`, `name`) VALUES ('ADMIN', 'Quản Trị Viên'), ('USER', 'Khách Hàng');

-- Bảng danh mục: Gồm id và tên danh mục
DROP TABLE IF EXISTS `categories`;
CREATE TABLE `categories` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `category_name` VARCHAR(150) NOT NULL UNIQUE,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

INSERT INTO `categories` (`category_name`) 
VALUES ('Khoa học viễn tưởng'), ('Hành động'), ('Tâm lý - Tình cảm');

-- Thông tin về 1 bộ phim, 1 bộ phim có 1 thể loại, 1 thể loại có thể có nhiều bộ phim
-- running_time: Độ dài của bộ phim, dùng TIME(hh:mm:ss)
DROP TABLE IF EXISTS `movies`;
CREATE TABLE `movies` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `movie_name` VARCHAR(255) NOT NULL,
    `movie_category_id` INT NOT NULL, 
    `movie_description` TEXT,
    `movie_directors` VARCHAR(1000),
    `movie_cast` VARCHAR(1000),
    `running_time` TIME DEFAULT '00:00:00',
    `image` TEXT,
    `status` tinyint(1) default 1,
    `trailer_url` VARCHAR(255),
    `createddate` TIMESTAMP NULL,
    `modifieddate` TIMESTAMP NULL,
    `createdby` VARCHAR(255) NULL,
    `modifiedby` VARCHAR(255) NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`movie_category_id`) REFERENCES `categories`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

INSERT INTO `movies` (`movie_name`, `movie_category_id`, `movie_description`, `movie_directors`, `movie_cast`, `running_time`) VALUES 
('Inception', 1, 'A skilled thief is offered a chance to have his past crimes forgiven by implanting another person\'s idea into their subconscious.', 'Christopher Nolan', 'Leonardo DiCaprio, Joseph Gordon-Levitt, Ellen Page', '02:28:00'),
('Parasite', 2, 'A poor family schemes to become employed by a wealthy family and infiltrates their household by posing as unrelated, highly qualified individuals.', 'Bong Joon-ho', 'Song Kang-ho, Lee Sun-kyun, Cho Yeo-jeong', '02:12:00');

-- Bảng giảm giá: Gồm mã phim được giảm giá(FK), tiêu đề giảm giá, nội dung giảm, phần trăm giảm giá
DROP TABLE IF EXISTS `discount`;
CREATE TABLE `discount` (
	`id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `movie_id` INT NOT NULL,
    `discount_title` VARCHAR(255),
    `discount_content` VARCHAR(255),
    `discount_percentage` INT,
    `createddate` TIMESTAMP NULL,
    `modifieddate` TIMESTAMP NULL,
    `createdby` VARCHAR(255) NULL,
    `modifiedby` VARCHAR(255) NULL,
    foreign key (`movie_id`) references `movies`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

INSERT INTO `discount` (movie_id, discount_title, discount_content, discount_percentage)
VALUES (1, 'DISCOUNT 1', 'ABC', 20);

INSERT INTO `discount` (movie_id, discount_title, discount_content, discount_percentage)
VALUES (2, 'DISCOUNT 2', 'XYZ', 15);

-- Kíp chiếu trong 1 ngày: Cố định 1 ngày 3 kíp chiếu: Sáng, chiều, tối 
DROP TABLE IF EXISTS `shifts`;
CREATE TABLE `shifts` (
	`id` INT NOT NULL PRIMARY KEY auto_increment,
    `name` VARCHAR(255),
    `shift_start` TIME,
    `shift_end` TIME 
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

INSERT INTO `shifts` (id, name, shift_start, shift_end) 
VALUES 
(1, 'Morning', '07:00:00', '11:00:00'),
(2, 'Afternoon', '13:00:00', '17:00:00'),
(3, 'Evening', '19:00:00', '23:00:00');

-- Bảng thông tin người dùng: Mã quyền, tên đăng nhập, mật khẩu, tên, avatar, email,..., status = 1 nếu tài khoản chưa bị khóa
-- account_balance: Số tiền trong tài khoản, order_count: Số đơn hàng đã mua
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `roleid` INT NOT NULL,
    `username` VARCHAR(255) NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    `fullname` VARCHAR(255),
    `avatar` LONGTEXT,
    `email` VARCHAR(255),
    `city` VARCHAR(255),
    `phone` VARCHAR(255),
    `status` TINYINT default 1,
    `account_balance` INT,
    `order_count` INT default 0,
    `createddate` TIMESTAMP,
    `modifieddate` TIMESTAMP,
    `createdby` VARCHAR(255),
	`modifiedby` VARCHAR(255),
    PRIMARY KEY (`id`),
    FOREIGN KEY (`roleid`) REFERENCES `roles`(`id`) ON DELETE CASCADE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
    
INSERT INTO `users` (roleid, username, password, fullname, avatar, email, city, phone, status, account_balance, order_count) 
VALUES (1, 'phuc', 'phuc', 'phuc', 'avatar1.png', 'lon@example.com', 'New York', '1234567890', 1, 150000, 0);

INSERT INTO `users` (roleid, username, password, fullname, avatar, email, city, phone, status, account_balance, order_count) 
VALUES (2, 'khanh8a04', 'khanhbkit1', 'Nguyen Duy Khanh', 'avatar2.png', 'khanhk72nh@gmail.com', 'Ha Noi', '0328873260', 1, 500000, 0);

-- Schedule: 1 phiên chiếu, mỗi 1 suất chiếu sẽ có 1 mã riêng
-- Từ 1 phiên chiếu, có thể tìm được phim, rạp, ngày chiếu, kíp chiếu
DROP TABLE IF EXISTS `schedule`;
CREATE TABLE `schedules` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `movie_id` INT NOT NULL,
    `cinema_id` INT NOT NULL,
    `schedule_date` DATE NOT NULL,
    `shift_id` INT NOT NULL,
    `createddate` TIMESTAMP,
    `modifieddate` TIMESTAMP,
    `createdby` VARCHAR(255),
    `modifiedby` VARCHAR(255),
    PRIMARY KEY (`id`),
    FOREIGN KEY (`movie_id`) REFERENCES `movies`(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`cinema_id`) REFERENCES `cinemas`(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`shift_id`) REFERENCES `shifts`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

INSERT INTO `schedules` (movie_id, cinema_id, schedule_date, shift_id) 
VALUES (1, 1, '2024-10-22', 1);

INSERT INTO `schedules` (movie_id, cinema_id, schedule_date, shift_id) 
VALUES (2, 2, '2024-10-23', 1);

-- Ghế: Ghế có 2 loại: Vip và Regular, mỗi 1 ghế thuộc về 1 rạp, 1 ghế có tọa độ hàng(A,B,...), tọa độ cột(1, 2,...)
DROP TABLE IF EXISTS `seats`;
CREATE TABLE `seats` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `seat_type` VARCHAR(255),
    `cinema_id` INT NOT NULL,
    `seat_row` VARCHAR(2) NOT NULL,
    `seat_number` INT NOT NULL,
    `createddate` TIMESTAMP,
    `modifieddate` TIMESTAMP,
    `createdby` VARCHAR(255),
    `modifiedby` VARCHAR(255),
    PRIMARY KEY (`id`),
    FOREIGN KEY (`cinema_id`) REFERENCES `cinemas`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

INSERT INTO `seats` (seat_type, cinema_id, seat_row, seat_number) 
VALUES 
('VIP', 1, 'A', 1),
('Regular', 1, 'B', 2),
('Regular', 1, 'C', 3),
('VIP', 1, 'A', 2);

-- Bảng đánh giá: Người dùng có thể đánh giá cho phim, gồm mã phim, mã người dùng
DROP TABLE IF EXISTS `ratings`;
CREATE TABLE `ratings` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `user_id` INT NOT NULL,
    `movie_id` INT NOT NULL,
    `rating_value` INT NOT NULL,
    `rating_comment` VARCHAR(255),
    `createddate` TIMESTAMP,
    `modifieddate` TIMESTAMP,
    `createdby` VARCHAR(255),
    `modifiedby` VARCHAR(255),
    PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id`) REFERENCES `users`(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`movie_id`) REFERENCES `movies`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

INSERT INTO `ratings` (user_id, movie_id, rating_value, rating_comment) 
VALUES 
(1, 1, 5, 'OKE'),
(2, 2, 4, 'cc');

-- Booking_details: Chi tiết về 1 đơn hàng: Gồm người đặt, mã ghế, kíp chiếu, giá gốc, giá sau giảm, ngày đặt, trạng thái thanh toán
DROP TABLE IF EXISTS `booking_details`;
CREATE TABLE `booking_details` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `user_id` INT NOT NULL,
    `seat_id` INT NOT NULL,
    `schedule_id` INT NOT NULL,
    `original_price` INT,
    `discounted_price` INT,
    `order_time` DATETIME,
    `payment_status` TINYINT(1),
    `createddate` TIMESTAMP,
    `modifieddate` TIMESTAMP,
    `createdby` VARCHAR(255),
    `modifiedby` VARCHAR(255),
    PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id`) REFERENCES `users`(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`seat_id`) REFERENCES `seats`(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`schedule_id`) REFERENCES `schedules`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

-- INSERT INTO `booking_details` (user_id, seat_id, schedule_id, original_price, discounted_price, order_time, payment_status) 
-- VALUES 
-- (1, 1, 1, 100000, 80000, '2024-10-22 14:30:00', 1),
-- (2, 2, 2, 120000, 100000, '2024-10-23 16:00:00', 0);

-- Giá vé gốc phụ thuộc vào phim và loại ghế
DROP TABLE IF EXISTS `movie_seat_prices`;
CREATE TABLE `movie_seat_prices` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `movie_id` INT NOT NULL,
    `seat_type` VARCHAR(255) NOT NULL,
    `base_price` INT NOT NULL,
    `createddate` TIMESTAMP,
    `modifieddate` TIMESTAMP,
    `createdby` VARCHAR(255),
    `modifiedby` VARCHAR(255),
    PRIMARY KEY (`id`),
    FOREIGN KEY (`movie_id`) REFERENCES `movies`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

INSERT INTO `movie_seat_prices` (movie_id, seat_type, base_price) 
VALUES 
(1, 'VIP', 200000),
(1, 'Regular', 150000);

-- Xem trạng thái của ghế ở 1 phiên đã được đặt chưa
DROP TABLE IF EXISTS `seat_schedule_status`;
CREATE TABLE `seat_schedule_status` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `seat_id` INT NOT NULL,
    `schedule_id` INT NOT NULL,
    `status` TINYINT(1) NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`seat_id`) REFERENCES `seats`(`id`),
    FOREIGN KEY (`schedule_id`) REFERENCES `schedules`(`id`) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;




    
