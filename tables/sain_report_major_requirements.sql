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
-- Table structure for table `major_requirements`
--

DROP TABLE IF EXISTS `major_requirements`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `major_requirements` (
  `majorRequirments_id` int(11) NOT NULL AUTO_INCREMENT,
  `major_id` int(11) NOT NULL,
  `area_type` varchar(45) NOT NULL,
  `course_credit_name` varchar(45) NOT NULL,
  `course_credits` double NOT NULL,
  `course_area` varchar(45) NOT NULL,
  PRIMARY KEY (`majorRequirments_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `major_requirements`
--

LOCK TABLES `major_requirements` WRITE;
/*!40000 ALTER TABLE `major_requirements` DISABLE KEYS */;
INSERT INTO `major_requirements` VALUES (1,1,'Computer Science','CST',24,'Computer_Science'),(2,1,'Computer Science','ENG',6,'English               '),(3,1,'Computer Science','HUM',6,'Humanities'),(4,1,'Computer Science','SOS',6,'Social_Sciences'),(5,1,'Computer Science','SCI',8,'Science'),(6,1,'Computer Science','PED',2,'Physical_Education'),(7,1,'Computer Science','COL',1.5,'Freshman_Seminar'),(8,1,'Computer Science','MAT',8,'Mathematics'),(9,2,'Information Tech','CST',40,'Computer_Science'),(10,2,'Information Tech','ENG',6,'English                  '),(11,2,'Information Tech','HUM',3,'Humanities             '),(12,2,'Information Tech','SOS',6,'Social_Sciences     '),(13,2,'Information Tech','MAT',8,'Mathematics         '),(14,2,'Information Tech','BUS',6,'Bussiness             '),(15,2,'Information Tech','COL',1.5,'Freshman_Seminar'),(16,2,'Information Tech','PED',2,'Physical Education ');
/*!40000 ALTER TABLE `major_requirements` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-05-08 19:09:20
