-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 14-06-2022 a las 13:13:36
-- Versión del servidor: 10.4.21-MariaDB
-- Versión de PHP: 8.0.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `bdasociacion`
--
CREATE DATABASE IF NOT EXISTS `bdasociacion` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `bdasociacion`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `carnets`
--

DROP TABLE IF EXISTS `carnets`;
CREATE TABLE IF NOT EXISTS `carnets` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `vacunas` varchar(500) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categorias`
--

DROP TABLE IF EXISTS `categorias`;
CREATE TABLE IF NOT EXISTS `categorias` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(20) NOT NULL,
  `orden` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `categorias`
--

INSERT INTO `categorias` (`id`, `nombre`, `orden`) VALUES
(1, 'NORMAL', 3),
(2, 'PREMIUM', 2),
(3, 'V.I.P.', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `mascotas`
--

DROP TABLE IF EXISTS `mascotas`;
CREATE TABLE IF NOT EXISTS `mascotas` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(30) NOT NULL,
  `fechanac` date NOT NULL,
  `adoptada` tinyint(1) NOT NULL DEFAULT 0,
  `idave` int(11) DEFAULT NULL,
  `idmamifero` int(11) DEFAULT NULL,
  `salvaje` tinyint(1) DEFAULT NULL,
  `peso` decimal(5,3) DEFAULT NULL,
  `idcarnet` int(11) DEFAULT NULL,
  `idsocio` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_carnets` (`idcarnet`),
  KEY `FK_socios` (`idsocio`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `socios`
--

DROP TABLE IF EXISTS `socios`;
CREATE TABLE IF NOT EXISTS `socios` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150) NOT NULL,
  `fechanac` date NOT NULL,
  `fechaalta` date NOT NULL,
  `idcategoria` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_categorias` (`idcategoria`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `mascotas`
--
ALTER TABLE `mascotas`
  ADD CONSTRAINT `FK_carnets` FOREIGN KEY (`idcarnet`) REFERENCES `carnets` (`id`) ON DELETE SET NULL ON UPDATE SET NULL,
  ADD CONSTRAINT `FK_socios` FOREIGN KEY (`idsocio`) REFERENCES `socios` (`id`) ON DELETE SET NULL ON UPDATE SET NULL;

--
-- Filtros para la tabla `socios`
--
ALTER TABLE `socios`
  ADD CONSTRAINT `FK_categorias` FOREIGN KEY (`idcategoria`) REFERENCES `categorias` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
