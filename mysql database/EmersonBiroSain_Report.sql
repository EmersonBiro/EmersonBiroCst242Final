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
-- Table structure for table `accounts`
--

DROP TABLE IF EXISTS `accounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `accounts` (
  `account_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `level` int(11) NOT NULL,
  PRIMARY KEY (`account_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `accounts`
--

LOCK TABLES `accounts` WRITE;
/*!40000 ALTER TABLE `accounts` DISABLE KEYS */;
INSERT INTO `accounts` VALUES (1,'student1','student1',1),(2,'faculty','faculty',2),(3,'student2','student2',1),(4,'admin','admin',3);
/*!40000 ALTER TABLE `accounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admin` (
  `admin_id` int(11) NOT NULL AUTO_INCREMENT,
  `account_id` int(11) NOT NULL,
  `level` int(11) NOT NULL DEFAULT '3',
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `rank` varchar(45) NOT NULL,
  PRIMARY KEY (`admin_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES (1,4,3,'Tom','Bis','Dean');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `courses`
--

DROP TABLE IF EXISTS `courses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `courses` (
  `course_id` int(11) NOT NULL AUTO_INCREMENT,
  `course_name` varchar(45) NOT NULL,
  `course_number` varchar(45) NOT NULL,
  `area_required` varchar(45) NOT NULL,
  `course_credit` int(11) NOT NULL,
  PRIMARY KEY (`course_id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courses`
--

LOCK TABLES `courses` WRITE;
/*!40000 ALTER TABLE `courses` DISABLE KEYS */;
INSERT INTO `courses` VALUES (8,'MAT','142','MAT',4),(12,'CST','111','CST',4),(13,'CST','112','CST',4),(14,'CST','141','CST',4),(15,'CST','246','CST',4),(16,'CST','242','CST',4),(17,'CST','222','CST',4),(18,'MAT','141','MAT',4),(19,'PSY','101','SOS',3),(20,'HIS','101','HUM',3),(21,'PED','121','PED',1),(22,'COL','101','COL',2),(23,'ACC','101','BUS',4),(24,'ACC','102','BUS',4),(25,'BUS','112','BUS',4),(26,'ECO','101','BUS',3),(27,'ENG','101','ENG',3),(28,'ENG','102','ENG',3);
/*!40000 ALTER TABLE `courses` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courses_taken`
--

LOCK TABLES `courses_taken` WRITE;
/*!40000 ALTER TABLE `courses_taken` DISABLE KEYS */;
INSERT INTO `courses_taken` VALUES (1,1,'CST',141,'CST','A',4),(2,1,'MAT',141,'MAT','A',4),(3,1,'CST',242,'CST','C+',4),(4,1,'CST',246,'CST','A',4),(5,1,'MAT',102,'MAT','A',4),(6,1,'BIO',130,'SCI','D+',4),(7,1,'MAT',114,'NUL','W',4),(8,1,'HUM',162,'HUM','D+',3),(9,1,'NUR',101,'NUR','F',9),(10,1,'BUS',101,'BUS','D+',3),(11,1,'PSY',101,'SOS','B',3),(13,2,'CST',141,'CST','A',4),(14,2,'MAT',125,'NUL','B',4),(15,2,'COL',101,'COL','D+',1.5),(16,1,'BUS',112,'BUS','C',3),(17,1,'ACC',101,'BUS','C',4),(18,1,'PED',121,'PED','A',1.5),(19,1,'ENG',102,'ENG','A',3);
/*!40000 ALTER TABLE `courses_taken` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `courses_taking`
--

DROP TABLE IF EXISTS `courses_taking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `courses_taking` (
  `coursesTaking_id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` int(11) NOT NULL,
  `course_name` varchar(45) NOT NULL,
  `course_number` int(11) NOT NULL,
  `grade` varchar(45) NOT NULL DEFAULT 'IP',
  `course_credit` double NOT NULL,
  `course_credit_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`coursesTaking_id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courses_taking`
--

LOCK TABLES `courses_taking` WRITE;
/*!40000 ALTER TABLE `courses_taking` DISABLE KEYS */;
INSERT INTO `courses_taking` VALUES (21,2,'MAT',142,'IP',4,'MAT'),(25,2,'CST',111,'IP',4,'CST'),(26,2,'CST',246,'IP',4,'CST'),(30,1,'CST',111,'IP',4,'CST'),(35,1,'MAT',142,'IP',4,'MAT'),(38,1,'CST',141,'IP',4,'CST'),(40,1,'ECO',101,'IP',3,'BUS');
/*!40000 ALTER TABLE `courses_taking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `faculty`
--

DROP TABLE IF EXISTS `faculty`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `faculty` (
  `faculty_id` int(11) NOT NULL AUTO_INCREMENT,
  `account_id` int(11) NOT NULL,
  `level` int(11) NOT NULL DEFAULT '2',
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `rank` varchar(45) NOT NULL,
  PRIMARY KEY (`faculty_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `faculty`
--

LOCK TABLES `faculty` WRITE;
/*!40000 ALTER TABLE `faculty` DISABLE KEYS */;
INSERT INTO `faculty` VALUES (1,2,2,'Dan','Halvo','Professor');
/*!40000 ALTER TABLE `faculty` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `major`
--

DROP TABLE IF EXISTS `major`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `major` (
  `major_id` int(11) NOT NULL AUTO_INCREMENT,
  `major_name` varchar(45) NOT NULL,
  `credits_required` double NOT NULL,
  `min_gpa` double NOT NULL DEFAULT '2',
  PRIMARY KEY (`major_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `major`
--

LOCK TABLES `major` WRITE;
/*!40000 ALTER TABLE `major` DISABLE KEYS */;
INSERT INTO `major` VALUES (1,'Computer Science',65.5,2),(2,'Information Tech',69.5,2),(3,'Accounting AS',72.5,2);
/*!40000 ALTER TABLE `major` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `major_requirements`
--

LOCK TABLES `major_requirements` WRITE;
/*!40000 ALTER TABLE `major_requirements` DISABLE KEYS */;
INSERT INTO `major_requirements` VALUES (1,1,'Computer Science','CST',24,'Computer_Science'),(2,1,'Computer Science','ENG',6,'English               '),(3,1,'Computer Science','HUM',6,'Humanities'),(4,1,'Computer Science','SOS',6,'Social_Sciences'),(5,1,'Computer Science','SCI',8,'Science'),(6,1,'Computer Science','PED',2,'Physical_Education'),(7,1,'Computer Science','COL',1.5,'Freshman_Seminar'),(8,1,'Computer Science','MAT',8,'Mathematics'),(9,2,'Information Tech','CST',40,'Computer_Science'),(10,2,'Information Tech','ENG',6,'English                  '),(11,2,'Information Tech','HUM',3,'Humanities             '),(12,2,'Information Tech','SOS',6,'Social_Sciences     '),(13,2,'Information Tech','MAT',8,'Mathematics         '),(14,2,'Information Tech','BUS',6,'Bussiness             '),(15,2,'Information Tech','COL',1.5,'Freshman_Seminar'),(16,2,'Information Tech','PED',2,'Physical Education '),(17,3,'Accounting AS','BUS',37.5,'Account_business'),(19,3,'Accounting AS','HUM',12,'Humanities'),(20,3,'Accounting AS','SOS',9,'Social_Science'),(21,3,'Accounting AS','MAT',8,'Mathematics'),(22,3,'Accounting AS','SCI',4,'Science'),(23,3,'Accounting AS','PED',2,'Physical_Education');
/*!40000 ALTER TABLE `major_requirements` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `student` (
  `student_id` int(11) NOT NULL AUTO_INCREMENT,
  `account_id` int(11) NOT NULL,
  `level` int(11) NOT NULL DEFAULT '1',
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `gpa` double NOT NULL,
  `major` varchar(45) NOT NULL,
  `credits_taken` double NOT NULL,
  `campus` varchar(45) NOT NULL,
  PRIMARY KEY (`student_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES (1,1,1,'John','Doe',2.26,'Computer Science',57.5,'Ammerman'),(2,3,1,'Marry','Jane',3.18,'Accounting AS',9.5,'Ammerman');
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-05-10 18:39:42
