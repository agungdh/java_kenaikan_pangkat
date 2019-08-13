-- mysqldump-php https://github.com/ifsnop/mysqldump-php
--
-- Host: 127.0.0.1	Database: kenaikan_pangkat
-- ------------------------------------------------------
-- Server version 	5.5.5-10.3.16-MariaDB
-- Date: Tue, 13 Aug 2019 06:16:09 +0200

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
-- Table structure for table `admin`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(191) NOT NULL,
  `password` varchar(191) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
SET autocommit=0;
INSERT INTO `admin` VALUES (9,'admin','21232f297a57a5a743894a0e4a801fc3');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;
COMMIT;

-- Dumped table `admin` with 1 row(s)
--

--
-- Table structure for table `gaji_berkala`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gaji_berkala` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tmt` date NOT NULL,
  `yad` date NOT NULL,
  `id_pegawai` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_pegawai` (`id_pegawai`),
  CONSTRAINT `gaji_berkala_ibfk_1` FOREIGN KEY (`id_pegawai`) REFERENCES `pegawai` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gaji_berkala`
--

LOCK TABLES `gaji_berkala` WRITE;
/*!40000 ALTER TABLE `gaji_berkala` DISABLE KEYS */;
SET autocommit=0;
INSERT INTO `gaji_berkala` VALUES (5,'2013-08-21','2017-08-21',29),(6,'2015-08-11','2019-08-11',28),(8,'2019-08-17','2023-08-17',28),(9,'2019-08-01','2023-08-01',28),(10,'2019-08-01','2023-08-01',31),(11,'2019-08-01','2023-08-01',29);
/*!40000 ALTER TABLE `gaji_berkala` ENABLE KEYS */;
UNLOCK TABLES;
COMMIT;

-- Dumped table `gaji_berkala` with 6 row(s)
--

--
-- Table structure for table `kenaikan_pangkat`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kenaikan_pangkat` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tmt` date NOT NULL,
  `yad` date NOT NULL,
  `id_pegawai` int(11) NOT NULL,
  `id_pangkat_lama` int(11) NOT NULL,
  `id_pangkat_baru` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_pegawai` (`id_pegawai`),
  KEY `id_pangkat_baru` (`id_pangkat_baru`),
  KEY `id_pangkat_lama` (`id_pangkat_lama`),
  CONSTRAINT `kenaikan_pangkat_ibfk_1` FOREIGN KEY (`id_pegawai`) REFERENCES `pegawai` (`id`),
  CONSTRAINT `kenaikan_pangkat_ibfk_2` FOREIGN KEY (`id_pangkat_baru`) REFERENCES `pangkatgol` (`id`),
  CONSTRAINT `kenaikan_pangkat_ibfk_3` FOREIGN KEY (`id_pangkat_lama`) REFERENCES `pangkatgol` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kenaikan_pangkat`
--

LOCK TABLES `kenaikan_pangkat` WRITE;
/*!40000 ALTER TABLE `kenaikan_pangkat` DISABLE KEYS */;
SET autocommit=0;
INSERT INTO `kenaikan_pangkat` VALUES (11,'2016-08-15','2018-08-15',29,9,9),(17,'2015-08-12','2017-08-12',28,9,9),(18,'2016-08-20','2018-08-20',28,9,9),(19,'2019-08-16','2021-08-16',28,9,9),(21,'2015-08-11','2017-08-11',28,9,9),(23,'2019-08-09','2021-08-09',31,13,12);
/*!40000 ALTER TABLE `kenaikan_pangkat` ENABLE KEYS */;
UNLOCK TABLES;
COMMIT;

-- Dumped table `kenaikan_pangkat` with 6 row(s)
--

--
-- Table structure for table `pangkatgol`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pangkatgol` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pangkatgol` varchar(191) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pangkatgol`
--

LOCK TABLES `pangkatgol` WRITE;
/*!40000 ALTER TABLE `pangkatgol` DISABLE KEYS */;
SET autocommit=0;
INSERT INTO `pangkatgol` VALUES (9,'IV/e Pembina Utama'),(10,'IV/d Pembina UtamaMadya'),(11,'IV/c Pembina Utama Muda'),(12,'IV/b Pembina Tingkat I'),(13,'IV/a Pembina'),(14,'III/d Penata Tingkat I'),(15,'III/c Penata'),(16,'III/b Penata Muda Tingkat I'),(17,'III/a Penata Muda'),(18,'II/d Pengatur Tingkat I'),(19,'II/c Pengatur'),(20,'II/b Pengatur Muda Tingkat I'),(21,'II/a Pengatur Muda'),(22,'I/d Juru Tingkat I'),(23,'I/c Juru'),(24,'I/b Juru Muda Tingkat I'),(25,'I/a Juru Muda');
/*!40000 ALTER TABLE `pangkatgol` ENABLE KEYS */;
UNLOCK TABLES;
COMMIT;

-- Dumped table `pangkatgol` with 17 row(s)
--

--
-- Table structure for table `pegawai`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pegawai` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nip` varchar(191) NOT NULL,
  `nama` varchar(191) NOT NULL,
  `id_pangkatgol` int(11) NOT NULL,
  `tmt_gaji` date NOT NULL,
  `yad_gaji` date NOT NULL,
  `tmt_pangkat` date NOT NULL,
  `yad_pangkat` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_pangkatgol` (`id_pangkatgol`),
  CONSTRAINT `pegawai_ibfk_1` FOREIGN KEY (`id_pangkatgol`) REFERENCES `pangkatgol` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pegawai`
--

LOCK TABLES `pegawai` WRITE;
/*!40000 ALTER TABLE `pegawai` DISABLE KEYS */;
SET autocommit=0;
INSERT INTO `pegawai` VALUES (28,'dsf 2','fsdaf 3',9,'2019-08-01','2023-08-01','2018-08-17','2020-08-17'),(29,'asdfasf asdf','asdfadfas df',14,'2019-08-01','2023-08-01','2019-08-09','2021-08-09'),(31,'werw','sfadsf',9,'2019-08-01','2023-08-01','2019-08-02','2021-08-02');
/*!40000 ALTER TABLE `pegawai` ENABLE KEYS */;
UNLOCK TABLES;
COMMIT;

-- Dumped table `pegawai` with 3 row(s)
--

--
-- Table structure for table `usulan`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usulan` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_pegawai` int(11) NOT NULL,
  `tipe` enum('gaji','pangkat') NOT NULL,
  `tanggal` date NOT NULL,
  `nomor` varchar(25) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_pegawai` (`id_pegawai`),
  CONSTRAINT `usulan_ibfk_1` FOREIGN KEY (`id_pegawai`) REFERENCES `pegawai` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usulan`
--

LOCK TABLES `usulan` WRITE;
/*!40000 ALTER TABLE `usulan` DISABLE KEYS */;
SET autocommit=0;
INSERT INTO `usulan` VALUES (4,28,'gaji','2019-08-10','fghjfgj'),(5,29,'gaji','2019-08-09','1241241'),(7,31,'gaji','2019-08-16','pangkat'),(10,28,'pangkat','2019-08-03','qwrqw qwrqwr');
/*!40000 ALTER TABLE `usulan` ENABLE KEYS */;
UNLOCK TABLES;
COMMIT;

-- Dumped table `usulan` with 4 row(s)
--

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on: Tue, 13 Aug 2019 06:16:09 +0200
