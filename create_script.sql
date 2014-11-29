CREATE DATABASE  IF NOT EXISTS `irem` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `irem`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win32 (x86)
--
-- Host: 127.0.0.1    Database: irem
-- ------------------------------------------------------
-- Server version	5.6.16

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
-- Table structure for table `irem_code_template`
--

DROP TABLE IF EXISTS `irem_code_template`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `irem_code_template` (
  `irem_code_template_id` int(6) NOT NULL,
  `description` varchar(100) NOT NULL,
  `code_text` varchar(1000) NOT NULL,
  PRIMARY KEY (`irem_code_template_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `irem_code_template`
--

LOCK TABLES `irem_code_template` WRITE;
/*!40000 ALTER TABLE `irem_code_template` DISABLE KEYS */;
INSERT INTO `irem_code_template` VALUES (1,'IF Template','if ( ) {\r\n\r\n}'),(2,'FOR Template','for( ; ; ){\r\n\r\n}'),(3,'WHILE Template','while( ){\r\n\r\n}'),(4,'Script Başarılı','return new ScriptResult(ScriptResultStatus.APPROVED, \"Script başarılı\");'),(5,'Script Başarısız','return new ScriptResult(ScriptResultStatus.DECLINED, \"Script başarısız\");');
/*!40000 ALTER TABLE `irem_code_template` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `irem_import_template`
--

DROP TABLE IF EXISTS `irem_import_template`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `irem_import_template` (
  `irem_import_template_id` int(11) NOT NULL,
  `description` varchar(100) NOT NULL,
  `class_name` varchar(100) NOT NULL,
  `package_path` varchar(255) DEFAULT NULL,
  `is_default` int(1) DEFAULT NULL,
  PRIMARY KEY (`irem_import_template_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `irem_import_template`
--

LOCK TABLES `irem_import_template` WRITE;
/*!40000 ALTER TABLE `irem_import_template` DISABLE KEYS */;
INSERT INTO `irem_import_template` VALUES (1,'Tüm scriptler en sonda bu class\'ı dönmek zorundadır','ScriptResult','com.ergo.insyst.irem.script',1),(2,'Tüm scriptlerde olmalıdır. Script\'in çalışma durumunu belirtir.','ScriptResultStatus','com.ergo.insyst.irem.script',1),(3,'String','String','java.lang',0);
/*!40000 ALTER TABLE `irem_import_template` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `irem_module`
--

DROP TABLE IF EXISTS `irem_module`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `irem_module` (
  `irem_module_id` int(11) NOT NULL,
  `description` varchar(100) NOT NULL,
  PRIMARY KEY (`irem_module_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `irem_module`
--

LOCK TABLES `irem_module` WRITE;
/*!40000 ALTER TABLE `irem_module` DISABLE KEYS */;
INSERT INTO `irem_module` VALUES (1,'deneme module'),(2,'deneme module 2');
/*!40000 ALTER TABLE `irem_module` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `irem_module_rule`
--

DROP TABLE IF EXISTS `irem_module_rule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `irem_module_rule` (
  `irem_module_rule_id` int(11) NOT NULL,
  `irem_module_id` int(11) NOT NULL,
  `irem_rule_id` smallint(6) NOT NULL,
  PRIMARY KEY (`irem_module_rule_id`),
  KEY `FK_irem_module_rule1` (`irem_module_id`),
  KEY `FK_irem_module_rule2` (`irem_rule_id`),
  CONSTRAINT `FK_irem_module_rule1` FOREIGN KEY (`irem_module_id`) REFERENCES `irem_module` (`irem_module_id`),
  CONSTRAINT `FK_irem_module_rule2` FOREIGN KEY (`irem_rule_id`) REFERENCES `irem_rule` (`irem_rule_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `irem_module_rule`
--

LOCK TABLES `irem_module_rule` WRITE;
/*!40000 ALTER TABLE `irem_module_rule` DISABLE KEYS */;
INSERT INTO `irem_module_rule` VALUES (1,2,3),(2,1,1),(3,1,2);
/*!40000 ALTER TABLE `irem_module_rule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `irem_module_rule_value`
--

DROP TABLE IF EXISTS `irem_module_rule_value`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `irem_module_rule_value` (
  `irem_module_rule_value_id` int(11) NOT NULL,
  `irem_module_rule_id` int(11) NOT NULL,
  `irem_rule_value_id` smallint(6) NOT NULL,
  `irem_script_id` smallint(6) NOT NULL,
  PRIMARY KEY (`irem_module_rule_value_id`),
  KEY `FK_irem_module_rule_value1` (`irem_module_rule_id`),
  KEY `FK_irem_module_rule_value2` (`irem_rule_value_id`),
  KEY `FK_irem_module_rule_value3` (`irem_script_id`),
  CONSTRAINT `FK_irem_module_rule_value1` FOREIGN KEY (`irem_module_rule_id`) REFERENCES `irem_module_rule` (`irem_module_rule_id`),
  CONSTRAINT `FK_irem_module_rule_value2` FOREIGN KEY (`irem_rule_value_id`) REFERENCES `irem_rule_value_seq` (`irem_rule_value_id`),
  CONSTRAINT `FK_irem_module_rule_value3` FOREIGN KEY (`irem_script_id`) REFERENCES `irem_script` (`irem_script_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `irem_module_rule_value`
--

LOCK TABLES `irem_module_rule_value` WRITE;
/*!40000 ALTER TABLE `irem_module_rule_value` DISABLE KEYS */;
/*!40000 ALTER TABLE `irem_module_rule_value` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `irem_property`
--

DROP TABLE IF EXISTS `irem_property`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `irem_property` (
  `irem_property_id` smallint(6) NOT NULL,
  `irem_property_code` varchar(50) NOT NULL,
  `description` varchar(100) NOT NULL,
  PRIMARY KEY (`irem_property_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `irem_property`
--

LOCK TABLES `irem_property` WRITE;
/*!40000 ALTER TABLE `irem_property` DISABLE KEYS */;
INSERT INTO `irem_property` VALUES (1,'code1','property1'),(2,'code2','property2');
/*!40000 ALTER TABLE `irem_property` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `irem_property_code`
--

DROP TABLE IF EXISTS `irem_property_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `irem_property_code` (
  `irem_property_code_id` int(11) NOT NULL,
  `code` varchar(50) NOT NULL,
  `description` varchar(50) NOT NULL,
  `irem_property_id` smallint(6) DEFAULT NULL,
  PRIMARY KEY (`irem_property_code_id`),
  KEY `FK_irem_property_code1` (`irem_property_id`),
  CONSTRAINT `FK_irem_property_code1` FOREIGN KEY (`irem_property_id`) REFERENCES `irem_property` (`irem_property_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `irem_property_code`
--

LOCK TABLES `irem_property_code` WRITE;
/*!40000 ALTER TABLE `irem_property_code` DISABLE KEYS */;
INSERT INTO `irem_property_code` VALUES (1,'markaKod','Marka Kodu',NULL),(2,'model','Model',NULL),(3,'tescilNo','Tescil Numarası',NULL),(4,'sasiNo','Şasi Numarası',NULL),(5,'ilKod','İl Kodu',NULL),(6,'plaka1','Plaka-1',NULL),(7,'plaka2','Plaka-2',NULL),(8,'renk','Renk',NULL),(9,'motorNo','Motor Numarası',NULL),(10,'koltukAdedi','Koltuk Adedi',NULL);
/*!40000 ALTER TABLE `irem_property_code` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `irem_property_value`
--

DROP TABLE IF EXISTS `irem_property_value`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `irem_property_value` (
  `irem_property_value_id` smallint(6) NOT NULL,
  `irem_property_id` smallint(6) NOT NULL,
  `val_string` varchar(250) NOT NULL,
  PRIMARY KEY (`irem_property_value_id`),
  KEY `FK_irem_property_value1` (`irem_property_id`),
  CONSTRAINT `FK_irem_property_value1` FOREIGN KEY (`irem_property_id`) REFERENCES `irem_property` (`irem_property_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `irem_property_value`
--

LOCK TABLES `irem_property_value` WRITE;
/*!40000 ALTER TABLE `irem_property_value` DISABLE KEYS */;
/*!40000 ALTER TABLE `irem_property_value` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `irem_rule`
--

DROP TABLE IF EXISTS `irem_rule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `irem_rule` (
  `irem_rule_id` smallint(6) NOT NULL,
  `description` varchar(100) NOT NULL,
  PRIMARY KEY (`irem_rule_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `irem_rule`
--

LOCK TABLES `irem_rule` WRITE;
/*!40000 ALTER TABLE `irem_rule` DISABLE KEYS */;
INSERT INTO `irem_rule` VALUES (1,'deneme1'),(2,'deneme2'),(3,'deneme3');
/*!40000 ALTER TABLE `irem_rule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `irem_rule_property`
--

DROP TABLE IF EXISTS `irem_rule_property`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `irem_rule_property` (
  `irem_rule_id` smallint(6) NOT NULL,
  `irem_property_id` smallint(6) NOT NULL,
  PRIMARY KEY (`irem_rule_id`,`irem_property_id`),
  KEY `FK_irem_rule_property2` (`irem_property_id`),
  CONSTRAINT `FK_irem_rule_property1` FOREIGN KEY (`irem_rule_id`) REFERENCES `irem_rule` (`irem_rule_id`),
  CONSTRAINT `FK_irem_rule_property2` FOREIGN KEY (`irem_property_id`) REFERENCES `irem_property` (`irem_property_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `irem_rule_property`
--

LOCK TABLES `irem_rule_property` WRITE;
/*!40000 ALTER TABLE `irem_rule_property` DISABLE KEYS */;
INSERT INTO `irem_rule_property` VALUES (1,1),(1,2),(3,2);
/*!40000 ALTER TABLE `irem_rule_property` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `irem_rule_value`
--

DROP TABLE IF EXISTS `irem_rule_value`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `irem_rule_value` (
  `irem_rule_value_id` smallint(6) NOT NULL,
  `irem_rule_id` smallint(6) NOT NULL,
  `irem_property_id` smallint(6) NOT NULL,
  `irem_property_value_id` smallint(6) NOT NULL,
  PRIMARY KEY (`irem_rule_value_id`,`irem_rule_id`,`irem_property_id`),
  KEY `FK_irem_rule_value2` (`irem_rule_id`,`irem_property_id`),
  KEY `FK_irem_rule_value3` (`irem_property_value_id`),
  CONSTRAINT `FK_irem_rule_value1` FOREIGN KEY (`irem_rule_value_id`) REFERENCES `irem_rule_value_seq` (`irem_rule_value_id`),
  CONSTRAINT `FK_irem_rule_value2` FOREIGN KEY (`irem_rule_id`, `irem_property_id`) REFERENCES `irem_rule_property` (`irem_rule_id`, `irem_property_id`),
  CONSTRAINT `FK_irem_rule_value3` FOREIGN KEY (`irem_property_value_id`) REFERENCES `irem_property_value` (`irem_property_value_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `irem_rule_value`
--

LOCK TABLES `irem_rule_value` WRITE;
/*!40000 ALTER TABLE `irem_rule_value` DISABLE KEYS */;
/*!40000 ALTER TABLE `irem_rule_value` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `irem_rule_value_seq`
--

DROP TABLE IF EXISTS `irem_rule_value_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `irem_rule_value_seq` (
  `irem_rule_value_id` smallint(6) NOT NULL,
  `irem_rule_id` smallint(6) NOT NULL,
  PRIMARY KEY (`irem_rule_value_id`),
  KEY `FK_irem_rule_value_seq1` (`irem_rule_id`),
  CONSTRAINT `FK_irem_rule_value_seq1` FOREIGN KEY (`irem_rule_id`) REFERENCES `irem_rule` (`irem_rule_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `irem_rule_value_seq`
--

LOCK TABLES `irem_rule_value_seq` WRITE;
/*!40000 ALTER TABLE `irem_rule_value_seq` DISABLE KEYS */;
/*!40000 ALTER TABLE `irem_rule_value_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `irem_script`
--

DROP TABLE IF EXISTS `irem_script`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `irem_script` (
  `irem_script_id` smallint(6) NOT NULL,
  `description` varchar(100) NOT NULL,
  `script_text` varchar(3000) NOT NULL,
  PRIMARY KEY (`irem_script_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `irem_script`
--

LOCK TABLES `irem_script` WRITE;
/*!40000 ALTER TABLE `irem_script` DISABLE KEYS */;
INSERT INTO `irem_script` VALUES (1,'deneme','\r\ndef cevap = 0;\r\n\r\ncevap = Integer.valueOf(ilKod) * 2;\r\n\r\nreturn new ScriptResult(ScriptResultStatus.APPROVED, cevap.toString());'),(2,'aaaaaa','\r\ndeneme'),(3,'falan filan','\r\ndeneme yan?lma'),(4,'xyz','\r\nasdasdasdas\r\nasd\r\nasd\r\nas\r\ndasd'),(5,'haydiiiiii','\r\nasdasdasdas\r\nasd\r\nasd\r\nas\r\ndasd'),(6,'bbbbbb',''),(8,'test','\r\naaa\r\nas\r\nda\r\nsd\r\nas\r\nda\r\nsd'),(9,'ooooooooo','\r\nasdasdasdasdasd'),(10,'i?te budur','\r\ndeneme son'),(11,'x1','\r\nasdasd'),(12,'adem deneme1','\r\nadem deneme1'),(13,'asdasd','\r\nasdasd'),(14,'asdasdasdd','\r\nasdsfdghfgh'),(15,'dfffff','\r\nasdasd'),(16,'aaaaa','\r\naaaaa'),(17,'asdasdads','\r\nasdasd'),(18,'yyyyy','\r\nzzzz'),(19,'zzzz','\r\nzzzz'),(20,'tttt','b1'),(21,'b1','\r\nb1'),(22,'b2','\r\nb2'),(23,'b3','\r\nb3'),(24,'b4','\r\nb4'),(25,'b5','\r\nb5'),(26,'b6','\r\nb6'),(27,'b7','b7');
/*!40000 ALTER TABLE `irem_script` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `irem_script_import`
--

DROP TABLE IF EXISTS `irem_script_import`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `irem_script_import` (
  `irem_script_id` smallint(6) NOT NULL,
  `irem_import_template_id` int(11) NOT NULL,
  PRIMARY KEY (`irem_script_id`,`irem_import_template_id`),
  KEY `FK_irem_script_import2` (`irem_import_template_id`),
  CONSTRAINT `FK_irem_script_import1` FOREIGN KEY (`irem_script_id`) REFERENCES `irem_script` (`irem_script_id`),
  CONSTRAINT `FK_irem_script_import2` FOREIGN KEY (`irem_import_template_id`) REFERENCES `irem_import_template` (`irem_import_template_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `irem_script_import`
--

LOCK TABLES `irem_script_import` WRITE;
/*!40000 ALTER TABLE `irem_script_import` DISABLE KEYS */;
INSERT INTO `irem_script_import` VALUES (12,3),(23,3),(24,3),(25,3),(26,3);
/*!40000 ALTER TABLE `irem_script_import` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sequences`
--

DROP TABLE IF EXISTS `sequences`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sequences` (
  `seq_name` varchar(50) NOT NULL,
  `seq_count` int(11) NOT NULL,
  PRIMARY KEY (`seq_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sequences`
--

LOCK TABLES `sequences` WRITE;
/*!40000 ALTER TABLE `sequences` DISABLE KEYS */;
INSERT INTO `sequences` VALUES ('SEQ_CODE_TEMPLATE',5),('SEQ_IMPORT_TEMPLATE',3),('SEQ_MODULE',2),('SEQ_MODULE_RULE',3),('SEQ_PROPERTY',2),('SEQ_PROPERTY_CODE',0),('SEQ_RULE',2),('SEQ_SCRIPT',27),('SEQ_SCRIPT_IMPORT',8);
/*!40000 ALTER TABLE `sequences` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-08-31 19:19:45
