-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: pablocadista.hopto.org    Database: surfschools
-- ------------------------------------------------------
-- Server version	5.5.54-0+deb8u1

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
-- Table structure for table `actividad`
--

DROP TABLE IF EXISTS `actividad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `actividad` (
  `idactividad` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `descripcion` varchar(255) NOT NULL,
  `escuela_idescuela` int(11) NOT NULL,
  `tipo` varchar(15) NOT NULL,
  `activa` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`idactividad`),
  KEY `fk_actividad_escuela1_idx` (`escuela_idescuela`),
  CONSTRAINT `fk_actividad_escuela1` FOREIGN KEY (`escuela_idescuela`) REFERENCES `escuela` (`idescuela`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `actividad_caracteristicas_relacion`
--

DROP TABLE IF EXISTS `actividad_caracteristicas_relacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `actividad_caracteristicas_relacion` (
  `actividad_idactividad` int(11) NOT NULL,
  `caracteristicas_actividad_idcaracteristicas_actividad` int(11) NOT NULL,
  `valor` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`actividad_idactividad`,`caracteristicas_actividad_idcaracteristicas_actividad`),
  KEY `fk_actividad_has_caracteristicas_actividad_caracteristicas__idx` (`caracteristicas_actividad_idcaracteristicas_actividad`),
  KEY `fk_actividad_has_caracteristicas_actividad_actividad_idx` (`actividad_idactividad`),
  CONSTRAINT `fk_actividad_has_caracteristicas_actividad_actividad` FOREIGN KEY (`actividad_idactividad`) REFERENCES `actividad` (`idactividad`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_actividad_has_caracteristicas_actividad_caracteristicas_ac1` FOREIGN KEY (`caracteristicas_actividad_idcaracteristicas_actividad`) REFERENCES `caracteristicas_actividad` (`idcaracteristicas_actividad`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `actividad_programada`
--

DROP TABLE IF EXISTS `actividad_programada`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `actividad_programada` (
  `idactividad_programada` int(11) NOT NULL AUTO_INCREMENT,
  `fecha` varchar(10) DEFAULT NULL,
  `participantes_max` int(11) DEFAULT NULL,
  `actividad_idactividad` int(11) NOT NULL,
  `activa` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`idactividad_programada`),
  KEY `fk_actividad_programada_actividad1_idx` (`actividad_idactividad`),
  CONSTRAINT `fk_actividad_programada_actividad1` FOREIGN KEY (`actividad_idactividad`) REFERENCES `actividad` (`idactividad`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `alquiler`
--

DROP TABLE IF EXISTS `alquiler`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `alquiler` (
  `idalquiler` int(11) NOT NULL AUTO_INCREMENT,
  `cantidad` int(11) DEFAULT NULL,
  `fecha` varchar(10) DEFAULT NULL,
  `factura` varchar(45) DEFAULT NULL,
  `usuario_idusuario` int(11) NOT NULL,
  `material_idmaterial` int(11) NOT NULL,
  `tipo` varchar(10) NOT NULL,
  `hora` varchar(10) DEFAULT NULL,
  `aprobado` tinyint(4) DEFAULT NULL,
  `cancelado` tinyint(4) DEFAULT NULL,
  `visto` tinyint(1) DEFAULT NULL,
  `realizado` tinyint(1) DEFAULT NULL,
  `descripcion` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`idalquiler`),
  KEY `fk_alquiler_usuario1_idx` (`usuario_idusuario`),
  KEY `fk_alquiler_material1_idx` (`material_idmaterial`),
  CONSTRAINT `fk_alquiler_material1` FOREIGN KEY (`material_idmaterial`) REFERENCES `material` (`idmaterial`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_alquiler_usuario1` FOREIGN KEY (`usuario_idusuario`) REFERENCES `usuario` (`idusuario`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `api`
--

DROP TABLE IF EXISTS `api`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `api` (
  `ciudad` varchar(255) NOT NULL,
  `codigo` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `caracteristicas_actividad`
--

DROP TABLE IF EXISTS `caracteristicas_actividad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `caracteristicas_actividad` (
  `idcaracteristicas_actividad` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`idcaracteristicas_actividad`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `caracteristicas_material`
--

DROP TABLE IF EXISTS `caracteristicas_material`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `caracteristicas_material` (
  `idcaracteristicas_material` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`idcaracteristicas_material`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `escuela`
--

DROP TABLE IF EXISTS `escuela`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `escuela` (
  `idescuela` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `gerente` varchar(45) NOT NULL,
  `latitud` varchar(45) NOT NULL,
  `longitud` varchar(45) NOT NULL,
  `direccion` varchar(80) NOT NULL,
  `descripcion` varchar(250) NOT NULL,
  `posibilidadsurfear` tinyint(1) DEFAULT NULL,
  `usuarioescuela` varchar(45) DEFAULT NULL,
  `contraseñaescuela` varchar(45) DEFAULT NULL,
  `colectivas` tinyint(1) NOT NULL,
  `individuales` tinyint(1) NOT NULL,
  `excursiones` tinyint(1) NOT NULL,
  `alquilerkayak` tinyint(1) NOT NULL,
  `alquilersurf` tinyint(1) NOT NULL,
  PRIMARY KEY (`idescuela`),
  UNIQUE KEY `usuarioescuela_UNIQUE` (`usuarioescuela`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `material`
--

DROP TABLE IF EXISTS `material`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `material` (
  `idmaterial` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) DEFAULT NULL,
  `escuela_idescuela` int(11) NOT NULL,
  PRIMARY KEY (`idmaterial`),
  KEY `fk_material_escuela1_idx` (`escuela_idescuela`),
  CONSTRAINT `fk_material_escuela1` FOREIGN KEY (`escuela_idescuela`) REFERENCES `escuela` (`idescuela`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `material_caracteristicas_relacion`
--

DROP TABLE IF EXISTS `material_caracteristicas_relacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `material_caracteristicas_relacion` (
  `material_idmaterial` int(11) NOT NULL,
  `caracteristicas_material_idcaracteristicas_material` int(11) NOT NULL,
  `valor` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`material_idmaterial`,`caracteristicas_material_idcaracteristicas_material`),
  KEY `fk_material_has_caracteristicas_material_caracteristicas_ma_idx` (`caracteristicas_material_idcaracteristicas_material`),
  KEY `fk_material_has_caracteristicas_material_material1_idx` (`material_idmaterial`),
  CONSTRAINT `fk_material_has_caracteristicas_material_caracteristicas_mate1` FOREIGN KEY (`caracteristicas_material_idcaracteristicas_material`) REFERENCES `caracteristicas_material` (`idcaracteristicas_material`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_material_has_caracteristicas_material_material1` FOREIGN KEY (`material_idmaterial`) REFERENCES `material` (`idmaterial`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `mensaje`
--

DROP TABLE IF EXISTS `mensaje`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mensaje` (
  `idmensaje` int(11) NOT NULL AUTO_INCREMENT,
  `asunto` varchar(100) DEFAULT NULL,
  `contenido` varchar(600) NOT NULL,
  `leido` tinyint(1) DEFAULT NULL,
  `fecha` date NOT NULL,
  `seguidor_idseguidor` int(11) NOT NULL,
  `escuela_idescuela` int(11) NOT NULL,
  PRIMARY KEY (`idmensaje`),
  KEY `fk_mensaje_seguidor1_idx` (`seguidor_idseguidor`),
  KEY `fk_mensaje_escuela1_idx` (`escuela_idescuela`),
  CONSTRAINT `fk_mensaje_escuela1` FOREIGN KEY (`escuela_idescuela`) REFERENCES `escuela` (`idescuela`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_mensaje_seguidor1` FOREIGN KEY (`seguidor_idseguidor`) REFERENCES `seguidor` (`idseguidor`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `seguidor`
--

DROP TABLE IF EXISTS `seguidor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `seguidor` (
  `idseguidor` int(11) NOT NULL AUTO_INCREMENT,
  `fecha` date DEFAULT NULL,
  `escuela_idescuela` int(11) NOT NULL,
  `usuario_idusuario` int(11) NOT NULL,
  PRIMARY KEY (`idseguidor`),
  KEY `fk_seguidor_escuela1_idx` (`escuela_idescuela`),
  KEY `fk_seguidor_usuario1_idx` (`usuario_idusuario`),
  CONSTRAINT `fk_seguidor_escuela1` FOREIGN KEY (`escuela_idescuela`) REFERENCES `escuela` (`idescuela`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_seguidor_usuario1` FOREIGN KEY (`usuario_idusuario`) REFERENCES `usuario` (`idusuario`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario` (
  `idusuario` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `telefono` varchar(45) NOT NULL,
  `usuario` varchar(45) DEFAULT NULL,
  `contraseñausuario` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idusuario`),
  UNIQUE KEY `usuario_UNIQUE` (`usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `usuario_actividad_relacion`
--

DROP TABLE IF EXISTS `usuario_actividad_relacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario_actividad_relacion` (
  `usuario_idusuario` int(11) NOT NULL,
  `actividad_programada_idactividad_programada` int(11) NOT NULL,
  PRIMARY KEY (`usuario_idusuario`,`actividad_programada_idactividad_programada`),
  KEY `fk_usuario_has_actividad_programada_actividad_programada1_idx` (`actividad_programada_idactividad_programada`),
  KEY `fk_usuario_has_actividad_programada_usuario1_idx` (`usuario_idusuario`),
  CONSTRAINT `fk_usuario_has_actividad_programada_actividad_programada1` FOREIGN KEY (`actividad_programada_idactividad_programada`) REFERENCES `actividad_programada` (`idactividad_programada`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_usuario_has_actividad_programada_usuario1` FOREIGN KEY (`usuario_idusuario`) REFERENCES `usuario` (`idusuario`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-06-17 11:58:42
