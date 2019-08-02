-- mysqldump-php https://github.com/ifsnop/mysqldump-php
--
-- Host: 127.0.0.1	Database: kenaikan_pangkat
-- ------------------------------------------------------
-- Server version 	5.5.5-10.3.13-MariaDB-2
-- Date: Fri, 02 Aug 2019 08:58:52 +0700

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
-- Table structure for table `usulan`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usulan` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_pegawai` int(11) NOT NULL,
  `tanggal` date NOT NULL,
  `nomor` varchar(25) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_pegawai` (`id_pegawai`),
  CONSTRAINT `usulan_ibfk_1` FOREIGN KEY (`id_pegawai`) REFERENCES `pegawai` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usulan`
--

LOCK TABLES `usulan` WRITE;
/*!40000 ALTER TABLE `usulan` DISABLE KEYS */;
SET autocommit=0;
/*!40000 ALTER TABLE `usulan` ENABLE KEYS */;
UNLOCK TABLES;
COMMIT;

-- Dumped table `usulan` with 0 row(s)
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
  `gaji_pokok_lama` int(11) NOT NULL,
  `gaji_pokok_baru` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_pegawai` (`id_pegawai`),
  CONSTRAINT `gaji_berkala_ibfk_1` FOREIGN KEY (`id_pegawai`) REFERENCES `pegawai` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gaji_berkala`
--

LOCK TABLES `gaji_berkala` WRITE;
/*!40000 ALTER TABLE `gaji_berkala` DISABLE KEYS */;
SET autocommit=0;
/*!40000 ALTER TABLE `gaji_berkala` ENABLE KEYS */;
UNLOCK TABLES;
COMMIT;

-- Dumped table `gaji_berkala` with 0 row(s)
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kenaikan_pangkat`
--

LOCK TABLES `kenaikan_pangkat` WRITE;
/*!40000 ALTER TABLE `kenaikan_pangkat` DISABLE KEYS */;
SET autocommit=0;
/*!40000 ALTER TABLE `kenaikan_pangkat` ENABLE KEYS */;
UNLOCK TABLES;
COMMIT;

-- Dumped table `kenaikan_pangkat` with 0 row(s)
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
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pegawai`
--

LOCK TABLES `pegawai` WRITE;
/*!40000 ALTER TABLE `pegawai` DISABLE KEYS */;
SET autocommit=0;
/*!40000 ALTER TABLE `pegawai` ENABLE KEYS */;
UNLOCK TABLES;
COMMIT;

-- Dumped table `pegawai` with 0 row(s)
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
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=latin1;
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

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on: Fri, 02 Aug 2019 08:58:52 +0700
