-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: localhost    Database: qlsv
-- ------------------------------------------------------
-- Server version	8.0.42

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
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'admin@gmail.com','$2a$10$lvq/0cLc6Hq/DyeSFsLazODjyVoO3zTsPVoEMXIDTRnRRIl8NeIoi','ROLE_ADMIN'),(2,'nnt@gmail.com','$2a$10$76jjoL7ul2fARLRIBNPNbODlbdixXc5FaobMMvcZZZbuRCxXhJxO.','ROLE_STUDENT'),(3,'nhh@gmail.com','$2a$10$mPqZKm9T..UEVlg0/fVw1ePpgautiCUtDhisjkHlWhmEwqpx7tEc2','ROLE_STUDENT'),(4,'ndd@gmail.com','$2a$10$CNuJUPS.JXm8Ok.fVxy8O.105a8g2NDsgk5jvehZ.KB1ivdU9g29W','ROLE_STUDENT'),(5,'ptp@gmail.com','$2a$10$j.cPOzY7GypzC.9KLPWkJ.zjOIzdt7vN3zplB/eI9wVnkAX99wsqi','ROLE_TEACHER'),(7,'pda@gmail.com','$2a$10$Op/xsSlYnPAZllKJBZQtiuWYp2lnYpiLqca.787JYaMWSgAUEsOty','ROLE_STUDENT'),(8,'nctm@gmail.com','$2a$10$ewBtJBBStH8bRoran27kVuVcXu/PeVVGn9RI.lJGLeb7RBshZcWNa','ROLE_STUDENT'),(9,'ndtd@gmail.com','$2a$10$WXzTnDzIxAPbQZITNTrk.uEHo5N135WhPw8zypJ4S18jYFNlpskzy','ROLE_STUDENT'),(11,'ndt@gmail.com','$2a$10$Xh0gS0bT26wj.r9i8cG2BeQ5qIT4DHrYK74QxQZ41LW6tYZbovFm6','ROLE_STUDENT'),(14,'phuongdiemxua@gmail.com','$2a$10$M5M2dOtRyscXDk9MKE3bsOGFKx0QmCVOqJSsEL0xOl6D6s3ptiBva','ROLE_TEACHER'),(15,'nlb@gmail.com','$2a$10$LnCz9kNURI9S.EFc5gPtF.G9kIzd9NN91pZlgXakd3N57CYY7GlWu','ROLE_TEACHER');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-30 20:10:21
