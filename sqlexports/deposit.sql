-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: May 03, 2022 at 08:32 AM
-- Server version: 5.7.36
-- PHP Version: 7.4.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `deposit`
--

-- --------------------------------------------------------

--
-- Table structure for table `deposit`
--

DROP TABLE IF EXISTS `deposit`;
CREATE TABLE IF NOT EXISTS `deposit` (
  `id` bigint(20) NOT NULL,
  `balance` decimal(19,2) DEFAULT NULL,
  `currency` int(11) DEFAULT NULL,
  `customer_id` bigint(20) DEFAULT NULL,
  `deposit_number` bigint(20) DEFAULT NULL,
  `deposit_type` int(11) DEFAULT NULL,
  `expire_date` datetime DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_50sy82hsyq1c2tagtynydvdx` (`deposit_number`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `deposit`
--

INSERT INTO `deposit` (`id`, `balance`, `currency`, `customer_id`, `deposit_number`, `deposit_type`, `expire_date`, `start_date`, `status`, `title`) VALUES
(1, '10000.00', 0, 1, 12345679, 0, '2023-05-03 12:59:06', '2022-05-03', 0, 'alibeyfaSHORT_TERM'),
(2, '15000.00', 0, 2, 12345680, 0, '2023-05-03 13:00:37', '2022-05-03', 0, 'rezamohamdadiSHORT_TERM');

-- --------------------------------------------------------

--
-- Table structure for table `deposit_sequence`
--

DROP TABLE IF EXISTS `deposit_sequence`;
CREATE TABLE IF NOT EXISTS `deposit_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `deposit_sequence`
--

INSERT INTO `deposit_sequence` (`next_val`) VALUES
(3);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
