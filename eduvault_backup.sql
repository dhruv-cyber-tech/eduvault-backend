/*M!999999\- enable the sandbox mode */ 
-- MariaDB dump 10.19-12.3.2-MariaDB, for Linux (x86_64)
--
-- Host: localhost    Database: eduvault_db
-- ------------------------------------------------------
-- Server version	12.3.2-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*M!100616 SET @OLD_NOTE_VERBOSITY=@@NOTE_VERBOSITY, NOTE_VERBOSITY=0 */;

--
-- Sequence structure for `subjects_seq`
--

DROP SEQUENCE IF EXISTS `subjects_seq`;
CREATE SEQUENCE `subjects_seq` start with 1 minvalue 1 maxvalue 9223372036854775806 increment by 50 nocache nocycle ENGINE=InnoDB;
DO SETVAL(`subjects_seq`, 1, 0);

--
-- Table structure for table `admin_users`
--

DROP TABLE IF EXISTS `admin_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin_users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `password_hash` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKcp8822350s9vtyww7xdbgeuvu` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin_users`
--

SET @OLD_AUTOCOMMIT=@@AUTOCOMMIT, @@AUTOCOMMIT=0;
LOCK TABLES `admin_users` WRITE;
/*!40000 ALTER TABLE `admin_users` DISABLE KEYS */;
INSERT INTO `admin_users` VALUES
(1,'2026-06-19 18:43:15.000000','admin@eduvault.com','$2a$10$FdwFjuTrh0ftohq.gOB3o.faS/uoY9ATresxeehw8MLguB3QxuDkW','Admin');
/*!40000 ALTER TABLE `admin_users` ENABLE KEYS */;
UNLOCK TABLES;
COMMIT;
SET AUTOCOMMIT=@OLD_AUTOCOMMIT;

--
-- Table structure for table `chapters`
--

DROP TABLE IF EXISTS `chapters`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `chapters` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `chapter_number` int(11) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `subject_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3rm6snrkx0k8xyqn7017b0v41` (`subject_id`),
  CONSTRAINT `FK3rm6snrkx0k8xyqn7017b0v41` FOREIGN KEY (`subject_id`) REFERENCES `subjects` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chapters`
--

SET @OLD_AUTOCOMMIT=@@AUTOCOMMIT, @@AUTOCOMMIT=0;
LOCK TABLES `chapters` WRITE;
/*!40000 ALTER TABLE `chapters` DISABLE KEYS */;
INSERT INTO `chapters` VALUES
(1,1,'Anka Ganit',4),
(2,NULL,'Nature - God\'s Plan',23),
(4,NULL,'Vigyan ni adbhut duniya',30),
(5,NULL,'Animal - the living wonders',23),
(6,NULL,'A Beautiful Bond',23),
(7,NULL,'Sunita Avkashma',25),
(8,NULL,'गिनती',22),
(9,NULL,'સાપ અને મદારી',25),
(10,NULL,'શું તે સરખું દેખાય છે?',24),
(11,NULL,'ભાગ અને પૂર્ણ',24),
(12,NULL,'આકાર અને ખૂણાઓ',24),
(13,1,'The Wonderful World Of Science',67),
(14,2,'Diversity In The Living World ',67),
(15,3,'Mindful Eating',67),
(16,4,'Exploring Magnets',67),
(17,5,'Measurement of Lenght and Motion',67);
/*!40000 ALTER TABLE `chapters` ENABLE KEYS */;
UNLOCK TABLES;
COMMIT;
SET AUTOCOMMIT=@OLD_AUTOCOMMIT;

--
-- Table structure for table `resource_tag`
--

DROP TABLE IF EXISTS `resource_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `resource_tag` (
  `resource_id` bigint(20) NOT NULL,
  `tag_id` bigint(20) NOT NULL,
  PRIMARY KEY (`resource_id`,`tag_id`),
  KEY `FKg1fv6x44l033msnodphilvqa1` (`tag_id`),
  CONSTRAINT `FK8obk7u5frttbqb53rxk24u4k4` FOREIGN KEY (`resource_id`) REFERENCES `resources` (`id`),
  CONSTRAINT `FKg1fv6x44l033msnodphilvqa1` FOREIGN KEY (`tag_id`) REFERENCES `tags` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resource_tag`
