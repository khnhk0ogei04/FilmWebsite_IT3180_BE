-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: web_film_update
-- ------------------------------------------------------
-- Server version	5.5.5-10.4.32-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `booking_details`
--

DROP TABLE IF EXISTS `booking_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `booking_details` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `seat_id` bigint(20) NOT NULL,
  `schedule_id` bigint(20) NOT NULL,
  `original_price` bigint(20) DEFAULT NULL,
  `discounted_price` bigint(20) DEFAULT NULL,
  `order_time` datetime DEFAULT current_timestamp(),
  `payment_status` tinyint(1) DEFAULT 0,
  `created_date` datetime(6) DEFAULT NULL,
  `modified_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `booking_details_ibfk_1` (`seat_id`),
  KEY `booking_details_ibfk_2` (`schedule_id`),
  KEY `booking_details_ibfk_3` (`user_id`),
  CONSTRAINT `booking_details_ibfk_1` FOREIGN KEY (`seat_id`) REFERENCES `seats` (`id`) ON DELETE CASCADE,
  CONSTRAINT `booking_details_ibfk_2` FOREIGN KEY (`schedule_id`) REFERENCES `schedule` (`id`) ON DELETE CASCADE,
  CONSTRAINT `booking_details_ibfk_3` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `booking_details`
--

