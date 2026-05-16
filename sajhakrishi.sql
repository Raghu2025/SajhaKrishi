-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 16, 2026 at 11:32 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `sajhakrishi`
--

-- --------------------------------------------------------

--
-- Table structure for table `bookings`
--

CREATE TABLE `bookings` (
  `id` bigint(20) NOT NULL,
  `equipment_id` bigint(20) NOT NULL,
  `kisan_id` bigint(20) NOT NULL,
  `owner_id` bigint(20) NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `total_days` int(11) NOT NULL,
  `price_per_day` decimal(10,2) NOT NULL,
  `total_price` decimal(10,2) NOT NULL,
  `deposit_amount` decimal(10,2) DEFAULT 0.00,
  `status` varchar(20) DEFAULT 'A',
  `payment_status` varchar(20) DEFAULT 'UNPAID',
  `pickup_address` text DEFAULT NULL,
  `notes` text DEFAULT NULL,
  `status_flag` varchar(20) DEFAULT 'PENDING',
  `booked_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `bookings`
--

INSERT INTO `bookings` (`id`, `equipment_id`, `kisan_id`, `owner_id`, `start_date`, `end_date`, `total_days`, `price_per_day`, `total_price`, `deposit_amount`, `status`, `payment_status`, `pickup_address`, `notes`, `status_flag`, `booked_at`, `created_at`) VALUES
(1, 14, 21, 20, '2026-05-13', '2026-05-16', 3, 988.00, 2964.00, 42.00, 'A', 'UNPAID', 'Kathmandu', '', 'COMPLETED', '2026-05-13 11:19:58', '2026-05-13 11:19:58'),
(2, 14, 21, 20, '2026-05-13', '2026-05-15', 2, 988.00, 1976.00, 42.00, 'A', 'UNPAID', 'dvdvd', '', 'PENDING', '2026-05-13 11:22:38', '2026-05-13 11:22:38'),
(3, 14, 21, 20, '2026-05-13', '2026-05-16', 3, 988.00, 2964.00, 42.00, 'A', 'UNPAID', 'Kathmandu', '', 'PENDING', '2026-05-13 11:23:41', '2026-05-13 11:23:41'),
(4, 14, 21, 20, '2026-05-13', '2026-05-15', 2, 988.00, 1976.00, 42.00, 'A', 'UNPAID', 'kathandu', '', 'CANCELLED', '2026-05-13 11:25:44', '2026-05-13 11:25:44'),
(5, 14, 21, 20, '2026-06-14', '2026-06-20', 6, 988.00, 5928.00, 42.00, 'A', 'UNPAID', 'Mandikatar, Kathmandu', '', 'CONFIRMED', '2026-05-14 09:28:27', '2026-05-14 09:28:27'),
(6, 14, 21, 20, '2026-06-14', '2026-06-20', 6, 988.00, 5928.00, 42.00, 'A', 'UNPAID', 'Kathmandu', '', 'CANCELLED', '2026-05-14 09:29:01', '2026-05-14 09:29:01');

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE `category` (
  `id` bigint(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `status` char(1) DEFAULT 'A'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`id`, `name`, `status`) VALUES
(1, 'Tractors & Power Tillers', 'A'),
(2, 'Mini Tillers & Weeders', 'A'),
(3, 'Irrigation & Pumps', 'A'),
(4, 'Harvesting Machinery1', 'A'),
(5, 'Post-Harvest Tools', 'A'),
(6, 'Plant Protection', 'A'),
(7, 'Seeding & Fertilizing', 'A'),
(8, 'Livestock Equipment', 'A');

-- --------------------------------------------------------

--
-- Table structure for table `equipment`
--

CREATE TABLE `equipment` (
  `id` bigint(20) NOT NULL,
  `status` char(1) DEFAULT 'A',
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `name` varchar(255) NOT NULL,
  `category_id` bigint(100) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `brand` varchar(100) DEFAULT NULL,
  `manufacture_year` int(11) DEFAULT NULL,
  `price_per_day` decimal(10,2) DEFAULT NULL,
  `price_per_hour` decimal(10,2) DEFAULT NULL,
  `deposit_amount` decimal(10,2) DEFAULT NULL,
  `availability_status` varchar(50) DEFAULT NULL,
  `district` varchar(100) DEFAULT NULL,
  `municipality` varchar(100) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `condition_` varchar(100) DEFAULT NULL,
  `specifications` text DEFAULT NULL,
  `fuel_type` varchar(50) DEFAULT NULL,
  `image_path` varchar(500) DEFAULT NULL,
  `owner_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `equipment`
--

INSERT INTO `equipment` (`id`, `status`, `created_at`, `name`, `category_id`, `description`, `brand`, `manufacture_year`, `price_per_day`, `price_per_hour`, `deposit_amount`, `availability_status`, `district`, `municipality`, `address`, `condition_`, `specifications`, `fuel_type`, `image_path`, `owner_id`) VALUES
(12, 'I', '2026-05-10 11:14:01', 'Test44', 3, 'Nulla eos in ad libe', 'Rerum id inventore ', 1993, 988.00, 453.00, 42.00, 'A', 'Panchthar', 'Nobis debitis animi', NULL, NULL, 'A aut repellendus A', NULL, '/uploads/equipment/1778411641553_tractor.jpg', 20),
(13, 'I', '2026-05-10 11:16:32', 'Acton Cooke4', 7, 'Accusamus vero magni', 'Iure deserunt dolor ', 2004, 520.00, 905.00, 59.00, 'A', 'Sindhupalchok', 'Illo officia provide', NULL, NULL, 'Itaque est commodo ', NULL, '/uploads/equipment/1778411792230_tractor.jpg', 20),
(14, 'A', '2026-05-11 15:00:27', 'Damian Orr', 3, 'Nulla eos in ad libe', 'Rerum id inventore ', 1993, 988.00, 453.00, 42.00, 'A', 'Panchthar', 'Nobis debitis animi', NULL, NULL, 'A aut repellendus A', NULL, '/uploads/equipment/1778516385664_logo.png', 20),
(15, 'A', '2026-05-11 15:05:43', 'Damian Orr22', 3, 'Nulla eos in ad libe', 'Rerum id inventore ', 1993, 988.00, 453.00, 42.00, 'A', 'Panchthar', 'Nobis debitis animi', NULL, NULL, 'A aut repellendus A', NULL, '/uploads/equipment/1778511943224_logo.png', 20),
(16, 'A', '2026-05-11 15:58:40', 'Damian Orr33', 3, 'Nulla eos in ad libe', 'Rerum id inventore ', 1993, 988.00, 453.00, 42.00, 'A', 'Panchthar', 'Nobis debitis animi', NULL, NULL, 'A aut repellendus A', NULL, NULL, 20),
(17, 'A', '2026-05-11 16:00:25', 'Damian Orr33466', 3, 'Nulla eos in ad libe', 'Rerum id inventore ', 1993, 988.00, 453.00, 42.00, 'A', 'Panchthar', 'Nobis debitis animi', NULL, NULL, 'A aut repellendus A', NULL, NULL, 20),
(18, 'I', '2026-05-11 16:23:46', 'Kendall Baker', 7, 'Commodo numquam aliq', 'Aliqua Labore volup', 2003, 486.00, 26.00, 64.00, 'A', 'Myagdi', 'Officia excepteur cu', NULL, NULL, 'Qui porro ad modi ea', NULL, '/uploads/equipment/1778516626791_logo.png', 20),
(19, 'A', '2026-05-13 05:13:43', 'Amelia Stewart', 3, 'Qui facere recusanda', 'Totam autem nostrum ', 1994, 649.00, 937.00, 50.00, 'A', 'Dhading', 'Do ipsa voluptatem', NULL, NULL, 'Dolore autem sed acc', NULL, '/uploads/equipment/1778649223705_tractor.jpg', 20),
(20, 'A', '2026-05-13 05:14:19', 'Mark Mcintosh', 5, 'Suscipit aut aut dol', 'Iste dolore ipsum e', 1992, 711.00, 554.00, 64.00, 'A', 'Kailali', 'Aut suscipit dolorem', NULL, NULL, 'Ut laborum culpa as', NULL, '/uploads/equipment/1778649259219_tractor.jpg', 21),
(21, 'A', '2026-05-14 08:32:10', 'Excavator', 1, 'Digging foundations, Break down structures, Lift and move heavy items ', 'Caterpillar', 2022, 1500.00, 300.00, 500.00, NULL, 'Bhaktapur', 'Bhaktapur', NULL, NULL, 'Addition of new inventory', NULL, '/uploads/equipment/1778747530786_logo-removebg-preview.png', 17);

-- --------------------------------------------------------

--
-- Table structure for table `payments`
--

CREATE TABLE `payments` (
  `id` bigint(20) NOT NULL,
  `booking_id` bigint(20) NOT NULL,
  `amount` decimal(10,2) NOT NULL,
  `payment_method` varchar(50) DEFAULT NULL,
  `payment_status` varchar(20) DEFAULT 'Pending',
  `paid_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `status` varchar(1) DEFAULT 'A'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `role`
--

CREATE TABLE `role` (
  `id` bigint(20) NOT NULL,
  `name` varchar(100) NOT NULL,
  `status` varchar(5) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `role`
--

INSERT INTO `role` (`id`, `name`, `status`, `created_at`) VALUES
(1, 'ADMIN', 'A', '2026-04-16 16:04:09'),
(2, 'NORMAL', 'A', '2026-04-16 16:16:00');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` bigint(11) NOT NULL,
  `full_name` varchar(100) NOT NULL,
  `phone_number` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `district` varchar(50) DEFAULT NULL,
  `role` bigint(20) DEFAULT NULL,
  `status` varchar(5) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `email` varchar(25) DEFAULT NULL,
  `failed_login_attempts` int(11) DEFAULT 0,
  `last_failed_login` bigint(20) DEFAULT NULL,
  `account_locked_until` bigint(20) DEFAULT NULL,
  `is_locked` char(1) DEFAULT 'N'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `full_name`, `phone_number`, `password`, `address`, `district`, `role`, `status`, `created_at`, `email`, `failed_login_attempts`, `last_failed_login`, `account_locked_until`, `is_locked`) VALUES
(17, 'Raghu', '9800000000', '$2a$10$IYLLwAUpss4PURT4g941y.L9fcxItbHEaXmUtzeeQuDF2OXm.2G4W', 'Kaldara', 'Humla', 1, 'A', '2026-04-17 04:44:38', 'admin@gmail.com', 0, NULL, NULL, 'N'),
(18, 'Bijay', '12345678', '$2a$10$CLsHem/j9RuTKTh5A3c15ufbrN4B4XaPLcMNKiPbqY1rLKaEP6bUO', 'Koteshowor', 'Kathmandu', 2, 'A', '2026-05-04 08:26:25', 'bijay@gmail.com', 0, NULL, NULL, 'N'),
(19, 'Zenia Harmon', '+1 (239) 363-2407', '$2a$10$Jwamp.xSZIy3qUzsY70Ln.cO8lBzUXMhx3nrOkfICPi62CZzppHcC', 'Esse nostrud placea', 'Bhaktapur', 2, 'A', '2026-05-04 08:27:06', 'borir@mailinator.com', 0, NULL, NULL, 'N'),
(20, 'Maryam Wynn', '+1 (402) 655-9355', '$2a$10$NYm7qsCRytLZ.R.R5UyrPe46ASOdHAqtYon/P.fp9nxYz0G5/pZo.', 'Consequatur Ut qui ', 'Dailekh', 2, 'A', '2026-05-04 08:27:52', 'nure@mailinator.com', 0, NULL, NULL, 'N'),
(21, 'Paula Young', '+1 (659) 347-4603', '$2a$10$NYm7qsCRytLZ.R.R5UyrPe46ASOdHAqtYon/P.fp9nxYz0G5/pZo.', 'Ipsum eiusmod corpor', 'Banke', 2, 'A', '2026-05-13 05:13:55', 'kigu@mailinator.com', 0, NULL, NULL, 'N');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `bookings`
--
ALTER TABLE `bookings`
  ADD PRIMARY KEY (`id`),
  ADD KEY `equipment_id` (`equipment_id`),
  ADD KEY `kisan_id` (`kisan_id`),
  ADD KEY `owner_id` (`owner_id`);

--
-- Indexes for table `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `equipment`
--
ALTER TABLE `equipment`
  ADD PRIMARY KEY (`id`),
  ADD KEY `owner_id` (`owner_id`),
  ADD KEY `category_id` (`category_id`);

--
-- Indexes for table `payments`
--
ALTER TABLE `payments`
  ADD PRIMARY KEY (`id`),
  ADD KEY `booking_id` (`booking_id`);

--
-- Indexes for table `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_user_role` (`role`),
  ADD KEY `idx_email` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `bookings`
--
ALTER TABLE `bookings`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `category`
--
ALTER TABLE `category`
  MODIFY `id` bigint(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `equipment`
--
ALTER TABLE `equipment`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT for table `payments`
--
ALTER TABLE `payments`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `role`
--
ALTER TABLE `role`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `bookings`
--
ALTER TABLE `bookings`
  ADD CONSTRAINT `bookings_ibfk_1` FOREIGN KEY (`equipment_id`) REFERENCES `equipment` (`id`),
  ADD CONSTRAINT `bookings_ibfk_2` FOREIGN KEY (`kisan_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `bookings_ibfk_3` FOREIGN KEY (`owner_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `equipment`
--
ALTER TABLE `equipment`
  ADD CONSTRAINT `equipment_ibfk_1` FOREIGN KEY (`owner_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `equipment_ibfk_2` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`);

--
-- Constraints for table `payments`
--
ALTER TABLE `payments`
  ADD CONSTRAINT `payments_ibfk_1` FOREIGN KEY (`booking_id`) REFERENCES `bookings` (`id`);

--
-- Constraints for table `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `fk_user_role` FOREIGN KEY (`role`) REFERENCES `role` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