--

SET @OLD_AUTOCOMMIT=@@AUTOCOMMIT, @@AUTOCOMMIT=0;
LOCK TABLES `resource_tag` WRITE;
/*!40000 ALTER TABLE `resource_tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `resource_tag` ENABLE KEYS */;
UNLOCK TABLES;
COMMIT;
SET AUTOCOMMIT=@OLD_AUTOCOMMIT;

--
-- Table structure for table `resources`
--

DROP TABLE IF EXISTS `resources`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `resources` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `file_name` varchar(255) NOT NULL,
  `file_path` varchar(255) NOT NULL,
  `file_size` bigint(20) DEFAULT NULL,
  `mime_type` varchar(255) DEFAULT NULL,
  `resource_type` enum('NOTES','SOLUTION','TEST_PAPER','WORKSHEET') NOT NULL,
  `title` varchar(255) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `chapter_id` bigint(20) DEFAULT NULL,
  `standard_id` bigint(20) NOT NULL,
  `subject_id` bigint(20) NOT NULL,
  `uploaded_by` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKbn1tde6423pmkgxh25aj03jx2` (`chapter_id`),
  KEY `FK8unybddvhpch63nwrdw32m4xc` (`standard_id`),
  KEY `FKs1xbb1cxb7bvipjvkam1gx8rc` (`subject_id`),
  KEY `FKi7x6tvhlhxyfvduvmbr58rmlc` (`uploaded_by`),
  CONSTRAINT `FK8unybddvhpch63nwrdw32m4xc` FOREIGN KEY (`standard_id`) REFERENCES `standards` (`id`),
  CONSTRAINT `FKbn1tde6423pmkgxh25aj03jx2` FOREIGN KEY (`chapter_id`) REFERENCES `chapters` (`id`),
  CONSTRAINT `FKi7x6tvhlhxyfvduvmbr58rmlc` FOREIGN KEY (`uploaded_by`) REFERENCES `admin_users` (`id`),
  CONSTRAINT `FKs1xbb1cxb7bvipjvkam1gx8rc` FOREIGN KEY (`subject_id`) REFERENCES `subjects` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resources`
--

SET @OLD_AUTOCOMMIT=@@AUTOCOMMIT, @@AUTOCOMMIT=0;
LOCK TABLES `resources` WRITE;
/*!40000 ALTER TABLE `resources` DISABLE KEYS */;
INSERT INTO `resources` VALUES
(4,'2026-06-22 17:24:14.087968','','ee5b6205-3143-4eac-a36b-8316083ebbc5.pdf','https://res.cloudinary.com/dpbs2obqm/raw/upload/v1782129253/eduvault/resources/c83e0c1a-1a79-4989-abea-b150a760860c.pdf',600374,'application/pdf','WORKSHEET','Practise Worksheet','2026-06-22 17:24:14.088009',2,31,23,1),
(5,'2026-06-22 17:47:17.012086','','Std_6_Science_Chapter_1_Worksheet(3).pdf','https://res.cloudinary.com/dpbs2obqm/raw/upload/v1782130636/eduvault/resources/8130622e-5e7a-4a10-8195-8f287eefa0a8.pdf',701292,'application/pdf','WORKSHEET','worksheet','2026-06-22 17:47:17.012109',4,32,30,1),
(6,'2026-06-22 18:28:51.757953','','New Doc 06-22-2026 18.25.pdf','https://res.cloudinary.com/dpbs2obqm/raw/upload/v1782133131/eduvault/resources/97e57df3-e77f-4a98-a9d8-d115e078bc01.pdf',772460,'application/pdf','WORKSHEET','Worksheet','2026-06-22 18:28:51.758009',5,31,23,1),
(7,'2026-06-23 08:42:31.364283','','New Doc 06-23-2026 08.30.pdf','https://res.cloudinary.com/dpbs2obqm/raw/upload/v1782184350/eduvault/resources/73e13e3c-846c-4621-b3fb-66784366e978.pdf',678912,'application/pdf','TEST_PAPER','First Sem','2026-06-23 08:42:31.364304',NULL,39,63,1),
(8,'2026-06-23 08:51:24.446673','','New Doc 06-23-2026 08.48.pdf','https://res.cloudinary.com/dpbs2obqm/raw/upload/v1782184883/eduvault/resources/13801e4a-c0ac-444e-8afc-2b1721d9248a.pdf',835679,'application/pdf','TEST_PAPER','first sem','2026-06-23 08:51:24.446699',NULL,31,25,1);
/*!40000 ALTER TABLE `resources` ENABLE KEYS */;
UNLOCK TABLES;
COMMIT;
SET AUTOCOMMIT=@OLD_AUTOCOMMIT;