LOCK TABLES `booking_details` WRITE;
/*!40000 ALTER TABLE `booking_details` DISABLE KEYS */;
INSERT INTO `booking_details` VALUES (27,12,13,6,140000,140000,'2024-11-14 21:41:47',1,NULL,NULL),(28,12,13,8,200000,200000,'2024-11-14 22:17:14',1,NULL,NULL),(29,12,12,8,300000,300000,'2024-11-14 22:17:17',1,NULL,NULL),(30,10,13,2,120000,90000,'2024-11-25 12:58:32',1,NULL,NULL),(31,10,9,5,300000,300000,'2024-11-26 23:21:14',1,NULL,NULL),(32,3,14,8,200000,200000,'2024-12-04 13:52:50',1,'2024-12-04 13:52:50.000000',NULL),(33,3,12,6,210000,210000,'2024-12-04 14:00:48',1,'2024-12-04 14:00:48.000000',NULL),(34,3,11,6,210000,210000,'2024-12-04 14:01:08',1,'2024-12-04 14:01:08.000000',NULL),(35,3,16,6,140000,140000,'2024-12-04 19:58:51',1,'2024-12-04 19:58:51.000000',NULL),(36,3,14,6,140000,140000,'2024-12-04 19:59:11',1,'2024-12-04 19:59:11.000000',NULL),(37,3,15,6,140000,140000,'2024-12-04 20:09:18',1,'2024-12-04 20:09:18.000000',NULL);
/*!40000 ALTER TABLE `booking_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `modified_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `category_name` (`category_name`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (1,'Khoa học viễn tưởng','https://th.bing.com/th/id/OIP.Ebrr4G21_gJfFECqG4wpRQHaKN?rs=1&pid=ImgDetMain',NULL,NULL),(2,'Hành động','https://th.bing.com/th/id/OIP.Ebrr4G21_gJfFECqG4wpRQHaKN?rs=1&pid=ImgDetMain',NULL,NULL),(3,'Tâm lý - Tình cảm','https://i.pinimg.com/736x/4d/2d/63/4d2d6354a838ff8e63cee70eedb5ec1a.jpg',NULL,NULL),(4,'hanh dong','https://upload.wikimedia.org/wikipedia/vi/4/42/%C3%81p_ph%C3%ADch_phim_M%E1%BA%AFt_bi%E1%BA%BFc.jpg',NULL,NULL),(5,'Phim Cung Đấu','http://res.cloudinary.com/davwqqao1/image/upload/v1729606620/jfqjhhd9xnmiaecc0mu0.jpg',NULL,NULL),(6,'Phim Cổ Trang','http://res.cloudinary.com/davwqqao1/image/upload/v1730731142/v7qzt8auesmw7swbp8bo.jpg',NULL,NULL),(7,'Phim Cung Đấu 2','http://res.cloudinary.com/davwqqao1/image/upload/v1732480223/lbnual8qguebw5gwddz3.png','2024-11-25 03:30:25.000000',NULL);
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cinemas`
--

DROP TABLE IF EXISTS `cinemas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cinemas` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cinema_name` varchar(255) NOT NULL,
  `cinema_address` varchar(255) NOT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `modified_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cinemas`
--

LOCK TABLES `cinemas` WRITE;
/*!40000 ALTER TABLE `cinemas` DISABLE KEYS */;
INSERT INTO `cinemas` VALUES (1,'Beta Mỹ Đình','Hà Nội','2024-12-06 08:50:33.000000','2024-12-06 08:50:33.000000'),(2,'Beta Thanh Xuân','Hà Nội','2024-12-06 08:50:33.000000','2024-12-06 08:50:33.000000'),(3,'Beta Đan Phượng','Hà Nội','2024-12-06 08:50:33.000000','2024-12-06 08:50:33.000000'),(4,'Beta Thái Nguyên','Hà Nội','2024-12-06 08:50:33.000000','2024-12-06 08:50:33.000000'),(5,'Beta Biên Hòa','Hà Nội','2024-12-06 08:50:33.000000','2024-12-06 08:50:33.000000'),(6,'Beta Long Khánh','Hà Nội','2024-12-06 08:50:33.000000','2024-12-06 08:50:33.000000'),(7,'Beta Long Thành','Hà Nội','2024-12-06 08:50:33.000000','2024-12-06 08:50:33.000000'),(8,'CGV Thái Hà','Hà Nội','2024-12-06 08:50:33.000000','2024-12-06 08:50:33.000000'),(9,'Beta Giải Phóng','Hà Nội','2024-12-06 08:50:33.000000','2024-12-06 08:50:33.000000');
/*!40000 ALTER TABLE `cinemas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `discount`
--

DROP TABLE IF EXISTS `discount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `discount` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `discount_percentage` bigint(20) NOT NULL,
  `notification_title` varchar(255) DEFAULT NULL,
  `notification_content` varchar(255) DEFAULT NULL,
  `movie_id` bigint(20) NOT NULL,
  `created_date` datetime DEFAULT current_timestamp(),
  `modified_date` datetime DEFAULT current_timestamp(),
  PRIMARY KEY (`id`),
  KEY `discount_ibfk_1` (`movie_id`),
  CONSTRAINT `discount_ibfk_1` FOREIGN KEY (`movie_id`) REFERENCES `movies` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `discount`
--

LOCK TABLES `discount` WRITE;
/*!40000 ALTER TABLE `discount` DISABLE KEYS */;
INSERT INTO `discount` VALUES (4,25,'Giảm giá bán ế','<p><strong>GIẢM GI&Aacute; 25% TIỀN</strong></p>\n<table style=\"border-collapse: collapse; width: 100.036%;\" border=\"1\"><colgroup><col style=\"width: 99.9112%;\"></colgroup>\n<tbody>\n<tr>\n<td>Giảm gi&aacute; ABCXYZ</td>\n</tr>\n</tbody>\n</table>\n<p>&nbsp;</p>',2,'2024-12-06 08:54:28','2024-12-06 08:54:28'),(8,20,'Giam gia 20%','<p>Giam gia 20%</p>',10,'2024-12-06 08:54:28','2024-12-06 08:54:28');
/*!40000 ALTER TABLE `discount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movie_seat_prices`
--

DROP TABLE IF EXISTS `movie_seat_prices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `movie_seat_prices` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `movie_id` bigint(20) NOT NULL,
  `seat_type` varchar(255) NOT NULL,
  `base_price` bigint(20) NOT NULL,
  `created_date` datetime(6) DEFAULT current_timestamp(6),
  `modified_date` datetime(6) DEFAULT current_timestamp(6) ON UPDATE current_timestamp(6),
  PRIMARY KEY (`id`),
  KEY `movie_seat_prices_ibfk_1` (`movie_id`),
  CONSTRAINT `movie_seat_prices_ibfk_1` FOREIGN KEY (`movie_id`) REFERENCES `movies` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movie_seat_prices`
--

LOCK TABLES `movie_seat_prices` WRITE;
/*!40000 ALTER TABLE `movie_seat_prices` DISABLE KEYS */;
INSERT INTO `movie_seat_prices` VALUES (1,1,'VIP',150000,'2024-12-06 09:48:37.326605','2024-12-06 09:48:37.326605'),(2,1,'Regular',100000,'2024-12-06 09:48:37.326605','2024-12-06 09:48:37.326605'),(3,2,'VIP',180000,'2024-12-06 09:48:37.326605','2024-12-06 09:48:37.326605'),(4,2,'Regular',120000,'2024-12-06 09:48:37.326605','2024-12-06 09:48:37.326605'),(17,10,'VIP',200000,'2024-12-06 09:48:37.326605','2024-12-06 09:48:37.326605'),(18,10,'Regular',100000,'2024-12-06 09:48:37.326605','2024-12-06 09:48:37.326605'),(21,12,'VIP',300000,'2024-12-06 09:48:37.326605','2024-12-06 09:48:37.326605'),(22,12,'Regular',200000,'2024-12-06 09:48:37.326605','2024-12-06 09:48:37.326605'),(23,13,'VIP',210000,'2024-12-06 09:48:37.326605','2024-12-06 09:48:37.326605'),(24,13,'Regular',140000,'2024-12-06 09:48:37.326605','2024-12-06 09:48:37.326605'),(25,14,'VIP',150000,'2024-12-06 09:48:37.326605','2024-12-06 09:48:37.326605'),(26,14,'Regular',100000,'2024-12-06 09:48:37.326605','2024-12-06 09:48:37.326605'),(27,15,'VIP',150000,'2024-12-06 09:48:37.326605','2024-12-06 09:48:37.326605'),(28,15,'Regular',120000,'2024-12-06 09:48:37.326605','2024-12-06 09:48:37.326605');
/*!40000 ALTER TABLE `movie_seat_prices` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movies`
--

DROP TABLE IF EXISTS `movies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `movies` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `movie_name` varchar(255) NOT NULL,
  `movie_category_id` bigint(20) NOT NULL,
  `movie_description` varchar(255) DEFAULT NULL,
  `movie_directors` varchar(255) DEFAULT NULL,
  `movie_cast` varchar(255) DEFAULT NULL,
  `running_time` time DEFAULT '00:00:00',
  `image` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `trailer_url` varchar(255) DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `modified_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `movies_ibfk_1` (`movie_category_id`),
  CONSTRAINT `movies_ibfk_1` FOREIGN KEY (`movie_category_id`) REFERENCES `categories` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movies`
--

LOCK TABLES `movies` WRITE;
/*!40000 ALTER TABLE `movies` DISABLE KEYS */;
INSERT INTO `movies` VALUES (1,'Inception',1,'<p>nam mô a di đà lạt 2345</p>','Christopher Nolan','Leonardo DiCaprio, Joseph Gordon-Levitt, Ellen Page','02:28:00','https://upload.wikimedia.org/wikipedia/vi/4/42/%C3%81p_ph%C3%ADch_phim_M%E1%BA%AFt_bi%E1%BA%BFc.jpg',1,NULL,NULL,'2024-12-04 13:28:02.000000'),(2,'Parasite',2,'<p>A poor family schemes to become employed by a wealthy family and infiltrates their household by posing as unrelated, highly qualified individuals.</p>','Bong Joon-ho','Song Kang-ho, Lee Sun-kyun, Cho Yeo-jeong','02:12:00','https://upload.wikimedia.org/wikipedia/vi/4/42/%C3%81p_ph%C3%ADch_phim_M%E1%BA%AFt_bi%E1%BA%BFc.jpg',1,'https://www.youtube.com/embed/IzSYlr3VI1A?si=RilhcSsI7eF3nvGN',NULL,'2024-11-27 00:18:10.000000'),(10,'Đi giữa trời rực rỡ',3,'<p>Pu Chải Th&aacute;i</p>','Đỗ Thanh Sơn','Đéo biết','01:27:00','http://res.cloudinary.com/davwqqao1/image/upload/v1729599116/qv7nmjowuqg6hizyt57y.jpg',0,'https://www.youtube.com/embed/pjm2aXT3A2M?si=TKAzAaB1-0qg9ODh',NULL,NULL),(12,'Ngày xưa có một chuyện tình',3,'<p><strong>XYZABC 123</strong></p>','Trịnh Đình Lê Minh','Avin Lu, Ngọc Xuân, Đỗ Nhật Hoàng','02:15:00','http://res.cloudinary.com/davwqqao1/image/upload/v1729971444/nizb3dcirbzatr1xzgjo.jpg',1,'https://www.youtube.com/embed/qaeHlk0OXec?si=jRsihgtQzKu-SQYB',NULL,NULL),(13,'THE CURSED LAND: VÙNG ĐẤT BỊ NGUYỀN RỦA',1,'<div>\n<p>Một g&oacute;a phụ v&agrave; con g&aacute;i đi đến miền Nam Th&aacute;i Lan để t&igrave;m sự gi&uacute;p đỡ từ một b&aacute;c sĩ Hồi gi&aacute;o sau khi giải tho&aacute;t một con quỷ trong một ng&ocirc;i nh&agrave; tồi t&agrave;n.</p>\n</div>','Panu Aree, Kong Rithdee','Bront Palarae, Ananda Everingham','01:57:00','http://res.cloudinary.com/davwqqao1/image/upload/v1730038413/govrasukgztbwh8lphwf.jpg',1,'https://www.youtube.com/embed/NKn9ygztcDs?si=Km0Ud__lyNHYsqdc',NULL,NULL),(14,'Tiên tri tử thần ',2,'<ul><li>test xem nào <strong>màu chữ đậm <em>nghiêng gạch dưới</em></strong></li><li><em>Pickaball<span class=\"ql-cursor\">﻿</span></em></li></ul>','Lee Yun-seok','Park Ju-hyun, Jaehyun, Kwak Si-yang, Lee Soo-jung','01:20:00','http://res.cloudinary.com/davwqqao1/image/upload/v1730317865/nivqj0qsree2d9imhswx.png',1,'https://www.youtube.com/embed/new1kh3wnIA?si=pWXfNG9lYKZlBQZT',NULL,'2024-11-27 00:11:29.000000'),(15,'Jack - J97',5,'<p>J97 Dom dom a123 123</p>','Khánh','Johny Tuyên','01:20:00','https://th.bing.com/th/id/OIP.XuET31MzZLS408arK01nxwHaHa?w=163&h=180&c=7&r=0&o=5&dpr=1.3&pid=1.7',1,'https://www.youtube.com/embed/uN4g5XJRNo8?si=wmc2y1axriv1XJ7C','2024-11-24 03:14:59.000000','2024-11-24 03:51:11.000000');
/*!40000 ALTER TABLE `movies` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ratings`
--

DROP TABLE IF EXISTS `ratings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ratings` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `movie_id` bigint(20) NOT NULL,
  `rating_value` bigint(20) DEFAULT NULL,
  `rating_comment` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ratings_ibfk_1` (`user_id`),
  KEY `ratings_ibfk_2` (`movie_id`),
  CONSTRAINT `ratings_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  CONSTRAINT `ratings_ibfk_2` FOREIGN KEY (`movie_id`) REFERENCES `movies` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ratings`
--

LOCK TABLES `ratings` WRITE;
/*!40000 ALTER TABLE `ratings` DISABLE KEYS */;
INSERT INTO `ratings` VALUES (1,2,1,5,'Phim rất hay',NULL,NULL),(2,1,2,4,'Như cl',NULL,NULL),(3,9,1,4,'meo meo meo',NULL,NULL),(13,10,13,4,'abc',NULL,NULL),(14,10,12,4,'hay',NULL,NULL),(15,3,1,3,'oke ổn ',NULL,NULL),(16,3,12,1,'chee',NULL,NULL),(17,3,12,4,'12345',NULL,NULL);
/*!40000 ALTER TABLE `ratings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` bigint(20) NOT NULL,
  `code` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'ADMIN','Quản Trị Viên'),(2,'USER','Khách Hàng');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `schedule`
--

DROP TABLE IF EXISTS `schedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `schedule` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `movie_id` bigint(20) NOT NULL,
  `cinema_id` bigint(20) NOT NULL,
  `schedule_date` date DEFAULT NULL,
  `shift_id` bigint(20) NOT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `modified_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `schedule_ibfk_2` (`cinema_id`),
  KEY `schedule_ibfk_3` (`shift_id`),
  KEY `schedule_ibfk_1` (`movie_id`),
  CONSTRAINT `schedule_ibfk_1` FOREIGN KEY (`movie_id`) REFERENCES `movies` (`id`) ON DELETE CASCADE,
  CONSTRAINT `schedule_ibfk_2` FOREIGN KEY (`cinema_id`) REFERENCES `cinemas` (`id`) ON DELETE CASCADE,
  CONSTRAINT `schedule_ibfk_3` FOREIGN KEY (`shift_id`) REFERENCES `shifts` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schedule`
--

LOCK TABLES `schedule` WRITE;
/*!40000 ALTER TABLE `schedule` DISABLE KEYS */;
INSERT INTO `schedule` VALUES (1,1,1,'2024-10-10',1,NULL,NULL),(2,2,2,'2024-10-10',1,NULL,NULL),(3,1,2,'2024-10-11',2,NULL,NULL),(5,12,1,'2024-11-01',1,NULL,NULL),(6,13,2,'2024-11-01',2,NULL,NULL),(7,12,2,'2024-11-01',3,NULL,NULL),(8,12,2,'2024-11-07',3,NULL,NULL),(9,15,2,'2024-11-27',2,'2024-11-25 03:29:40.000000',NULL);
/*!40000 ALTER TABLE `schedule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seat_schedule_status`
--

DROP TABLE IF EXISTS `seat_schedule_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `seat_schedule_status` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `seat_id` bigint(20) NOT NULL,
  `schedule_id` bigint(20) NOT NULL,
  `status` int(11) NOT NULL,
  `created_date` datetime DEFAULT current_timestamp(),
  `modified_date` datetime DEFAULT current_timestamp(),
  PRIMARY KEY (`id`),
  KEY `seat_schedule_status_ibfk_1` (`seat_id`),
  KEY `seat_schedule_status_ibfk_2` (`schedule_id`),
  CONSTRAINT `seat_schedule_status_ibfk_1` FOREIGN KEY (`seat_id`) REFERENCES `seats` (`id`) ON DELETE CASCADE,
  CONSTRAINT `seat_schedule_status_ibfk_2` FOREIGN KEY (`schedule_id`) REFERENCES `schedule` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seat_schedule_status`
--

LOCK TABLES `seat_schedule_status` WRITE;
/*!40000 ALTER TABLE `seat_schedule_status` DISABLE KEYS */;
INSERT INTO `seat_schedule_status` VALUES (29,13,6,1,'2024-12-06 09:29:52','2024-12-06 09:29:52'),(30,13,8,1,'2024-12-06 09:29:52','2024-12-06 09:29:52'),(31,12,8,1,'2024-12-06 09:29:52','2024-12-06 09:29:52'),(32,13,2,1,'2024-12-06 09:29:52','2024-12-06 09:29:52'),(33,9,5,1,'2024-12-06 09:29:52','2024-12-06 09:29:52'),(34,14,8,1,'2024-12-06 09:29:52','2024-12-06 09:29:52'),(35,12,6,1,'2024-12-06 09:29:52','2024-12-06 09:29:52'),(36,11,6,1,'2024-12-06 09:29:52','2024-12-06 09:29:52'),(37,16,6,1,'2024-12-06 09:29:52','2024-12-06 09:29:52'),(38,14,6,1,'2024-12-06 09:29:52','2024-12-06 09:29:52'),(39,15,6,1,'2024-12-06 09:29:52','2024-12-06 09:29:52');
/*!40000 ALTER TABLE `seat_schedule_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seats`
--

DROP TABLE IF EXISTS `seats`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `seats` (
  `id` bigint(20) NOT NULL,
  `seat_type` varchar(50) NOT NULL,
  `cinema_id` bigint(20) NOT NULL,
  `seat_row` varchar(2) DEFAULT NULL,
  `seat_number` bigint(20) NOT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `seats_ibfk_1` (`cinema_id`),
  CONSTRAINT `seats_ibfk_1` FOREIGN KEY (`cinema_id`) REFERENCES `cinemas` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seats`
--

LOCK TABLES `seats` WRITE;
/*!40000 ALTER TABLE `seats` DISABLE KEYS */;
INSERT INTO `seats` VALUES (1,'Regular',1,'A',1,NULL,NULL),(2,'Regular',1,'A',2,NULL,NULL),(3,'VIP',1,'B',1,NULL,NULL),(4,'VIP',1,'B',2,NULL,NULL),(5,'Regular',1,'A',3,NULL,NULL),(6,'Regular',1,'A',4,NULL,NULL),(7,'Regular',1,'A',5,NULL,NULL),(8,'Regular',1,'A',6,NULL,NULL),(9,'VIP',1,'B',3,NULL,NULL),(10,'VIP',1,'B',4,NULL,NULL),(11,'VIP',2,'A',1,NULL,NULL),(12,'VIP',2,'B',1,NULL,NULL),(13,'Regular',2,'A',2,NULL,NULL),(14,'Regular',2,'A',3,NULL,NULL),(15,'Regular',2,'A',4,NULL,NULL),(16,'Regular',2,'A',5,NULL,NULL),(17,'Regular',2,'A',6,NULL,NULL),(18,'Regular',2,'B',2,NULL,NULL);
/*!40000 ALTER TABLE `seats` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shifts`
--

DROP TABLE IF EXISTS `shifts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shifts` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `shift_start` time NOT NULL,
  `shift_end` time NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shifts`
--

LOCK TABLES `shifts` WRITE;
/*!40000 ALTER TABLE `shifts` DISABLE KEYS */;
INSERT INTO `shifts` VALUES (1,'Morning','07:00:00','11:00:00'),(2,'Afternoon','13:00:00','17:00:00'),(3,'Evening','19:00:00','23:00:00');
/*!40000 ALTER TABLE `shifts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `roleid` bigint(20) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `fullname` varchar(255) DEFAULT NULL,
  `avatar` longtext DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `status` int(11) NOT NULL DEFAULT 1,
  `account_balance` bigint(20) DEFAULT NULL,
  `order_count` bigint(20) DEFAULT NULL,
  `reset_token` varchar(255) DEFAULT NULL,
  `token_expiration` datetime DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `modified_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `users_ibfk_1` (`roleid`),
  CONSTRAINT `users_ibfk_1` FOREIGN KEY (`roleid`) REFERENCES `roles` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,1,'khanh04','$2a$10$94v8c/3U9.YEsmsm8ScvJ.AxTL3doOrD8n/vXQ.5mZWhTzEzFwZKW','Nguyễn Văn A','http://example.com/avatar1.jpg','admin@example.com','Hà Nội','123456',1,29500,0,'0e3e6a5c-e888-481d-83bc-1be7ee02f2fa','2024-11-14 22:03:51',NULL,NULL),(2,2,'cuong04','$2a$10$94v8c/3U9.YEsmsm8ScvJ.AxTL3doOrD8n/vXQ.5mZWhTzEzFwZKW','Nguyễn Văn B','http://example.com/avatar1.jpg','admin@example.com','Hà Nội','0123456789',1,40000,0,NULL,'1970-01-01 00:00:00',NULL,NULL),(3,2,'phuc04','$2a$10$uF0c13bBYW.mTN0db2N6gOx9bpxKpVf803QZgB5cnX8gpwt61D8se','Phúc Nguyễn','https://adoreyou.vn/wp-content/uploads/2-258.jpg','dcm04@gmail.com','37','0123456789',1,172000,0,NULL,'1970-01-01 00:00:00',NULL,'2024-12-04 20:09:18.000000'),(4,1,'khanhnd04','$2a$10$94v8c/3U9.YEsmsm8ScvJ.AxTL3doOrD8n/vXQ.5mZWhTzEzFwZKW','Nguyen Khanh','https://www.vbu.edu.vn/sites/default/files/public/teacher/avatars/t_nhat_tu.jpg','khanh8a04@gmail.com','29','0328873260',1,500000,0,NULL,'1970-01-01 00:00:00',NULL,NULL),(8,2,'khanhbkite10','$2a$10$/BW6h2Hr1pXE6vDAKkqYrumegt7MsTCQSsYx1ue8jIc73dw/dfH7a','Khanh Chim To','https://png.pngtree.com/png-clipart/20220719/original/pngtree-loading-icon-vector-transparent-png-image_8367371.png','khanh8a04@gmail.com','26','0328873260',1,1,0,NULL,NULL,NULL,NULL),(9,2,'khanhlonto','$2a$10$wcj7B1QZFqSHdparzlQ2veAQHrZE14Es2iKZWDYLAhNfHiE7rCQ9K','Khanh Chim To 02','https://png.pngtree.com/png-clipart/20220719/original/pngtree-loading-icon-vector-transparent-png-image_8367371.png','khanh8a04@gmail.com','26','0328873260',1,0,0,NULL,'1970-01-01 00:00:00',NULL,NULL),(10,2,'khanh12t1','$2a$10$.uzK3mku62Tz.GcEj2t2YeNukreX2abblO.i2Tl5YoguH0c6UU0Cu','Nguyễn Duy Khánh','','khanh8a04@gmail.com','30','0328873260',0,330000,0,'6bc145e1-5fcb-43a0-9b8d-442f5e85fdde','2024-11-26 17:36:32',NULL,'2024-11-26 23:21:15.000000'),(11,2,'khanhk72cnh','$2a$10$nWQLkF//Xc7Jp4snmFSaQurA4dszC5KJ44HQ26QlnCX/E7lFjJ7.2','Vi Thần ','','khanh8a04@gmail.com','30','0123456789',0,1000000,0,NULL,NULL,NULL,NULL),(12,2,'phucto','$2a$10$3ZhsMQkJjLF2.huK0X6oVeSr6oTZsWghRe6Kzreh.brh/HlUbaY5W','Phuc','','nnphuchust@gmail.com','Parky','0328873260',0,415000,0,'1f7cec43-c0e0-4866-a4ae-336d6971a89c','2024-11-14 21:39:41',NULL,'2024-11-25 04:18:34.000000'),(13,2,'khanh0404','$2a$10$3PmrmR/rAbNv9bYRGs2Hfet/ivLeEIEIhmJvNiNfP4zHNU1nf6rIW','Nguyen Khanh 001',NULL,'khanh8a04@gmail.com','29','0123456789',1,0,0,NULL,NULL,'2024-12-04 13:08:15.000000',NULL),(14,2,'khanhkhanh','$2a$10$kwDUW7qnkjczIFEhMfer7OxchND8El2Vf3L3SPVyzCdz0RbqotvPa','Nguyen Khanh 004',NULL,'khanh8a04@gmail.com','29','0123456789',1,0,0,NULL,NULL,'2024-12-04 22:27:00.000000',NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'web_film_update'
--

--
-- Dumping routines for database 'web_film_update'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-06  9:59:53
