-- MySQL dump 10.13  Distrib 5.7.9, for osx10.9 (x86_64)
--
-- Host: localhost    Database: RefugiApp
-- ------------------------------------------------------
-- Server version	5.7.9

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
-- Table structure for table `Content`
--

DROP TABLE IF EXISTS `Content`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Content` (
  `idcontent` int(11) NOT NULL AUTO_INCREMENT,
  `type` int(11) NOT NULL,
  `audio` varchar(200) DEFAULT NULL,
  `fraseEs` varchar(200) CHARACTER SET utf8 NOT NULL,
  `fraseAr` varchar(200) CHARACTER SET utf8 DEFAULT NULL,
  `users_id` int(11) NOT NULL,
  PRIMARY KEY (`idcontent`,`users_id`),
  UNIQUE KEY `idenviroments_UNIQUE` (`idcontent`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Content`
--

LOCK TABLES `Content` WRITE;
/*!40000 ALTER TABLE `Content` DISABLE KEYS */;
/*!40000 ALTER TABLE `Content` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Phrase`
--

DROP TABLE IF EXISTS `Phrase`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Phrase` (
  `idPhrase` int(11) NOT NULL AUTO_INCREMENT,
  `type` int(11) NOT NULL,
  `phrase_es` varchar(120) CHARACTER SET utf8 NOT NULL,
  `phrase_ar` varchar(120) CHARACTER SET utf8 NOT NULL,
  `audioFrase` varchar(200) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`idPhrase`),
  UNIQUE KEY `idPhrase_UNIQUE` (`idPhrase`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Phrase`
--

LOCK TABLES `Phrase` WRITE;
/*!40000 ALTER TABLE `Phrase` DISABLE KEYS */;
INSERT INTO `Phrase` VALUES (1,1,'Buenos dias','آلشرطة','buenos_dias.mp3'),(2,1,'Buenas tardes','صباح آلخير','buenas_tardes.mp3'),(3,1,'Buenas noches','أهلا','buenas_noches.mp3'),(4,1,'Necesito ayuda','آلشرطة','ayuda.mp3'),(5,1,'No se español','مساء الخير','esp.mp3'),(6,1,'Me han robado','أنا بحاجة للمساعدة','robado.mp3'),(7,1,'Me he perdido','لا أتكلم الاسبانية','perdido.mp3'),(8,1,'Muchas gracias','لقد سرقوني','gracias.mp3'),(9,1,'Adios','وداعا','adios.mp3'),(10,2,'Buenos dias','آلشرطة','buenos_dias.mp3'),(11,2,'Buenas tardes','آلشرطة','buenas_tardes.mp3'),(12,2,'Soy refugiado','آلشرطة','refugiado.mp3'),(13,2,'Necesito documentacion','آلشرطة','documentacion.mp3'),(14,2,'No se español','لا أتكلم الإسبانية','esp.mp3'),(15,2,'Gracias','شكرا','gracias.mp3'),(16,2,'Adios','وداعا','adios.mp3'),(17,3,'Cobrame','تخلص','cobrame.mp3'),(18,3,'¿Cuanto es?','كم ثمنه¿?','cuanto_es.mp3'),(19,3,'Una bolsa por favor','كيس من فضلك','bolsa.mp3'),(20,3,'Gracias','شكرا','gracias.mp3'),(21,4,'Hola ','آلمخبزة','hola.mp3'),(22,4,'Quiero una barra de pan, por favor','أريد الخبز من فضلك','barra.mp3'),(23,4,'Quiero eso','أريد ذاك','eso.mp3'),(24,4,'¿Cuanto es?','كما ثمنها؟','cuanto_es.mp3'),(25,4,'Gracias','شكرا','gracias.mp3'),(26,4,'Adios','وداعا','adios.mp3'),(27,5,'Hola','آلمخبزة','hola.mp3'),(28,5,'Quiero un billete','أريد تذكرة','billete.mp3'),(29,5,'Quiero ir a...','أريد أن أذهب إلى...','ir_a.mp3'),(30,5,'¿Cuanto cuesta?','كما ثمنها؟','cuanto_cuesta.mp3'),(31,5,'Hasta luego','وداعا','hasta_luego.mp3'),(32,6,'Hola','آلمخبزة','hola.mp3'),(33,6,'Necesito una medicina','انا بحاجة الى هذا الدواء','medicina.mp3'),(34,6,'Me han dado esta receta en el médico','لقد أعطاني هذه وصفة الطبيب','receta.mp3'),(35,6,'¿Cuanto es?','كما ثمنها؟','cuanto_es.mp3'),(36,6,'Gracias','شكرا','gracias.mp3'),(37,7,'Hola','آلمخبزة','hola.mp3'),(38,7,'No me encuentro bien','لا أشعر جيدا','no_me_encuentro_bien.mp3'),(39,7,'Me duele...','انه لامر مؤلم','me_duele.mp3'),(40,7,'Tengo fiebre','لدي الحمى','fiebre.mp3'),(41,7,'¿Que tengo que tomar?','¿ لا بد لي من اتخاذ?','que_tomar.mp3'),(42,7,'Gracias doctor','أشكر الدكتور','gracias_doctor.mp3');
/*!40000 ALTER TABLE `Phrase` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Users`
--

DROP TABLE IF EXISTS `Users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) CHARACTER SET utf8 NOT NULL,
  `password` varchar(45) CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Users`
--

LOCK TABLES `Users` WRITE;
/*!40000 ALTER TABLE `Users` DISABLE KEYS */;
INSERT INTO `Users` VALUES (16,'Julen','Velar'),(17,'a','a');
/*!40000 ALTER TABLE `Users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-01-25 18:40:00