--
-- Table structure for table `standards`
--

DROP TABLE IF EXISTS `standards`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `standards` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `sort_order` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKo5mwfscg9j38of8jt1mutdnjy` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `standards`
--

SET @OLD_AUTOCOMMIT=@@AUTOCOMMIT, @@AUTOCOMMIT=0;
LOCK TABLES `standards` WRITE;
/*!40000 ALTER TABLE `standards` DISABLE KEYS */;
INSERT INTO `standards` VALUES
(27,'GSEB Gujarati Std 1',1),
(28,'GSEB Gujarati Std 2',2),
(29,'GSEB Gujarati Std 3',3),
(30,'GSEB Gujarati Std 4',4),
(31,'GSEB Gujarati Std 5',5),
(32,'GSEB Gujarati Std 6',6),
(33,'GSEB Gujarati Std 7',7),
(34,'GSEB Gujarati Std 8',8),
(35,'GSEB English Std 1',9),
(36,'GSEB English Std 2',10),
(37,'GSEB English Std 3',11),
(38,'GSEB English Std 4',12),
(39,'GSEB English Std 5',13),
(40,'GSEB English Std 6',14),
(41,'GSEB English Std 7',15),
(42,'GSEB English Std 8',16),
(43,'CBSE Std 1',17),
(44,'CBSE Std 2',18),
(45,'CBSE Std 3',19),
(46,'CBSE Std 4',20),
(47,'CBSE Std 5',21),
(48,'CBSE Std 6',22),
(49,'CBSE Std 7',23),
(50,'CBSE Std 8',24);
/*!40000 ALTER TABLE `standards` ENABLE KEYS */;
UNLOCK TABLES;
COMMIT;
SET AUTOCOMMIT=@OLD_AUTOCOMMIT;

--
-- Table structure for table `subjects`
--

DROP TABLE IF EXISTS `subjects`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `subjects` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `standard_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5tk3ksto5e1r092eirwb46qlx` (`standard_id`),
  CONSTRAINT `FK5tk3ksto5e1r092eirwb46qlx` FOREIGN KEY (`standard_id`) REFERENCES `standards` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=116 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subjects`
--

