-- MySQL dump 10.13  Distrib 8.0.23, for Win64 (x86_64)
--
-- Host: localhost    Database: webdb
-- ------------------------------------------------------
-- Server version	8.0.23

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
-- Table structure for table `drug_pictures`
--

DROP TABLE IF EXISTS `drug_pictures`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `drug_pictures` (
  `drug_picture_id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `drug_picture` mediumblob NOT NULL,
  `picture_drug_id` int unsigned NOT NULL,
  PRIMARY KEY (`drug_picture_id`),
  UNIQUE KEY `drugs_picture_id_UNIQUE` (`drug_picture_id`),
  KEY `drug_id_idx` (`picture_drug_id`),
  CONSTRAINT `path_drug_id` FOREIGN KEY (`picture_drug_id`) REFERENCES `drugs` (`drug_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `drugs`
--

DROP TABLE IF EXISTS `drugs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `drugs` (
  `drug_id` int unsigned NOT NULL AUTO_INCREMENT,
  `drug_name` varchar(45) NOT NULL,
  `amount` int(10) unsigned zerofill NOT NULL,
  `description` text NOT NULL,
  `need_prescription` tinyint NOT NULL,
  PRIMARY KEY (`drug_id`),
  UNIQUE KEY `drug_name_UNIQUE` (`drug_name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `order_id` bigint unsigned NOT NULL,
  `customer_id` bigint unsigned NOT NULL,
  `order_drug_id` int unsigned NOT NULL,
  `amount` smallint unsigned NOT NULL,
  `dosage` tinyint unsigned NOT NULL,
  `prescription_id` bigint unsigned DEFAULT NULL,
  `status` tinyint NOT NULL,
  PRIMARY KEY (`order_id`),
  KEY `customer_id_idx` (`customer_id`),
  KEY `drug_id_idx` (`order_drug_id`),
  KEY `prescription_id_idx` (`prescription_id`),
  CONSTRAINT `customer_id` FOREIGN KEY (`customer_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `drug_id` FOREIGN KEY (`order_drug_id`) REFERENCES `drugs` (`drug_id`),
  CONSTRAINT `prescription_id` FOREIGN KEY (`prescription_id`) REFERENCES `prescriptions` (`prescription_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `prescription_statuses`
--

DROP TABLE IF EXISTS `prescription_statuses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `prescription_statuses` (
  `prescription_status_id` tinyint unsigned NOT NULL AUTO_INCREMENT,
  `prescription_status` varchar(20) NOT NULL,
  PRIMARY KEY (`prescription_status_id`),
  UNIQUE KEY `prescription_status_id_UNIQUE` (`prescription_status_id`),
  UNIQUE KEY `prescription_status_UNIQUE` (`prescription_status`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `prescriptions`
--

DROP TABLE IF EXISTS `prescriptions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `prescriptions` (
  `prescription_id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `customer_id` bigint unsigned NOT NULL,
  `doctor_id` bigint unsigned NOT NULL,
  `prescription_drug_id` int unsigned NOT NULL,
  `amount` int unsigned NOT NULL,
  `issue_date` bigint DEFAULT NULL,
  `end_date` bigint DEFAULT NULL,
  `prescription_status_id` tinyint unsigned DEFAULT NULL,
  PRIMARY KEY (`prescription_id`),
  KEY `prescription_drug_id_idx` (`prescription_drug_id`),
  KEY `prescription_customer_id_idx` (`customer_id`),
  KEY `prescription_employee_id_idx` (`doctor_id`),
  KEY `prescription_status_id_idx` (`prescription_status_id`),
  CONSTRAINT `prescription_customer_id` FOREIGN KEY (`customer_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `prescription_drug_id` FOREIGN KEY (`prescription_drug_id`) REFERENCES `drugs` (`drug_id`),
  CONSTRAINT `prescription_employee_id` FOREIGN KEY (`doctor_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `prescription_status_id` FOREIGN KEY (`prescription_status_id`) REFERENCES `prescription_statuses` (`prescription_status_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `role_id` int unsigned NOT NULL AUTO_INCREMENT,
  `role` varchar(20) NOT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `surname` varchar(50) NOT NULL,
  `email` varchar(75) NOT NULL,
  `password` varchar(40) NOT NULL,
  `role_id` int unsigned NOT NULL,
  `amount` decimal(10,2) unsigned DEFAULT '0.00',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  KEY `role_id_idx` (`role_id`),
  CONSTRAINT `role_id` FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-03-18  1:41:59
