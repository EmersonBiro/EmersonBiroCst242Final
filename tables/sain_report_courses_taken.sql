-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: localhost    Database: sain_report
-- ------------------------------------------------------
-- Server version	5.7.12-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `courses_taken`
--

DROP TABLE IF EXISTS `courses_taken`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `courses_taken` (
  `coursesTaken_id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` int(11) NOT NULL,
  `course_name` varchar(45) NOT NULL,
  `course_number` int(11) NOT NULL,
  `course_credit_name` varchar(45) NOT NULL,
  `grade` varchar(45) NOT NULL,
  `course_credit` double NOT NULL,
  PRIMARY KEY (`coursesTaken_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courses_taken`
--

LOCK TABLES `courses_taken` WRITE;
/*!40000 ALTER TABLE `courses_taken` DISABLE KEYS */;
INSERT INTO `courses_taken` VALUES (1,1,'CST',141,'CST','A',4),(2,1,'MAT',141,'MAT','A',4),(3,1,'CST',242,'CST','F',4),(4,1,'CST',246,'CST','A',4),(5,1,'MAT',102,'MAT','A',4),(6,1,'BIO',130,'SCI','A',4),(7,1,'MAT',114,'NUL','A',4),(8,1,'HUM',162,'HUM','A',3),(9,1,'NUR',101,'NUR','A',9),(10,1,'BUS',101,'BUS','B',3),(11,1,'PSY',101,'SOS','B+',3),(13,2,'CST',141,'CST','A',4),(14,2,'MAT',125,'NUL','B+',4),(15,2,'COL',101,'COL','A',1.5);
/*!40000 ALTER TABLE `courses_taken` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-05-08 19:09:21