SET @OLD_AUTOCOMMIT=@@AUTOCOMMIT, @@AUTOCOMMIT=0;
LOCK TABLES `subjects` WRITE;
/*!40000 ALTER TABLE `subjects` DISABLE KEYS */;
INSERT INTO `subjects` VALUES
(1,'Gujarati',27),
(2,'Hindi',27),
(3,'English',27),
(4,'Maths',27),
(5,'Paryavaran',27),
(6,'Gujarati',28),
(7,'Hindi',28),
(8,'English',28),
(9,'Maths',28),
(10,'Paryavaran',28),
(11,'Gujarati',29),
(12,'Hindi',29),
(13,'English',29),
(14,'Maths',29),
(15,'Paryavaran',29),
(16,'Gujarati',30),
(17,'Hindi',30),
(18,'English',30),
(19,'Maths',30),
(20,'Paryavaran',30),
(21,'Gujarati',31),
(22,'Hindi',31),
(23,'English',31),
(24,'Maths',31),
(25,'Paryavaran',31),
(26,'Gujarati',32),
(27,'Hindi',32),
(28,'English',32),
(29,'Maths',32),
(30,'Vigyan',32),
(31,'Samajik Vigyan',32),
(32,'Gujarati',33),
(33,'Hindi',33),
(34,'English',33),
(35,'Maths',33),
(36,'Vigyan',33),
(37,'Samajik Vigyan',33),
(38,'Gujarati',34),
(39,'Hindi',34),
(40,'English',34),
(41,'Maths',34),
(42,'Vigyan',34),
(43,'Samajik Vigyan',34),
(44,'Gujarati',35),
(45,'English',35),
(46,'Maths',35),
(47,'EVS',35),
(48,'Gujarati',36),
(49,'English',36),
(50,'Maths',36),
(51,'EVS',36),
(52,'Gujarati',37),
(53,'English',37),
(54,'Maths',37),
(55,'EVS',37),
(56,'Gujarati',38),
(57,'English',38),
(58,'Maths',38),
(59,'EVS',38),
(60,'Gujarati',39),
(61,'English',39),
(62,'Maths',39),
(63,'EVS',39),
(64,'Gujarati',40),
(65,'English',40),
(66,'Maths',40),
(67,'Science',40),
(68,'Social Science',40),
(69,'Gujarati',41),
(70,'English',41),
(71,'Maths',41),
(72,'Science',41),
(73,'Social Science',41),
(74,'Gujarati',42),
(75,'English',42),
(76,'Maths',42),
(77,'Science',42),
(78,'Social Science',42),
(79,'Hindi',43),
(80,'English',43),
(81,'Maths',43),
(82,'EVS',43),
(83,'Hindi',44),
(84,'English',44),
(85,'Maths',44),
(86,'EVS',44),
(87,'Hindi',45),
(88,'English',45),
(89,'Maths',45),
(90,'EVS',45),
(91,'Hindi',46),
(92,'English',46),
(93,'Maths',46),
(94,'EVS',46),
(95,'Hindi',47),
(96,'English',47),
(97,'Maths',47),
(98,'EVS',47),
(99,'Hindi',48),
(100,'English',48),
(101,'Maths',48),
(102,'Science',48),
(103,'Social Science',48),
(104,'Hindi',49),
(105,'English',49),
(106,'Maths',49),
(107,'Science',49),
(108,'Social Science',49),
(109,'Hindi',50),
(110,'English',50),
(111,'Maths',50),
(112,'Science',50),
(113,'Social Science',50),
(114,'Hindi',40),
(115,'Sanskrit',40);
/*!40000 ALTER TABLE `subjects` ENABLE KEYS */;
UNLOCK TABLES;
COMMIT;
SET AUTOCOMMIT=@OLD_AUTOCOMMIT;

--
-- Table structure for table `tags`
--

DROP TABLE IF EXISTS `tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `tags` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `slug` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKt48xdq560gs3gap9g7jg36kgc` (`name`),
  UNIQUE KEY `UKsn0d91hxu700qcw0n4pebp5vc` (`slug`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tags`
--

SET @OLD_AUTOCOMMIT=@@AUTOCOMMIT, @@AUTOCOMMIT=0;
LOCK TABLES `tags` WRITE;
/*!40000 ALTER TABLE `tags` DISABLE KEYS */;
/*!40000 ALTER TABLE `tags` ENABLE KEYS */;
UNLOCK TABLES;
COMMIT;
SET AUTOCOMMIT=@OLD_AUTOCOMMIT;

--
-- Dumping routines for database 'eduvault_db'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*M!100616 SET NOTE_VERBOSITY=@OLD_NOTE_VERBOSITY */;

-- Dump completed on 2026-06-24 12:30:13
