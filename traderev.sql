-- MySQL dump 10.16  Distrib 10.2.12-MariaDB, for osx10.12 (x86_64)
--
-- Host: localhost    Database: traderev
-- ------------------------------------------------------
-- Server version	10.2.12-MariaDB

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
-- Current Database: `traderev`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `traderev` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `traderev`;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_ts` timestamp DEFAULT CURRENT_TIMESTAMP,
  `updated_ts` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `account_status` int(11) DEFAULT NULL,
  `failed_attempts` int(11) DEFAULT NULL,
  `last_login_attempt_ts` datetime DEFAULT NULL,
  `last_successful_login` datetime DEFAULT NULL,
  `member_type` int(11) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `role` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `UKUserUsername` UNIQUE (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'2019-12-11 17:01:46','2019-12-11 17:01:46',1,0,NULL,NULL,1,'thor','thor',100),(2,'2019-12-11 17:01:46','2019-12-11 17:01:46',1,0,NULL,NULL,1,'loki','loki',100),(3,'2019-12-11 17:02:05','2019-12-11 17:02:05',1,0,NULL,NULL,1,'odin','odin',1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_info`
--

DROP TABLE IF EXISTS `user_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_ts` timestamp DEFAULT CURRENT_TIMESTAMP,
  `updated_ts` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `address` varchar(255) DEFAULT NULL,
  `alternate_address` varchar(255) DEFAULT NULL,
  `alternate_email_addess` varchar(255) DEFAULT NULL,
  `alternate_phone_num` varchar(255) DEFAULT NULL,
  `drivers_license_image_info` varchar(255) DEFAULT NULL,
  `drivers_license_number` varchar(255) DEFAULT NULL,
  `email_addess` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `middle_name` varchar(255) DEFAULT NULL,
  `phone_num` varchar(255) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `primary_zipcode` varchar(255) DEFAULT NULL,
  `primary_address` varchar(255) DEFAULT NULL,
  `alternate_zipcode` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKUserInfoUser` (`user_id`),
  CONSTRAINT `FKUserInfoUser` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_info`
--

LOCK TABLES `user_info` WRITE;
/*!40000 ALTER TABLE `user_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `car_info`
--

DROP TABLE IF EXISTS `car_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `car_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_ts` timestamp DEFAULT CURRENT_TIMESTAMP,
  `updated_ts` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `manufacturer` varchar(255) DEFAULT NULL,
  `model` varchar(255) DEFAULT NULL,
  `year` varchar(255) DEFAULT NULL,
  `owner` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKCarInfoOwner` (`owner`),
  CONSTRAINT `FKCarInfoOwner` FOREIGN KEY (`owner`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `car_info`
--

LOCK TABLES `car_info` WRITE;
/*!40000 ALTER TABLE `car_info` DISABLE KEYS */;
INSERT INTO `car_info` VALUES (1,'2019-12-11 17:05:06','2019-12-11 17:05:06','Ford','Mustang','2012',1),(2,'2019-12-11 17:05:10','2019-12-11 17:05:10','Toyota','Corolla','2013',2);
/*!40000 ALTER TABLE `car_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `car_auction`
--

DROP TABLE IF EXISTS `car_auction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `car_auction` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_ts` timestamp DEFAULT CURRENT_TIMESTAMP,
  `updated_ts` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `auction_date` datetime DEFAULT NULL,
  `opening_bid` double DEFAULT NULL,
  `selling_price` double DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `buyer_id` int(11) DEFAULT NULL,
  `car_info_id` int(11) DEFAULT NULL,
  `seller_id` int(11) DEFAULT NULL,
  `selling_bid_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKCarAuctionBuyer` (`buyer_id`),
  KEY `FKCarAuctionCarInfo` (`car_info_id`),
  KEY `FKCarAuctionSeller` (`seller_id`),
  KEY `FKCarAuctionSellingBid` (`selling_bid_id`),
  CONSTRAINT `FKCarAuctionCarInfo` FOREIGN KEY (`car_info_id`) REFERENCES `car_info` (`id`),
  CONSTRAINT `FKCarAuctionSellingBid` FOREIGN KEY (`selling_bid_id`) REFERENCES `car_bid` (`id`),
  CONSTRAINT `FKCarAuctionSeller` FOREIGN KEY (`seller_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKCarAuctionBuyer` FOREIGN KEY (`buyer_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `car_auction`
--

LOCK TABLES `car_auction` WRITE;
/*!40000 ALTER TABLE `car_auction` DISABLE KEYS */;
/*!40000 ALTER TABLE `car_auction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `car_bid`
--

DROP TABLE IF EXISTS `car_bid`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `car_bid` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_ts` timestamp DEFAULT CURRENT_TIMESTAMP,
  `updated_ts` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `bid_amount` double DEFAULT NULL,
  `time_of_bid` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `bid_by` int(11) DEFAULT NULL,
  `auction_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKCarBidBidBy` (`bid_by`),
  KEY `FKCarBidAuction` (`auction_id`),
  CONSTRAINT `UKCarBidBidAmount` UNIQUE (`auction_id`, `bid_amount`),
  CONSTRAINT `FKCarBidBidBy` FOREIGN KEY (`bid_by`) REFERENCES `user` (`id`),
  CONSTRAINT `FKCarBidAuction` FOREIGN KEY (`auction_id`) REFERENCES `car_auction` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `car_bid`
--

LOCK TABLES `car_bid` WRITE;
/*!40000 ALTER TABLE `car_bid` DISABLE KEYS */;
/*!40000 ALTER TABLE `car_bid` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-12-11 21:18:17
