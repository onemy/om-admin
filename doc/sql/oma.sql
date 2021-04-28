-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: oma
-- ------------------------------------------------------
-- Server version	5.7.32-log

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
-- Table structure for table `sys_acl`
--

DROP TABLE IF EXISTS `sys_acl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_acl` (
  `id` varchar(32) NOT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `creator` varchar(255) DEFAULT NULL,
  `del` varchar(255) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `order_no` int(11) NOT NULL,
  `update_date` datetime(6) DEFAULT NULL,
  `updater` varchar(255) DEFAULT NULL,
  `principal_type` varchar(255) DEFAULT NULL,
  `principal_id` varchar(255) DEFAULT NULL,
  `resource_type` varchar(255) DEFAULT NULL,
  `resource_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_acl`
--

LOCK TABLES `sys_acl` WRITE;
/*!40000 ALTER TABLE `sys_acl` DISABLE KEYS */;
INSERT INTO `sys_acl` VALUES ('402881847917cea4017917d4388a0001','2021-04-28 17:34:14.659000','admin',NULL,NULL,0,'2021-04-28 17:34:14.659000',NULL,'U','402881847917cea4017917d3ee000000','M','1'),('402881847917cea4017917d438950002','2021-04-28 17:34:14.676000','admin',NULL,NULL,0,'2021-04-28 17:34:14.676000',NULL,'U','402881847917cea4017917d3ee000000','M','2'),('402881847917cea4017917d4389d0003','2021-04-28 17:34:14.684000','admin',NULL,NULL,0,'2021-04-28 17:34:14.684000',NULL,'U','402881847917cea4017917d3ee000000','M','3'),('402881847917cea4017917d438a50004','2021-04-28 17:34:14.693000','admin',NULL,NULL,0,'2021-04-28 17:34:14.693000',NULL,'U','402881847917cea4017917d3ee000000','M','5'),('402881847917cea4017917d438ad0005','2021-04-28 17:34:14.701000','admin',NULL,NULL,0,'2021-04-28 17:34:14.701000',NULL,'U','402881847917cea4017917d3ee000000','P','1'),('402881847917cea4017917d438b30006','2021-04-28 17:34:14.707000','admin',NULL,NULL,0,'2021-04-28 17:34:14.707000',NULL,'U','402881847917cea4017917d3ee000000','P','2'),('402881847917cea4017917d438ba0007','2021-04-28 17:34:14.714000','admin',NULL,NULL,0,'2021-04-28 17:34:14.714000',NULL,'U','402881847917cea4017917d3ee000000','P','3');
/*!40000 ALTER TABLE `sys_acl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_group`
--

DROP TABLE IF EXISTS `sys_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_group` (
  `id` varchar(32) NOT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `creator` varchar(255) DEFAULT NULL,
  `del` varchar(255) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `order_no` int(11) NOT NULL,
  `update_date` datetime(6) DEFAULT NULL,
  `updater` varchar(255) DEFAULT NULL,
  `group_code` varchar(255) DEFAULT NULL,
  `group_name` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `parent_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4miqlksu13h7y9nos119amknu` (`parent_id`),
  CONSTRAINT `FK4miqlksu13h7y9nos119amknu` FOREIGN KEY (`parent_id`) REFERENCES `sys_group` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_group`
--

LOCK TABLES `sys_group` WRITE;
/*!40000 ALTER TABLE `sys_group` DISABLE KEYS */;
INSERT INTO `sys_group` VALUES ('1',NULL,NULL,NULL,NULL,1,NULL,NULL,'root','集团总部','0',NULL),('2',NULL,NULL,NULL,NULL,2,NULL,NULL,'all','总务部','0','1'),('3',NULL,NULL,NULL,NULL,3,NULL,NULL,'hr','人力部','0','1'),('4',NULL,NULL,NULL,NULL,4,NULL,NULL,'fico','财务部','0','1'),('5','2021-04-26 14:52:11.816000','admin',NULL,'test',5,'2021-04-26 14:52:11.816000',NULL,'tech','技术部','1','1'),('6',NULL,NULL,NULL,NULL,6,NULL,NULL,'sh','上海分公司','0',NULL),('7',NULL,NULL,NULL,NULL,7,NULL,NULL,'market','市场部','0','6');
/*!40000 ALTER TABLE `sys_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_menu`
--

DROP TABLE IF EXISTS `sys_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_menu` (
  `id` varchar(32) NOT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `creator` varchar(255) DEFAULT NULL,
  `del` varchar(255) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `order_no` int(11) NOT NULL,
  `update_date` datetime(6) DEFAULT NULL,
  `updater` varchar(255) DEFAULT NULL,
  `code` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `url` varchar(255) DEFAULT NULL,
  `component` varchar(255) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `icon_svg` varchar(255) DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `target` varchar(255) DEFAULT NULL,
  `module_id` varchar(32) DEFAULT NULL,
  `parent_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_tq5314uwm3dsbq5mjd8mwdg2f` (`code`),
  KEY `FK4xbdv7cd5uupcix43h4n85y5g` (`module_id`),
  KEY `FK2jrf4gb0gjqi8882gxytpxnhe` (`parent_id`),
  CONSTRAINT `FK2jrf4gb0gjqi8882gxytpxnhe` FOREIGN KEY (`parent_id`) REFERENCES `sys_menu` (`id`),
  CONSTRAINT `FK4xbdv7cd5uupcix43h4n85y5g` FOREIGN KEY (`module_id`) REFERENCES `sys_module` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_menu`
--

LOCK TABLES `sys_menu` WRITE;
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;
INSERT INTO `sys_menu` VALUES ('1',NULL,NULL,NULL,NULL,1,NULL,NULL,'sys','系统管理','#','1','far fa-circle','','1','0','1',NULL,NULL),('2',NULL,NULL,NULL,NULL,2,NULL,NULL,'user','用户管理','pages/user/index.html','1','fas fa-user','','1','0','1',NULL,'1'),('3',NULL,NULL,NULL,NULL,3,NULL,NULL,'role','角色管理','pages/role/index.html','1','fas fa-user-tag','','1','0','1',NULL,'1'),('4',NULL,NULL,NULL,NULL,4,NULL,NULL,'menu','菜单管理','pages/menu/index.html','1','fas fa-tasks','','1','0','1',NULL,'1'),('5',NULL,NULL,NULL,NULL,5,NULL,NULL,'group','组织管理','pages/group/index.html','1','fas fa-users','','1','0','1',NULL,'1');
/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_module`
--

DROP TABLE IF EXISTS `sys_module`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_module` (
  `id` varchar(32) NOT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `creator` varchar(255) DEFAULT NULL,
  `del` varchar(255) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `order_no` int(11) NOT NULL,
  `update_date` datetime(6) DEFAULT NULL,
  `updater` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_module`
--

LOCK TABLES `sys_module` WRITE;
/*!40000 ALTER TABLE `sys_module` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_module` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_permission`
--

DROP TABLE IF EXISTS `sys_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_permission` (
  `id` varchar(32) NOT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `creator` varchar(255) DEFAULT NULL,
  `del` varchar(255) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `order_no` int(11) NOT NULL,
  `update_date` datetime(6) DEFAULT NULL,
  `updater` varchar(255) DEFAULT NULL,
  `code` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `url` varchar(255) DEFAULT NULL,
  `menu_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_2vm98en2ouht0v15fvef2whp4` (`code`),
  KEY `FKdh0pa1l2c4t5xnewwsev2yyx2` (`menu_id`),
  CONSTRAINT `FKdh0pa1l2c4t5xnewwsev2yyx2` FOREIGN KEY (`menu_id`) REFERENCES `sys_menu` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_permission`
--

LOCK TABLES `sys_permission` WRITE;
/*!40000 ALTER TABLE `sys_permission` DISABLE KEYS */;
INSERT INTO `sys_permission` VALUES ('1',NULL,NULL,NULL,NULL,1,NULL,NULL,'userList','用户查询','user/list','2'),('2',NULL,NULL,NULL,NULL,2,NULL,NULL,'userSave','用户修改','user/save','2'),('3',NULL,NULL,NULL,NULL,3,NULL,NULL,'userDelete','用户删除','user/delete','2');
/*!40000 ALTER TABLE `sys_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role` (
  `id` varchar(32) NOT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `creator` varchar(255) DEFAULT NULL,
  `del` varchar(255) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `order_no` int(11) NOT NULL,
  `update_date` datetime(6) DEFAULT NULL,
  `updater` varchar(255) DEFAULT NULL,
  `role_code` varchar(255) DEFAULT NULL,
  `role_name` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user` (
  `id` varchar(32) NOT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `creator` varchar(255) DEFAULT NULL,
  `del` varchar(255) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `order_no` int(11) NOT NULL,
  `update_date` datetime(6) DEFAULT NULL,
  `updater` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `end_date` datetime(6) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `pic` longtext,
  `sex` varchar(255) DEFAULT NULL,
  `start_date` datetime(6) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `username` varchar(255) NOT NULL,
  `group_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_51bvuyvihefoh4kp5syh2jpi4` (`username`),
  KEY `FKl7i4833vinxhaeho5oynrsjtk` (`group_id`),
  CONSTRAINT `FKl7i4833vinxhaeho5oynrsjtk` FOREIGN KEY (`group_id`) REFERENCES `sys_group` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES ('1','2021-04-11 17:21:18.392000','admin',NULL,'test',1,'2021-04-12 19:23:04.745000','admin','onemysoft@163.com',NULL,'13912345678','管理员','$2a$10$YnQh6XDwpXzd4kilw8aIleR61GY6kCVDcF04ac5EPs3LRBkJGYyZK',NULL,'1',NULL,'0',NULL,'admin',NULL),('402881847917cea4017917d3ee000000','2021-04-28 17:33:55.562000','admin',NULL,'',2,'2021-04-28 17:33:55.562000',NULL,'',NULL,'','test','$2a$10$B5Tkotkqysx3n8gQRLs.TOdCeTFBS3YvB9A40xDMbjmB..nvoMOpG',NULL,'1',NULL,'0',NULL,'guest','2');
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_roles`
--

DROP TABLE IF EXISTS `sys_user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user_roles` (
  `users_id` varchar(32) NOT NULL,
  `roles_id` varchar(32) NOT NULL,
  PRIMARY KEY (`users_id`,`roles_id`),
  KEY `FKdpvc6d7xqpqr43dfuk1s27cqh` (`roles_id`),
  CONSTRAINT `FKdpvc6d7xqpqr43dfuk1s27cqh` FOREIGN KEY (`roles_id`) REFERENCES `sys_role` (`id`),
  CONSTRAINT `FKl5a53kgwyql3twmb6wkyf1x7c` FOREIGN KEY (`users_id`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_roles`
--

LOCK TABLES `sys_user_roles` WRITE;
/*!40000 ALTER TABLE `sys_user_roles` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_user_roles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-04-28 17:37:55
