-- MySQL dump 10.13  Distrib 8.0.46, for Win64 (x86_64)
--
-- Host: localhost    Database: parking_database
-- ------------------------------------------------------
-- Server version	8.0.46

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `parking_report`
--

DROP TABLE IF EXISTS `parking_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `parking_report` (
  `id` int NOT NULL AUTO_INCREMENT,
  `owner` varchar(50) NOT NULL,
  `plate` varchar(20) NOT NULL,
  `vehicle` varchar(20) NOT NULL,
  `time_in` datetime NOT NULL,
  `time_out` datetime DEFAULT NULL,
  `duration` varchar(20) DEFAULT NULL,
  `status` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parking_report`
--

LOCK TABLES `parking_report` WRITE;
/*!40000 ALTER TABLE `parking_report` DISABLE KEYS */;
INSERT INTO `parking_report` VALUES (1,'26-0425','ABC123','Motorcycle','2026-10-28 21:16:12','2026-10-29 16:18:12',NULL,'EXITED'),(2,'Cyrlle','CJ2006','Car','2026-10-28 21:23:24','2026-10-28 21:25:52','0h 2m','EXITED'),(3,'26-0204','CRK2021','Car','2026-10-28 21:23:47','2026-10-29 16:18:12',NULL,'EXITED'),(4,'asd','AAAA','Motorcycle','2026-10-28 21:26:33','2026-10-28 21:28:37','0h 2m','EXITED'),(5,'cj monedero','122006','Car','2026-10-28 22:19:18','2026-10-29 16:18:12',NULL,'EXITED'),(6,'iceman','202627','Car','2026-10-28 22:26:24','2026-10-29 16:18:12',NULL,'EXITED'),(7,'26-3216','LMNOP','Car','2026-10-29 15:45:42','2026-10-29 16:18:12',NULL,'EXITED'),(8,'joyboi','QRST','Car','2026-10-29 15:46:15','2026-10-29 16:18:12',NULL,'EXITED'),(9,'26-6666','ABCDE','Motorcycle','2026-10-29 15:46:45','2026-10-29 16:18:12',NULL,'EXITED'),(10,'MONEDERO','CJM2006','Car','2026-10-29 16:31:49','2026-10-29 16:34:56','0h 3m','EXITED'),(11,'RUBIO','KRM2026','Motorcycle','2026-10-29 16:32:13','2026-10-29 16:34:56','0h 2m','EXITED'),(12,'26-0000','01234','Car','2026-10-29 17:00:07','2026-10-29 17:04:59','0h 4m','EXITED');
/*!40000 ALTER TABLE `parking_report` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-10-29 22:10:49
