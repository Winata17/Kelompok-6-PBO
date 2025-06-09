-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 09, 2025 at 02:37 AM
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
-- Database: `db_sepeda`
--

-- --------------------------------------------------------

--
-- Table structure for table `kategori`
--

CREATE TABLE `kategori` (
  `kategori_id` int(11) NOT NULL,
  `nama_kategori` varchar(20) NOT NULL,
  `image_path` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `kategori`
--

INSERT INTO `kategori` (`kategori_id`, `nama_kategori`, `image_path`) VALUES
(1, 'Road Bike', 'D:\\Documents\\KULIAH\\SEMESTER 4\\PBO\\00 - PBO\\src\\UAS\\GambarUas\\Roadbike.jpg'),
(2, 'Fixie', 'D:\\Documents\\KULIAH\\SEMESTER 4\\PBO\\00 - PBO\\src\\UAS\\GambarUas\\Fixie.jpg'),
(3, 'Sepeda Listrik', 'D:\\Documents\\KULIAH\\SEMESTER 4\\PBO\\00 - PBO\\src\\UAS\\GambarUas\\Listrik.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `order_id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `order_date` datetime DEFAULT NULL,
  `total_price` double DEFAULT NULL,
  `status` varchar(50) DEFAULT 'proses',
  `total_amount` decimal(12,2) DEFAULT NULL,
  `alamat_pengiriman` text NOT NULL,
  `metode_pembayaran` varchar(50) NOT NULL,
  `pesan_opsional` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`order_id`, `user_id`, `order_date`, `total_price`, `status`, `total_amount`, `alamat_pengiriman`, `metode_pembayaran`, `pesan_opsional`) VALUES
(20, 1, '2025-06-01 14:17:41', 5000000, 'dikirim', 1.00, '1', 'Transfer Bank', 'aaaaaa'),
(21, 1, '2025-06-01 14:43:53', 5763999, 'proses', 1.00, '1', 'COD', '1'),
(22, 1, '2025-06-04 18:03:14', 3542000, 'proses', 1.00, 'q', 'COD', 'q'),
(23, 1, '2025-06-04 18:04:30', 3187800, 'proses', 1.00, '1', 'Transfer Bank', '1'),
(24, 1, '2025-06-04 18:35:51', 3364900, 'proses', 1.00, 'v', 'E-Wallet', '1'),
(25, 1, '2025-06-04 18:36:46', 3187800, 'proses', 1.00, 'v', 'Transfer Bank', '1'),
(26, 1, '2025-06-04 18:40:49', 3364900, 'proses', 1.00, '1', 'E-Wallet', '1'),
(27, 1, '2025-06-09 02:51:31', 3187800, 'proses', 1.00, 'qq', 'Transfer Bank', 'aa');

-- --------------------------------------------------------

--
-- Table structure for table `order_items`
--

CREATE TABLE `order_items` (
  `item_id` int(11) NOT NULL,
  `order_id` int(11) DEFAULT NULL,
  `produk_id` int(11) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `harga` decimal(12,2) DEFAULT NULL,
  `subtotal` decimal(12,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `order_items`
--

INSERT INTO `order_items` (`item_id`, `order_id`, `produk_id`, `quantity`, `harga`, `subtotal`) VALUES
(22, 20, 5, 1, NULL, 5000000.00),
(23, 21, 1, 1, NULL, 5763999.00),
(24, 22, 9, 1, NULL, 3542000.00),
(25, 23, 9, 1, NULL, 3187800.00),
(26, 24, 9, 1, NULL, 3364900.00),
(27, 25, 9, 1, NULL, 3187800.00),
(28, 26, 9, 1, NULL, 3364900.00),
(29, 27, 9, 1, NULL, 3187800.00);

-- --------------------------------------------------------

--
-- Table structure for table `produk`
--

CREATE TABLE `produk` (
  `produk_id` int(11) NOT NULL,
  `name` varchar(30) NOT NULL,
  `kategori_id` int(11) DEFAULT NULL,
  `deskripsi` text DEFAULT NULL,
  `stok` int(11) DEFAULT 0,
  `harga` decimal(10,2) NOT NULL,
  `image_path` varchar(150) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `produk`
--

INSERT INTO `produk` (`produk_id`, `name`, `kategori_id`, `deskripsi`, `stok`, `harga`, `image_path`) VALUES
(1, 'Element Curved', 1, 'Cepat dan bergaya, cocok untuk Anda yang suka melaju kencang dan tampil sporty di jalanan', 6, 5763999.00, 'D:\\Documents\\KULIAH\\SEMESTER 4\\PBO\\UAS\\src\\UAS\\GambarUas\\Element Curved.png'),
(3, 'Exotic Varilux Pro', 3, 'Sepeda listrik bertenaga tinggi dengan desain modern dan performa tangguh.', 4, 5900000.00, 'D:\\Documents\\KULIAH\\SEMESTER 4\\PBO\\UAS\\src\\UAS\\GambarUas\\Exotic Varilux Pro.png'),
(4, 'Uwinfly D70', 3, 'Sepeda listrik bergaya elegan yang cocok untuk mobilitas harian di perkotaan.', 1, 4200000.00, 'D:\\Documents\\KULIAH\\SEMESTER 4\\PBO\\UAS\\src\\UAS\\GambarUas\\Uwinfly D70.png'),
(5, 'Pacific Ventura', 3, 'Sepeda listrik nyaman dan efisien, ideal untuk perjalanan santai maupun aktifitas rutin.', 1, 5000000.00, 'D:\\Documents\\KULIAH\\SEMESTER 4\\PBO\\UAS\\src\\UAS\\GambarUas\\Pacific Ventura.png'),
(9, 'Diavolo La Finale Specs', 2, 'Sepeda fixie dengan kualitas bersaing di kelasnya.', 4, 3542000.00, 'D:\\Documents\\KULIAH\\SEMESTER 4\\PBO\\UAS\\src\\UAS\\GambarUas\\Diavolo La Finale Specs.png');

-- --------------------------------------------------------

--
-- Table structure for table `riwayat_transaksi`
--

CREATE TABLE `riwayat_transaksi` (
  `id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `produk_id` int(11) DEFAULT NULL,
  `jumlah` int(11) DEFAULT NULL,
  `total_harga` double DEFAULT NULL,
  `tanggal` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `riwayat_transaksi`
--

INSERT INTO `riwayat_transaksi` (`id`, `user_id`, `produk_id`, `jumlah`, `total_harga`, `tanggal`) VALUES
(16, 1, 5, 1, 5000000, '2025-06-01 14:17:41'),
(17, 1, 1, 1, 5763999, '2025-06-01 14:43:53'),
(18, 1, 9, 1, 3542000, '2025-06-04 18:03:14'),
(19, 1, 9, 1, 3187800, '2025-06-04 18:04:30'),
(20, 1, 9, 1, 3364900, '2025-06-04 18:35:51'),
(21, 1, 9, 1, 3187800, '2025-06-04 18:36:46'),
(22, 1, 9, 1, 3364900, '2025-06-04 18:40:49'),
(23, 1, 9, 1, 3187800, '2025-06-09 02:51:31');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL,
  `username` varchar(20) NOT NULL,
  `email` varchar(30) NOT NULL,
  `password` varchar(20) NOT NULL,
  `full_name` varchar(30) NOT NULL,
  `role` varchar(20) DEFAULT 'user'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `username`, `email`, `password`, `full_name`, `role`) VALUES
(1, 'johndoe', 'john@example.com', '123456', 'John Doellllo', 'user'),
(2, 'janedoe', 'jane@example.com', 'password', 'Jane Doe', 'user'),
(3, 'Kelompok6', 'Kelompok6@gmail.com', 'kelompok6', 'Kelompok6', 'user'),
(4, 'Rafly', 'mraflyyydwiii@gmail.com', '2323', 'Muhammad Rafly Dwi Gunawan', 'user'),
(5, 'Zeva', 'zeva@gmail.com', '1111', 'Zeva Abid', 'user'),
(6, 'Adit Memet', 'adit@gmail.com', '2222', 'Adit', 'user'),
(7, 'win', 'win@gmail.com', '123', 'winata suryana', 'admin');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `kategori`
--
ALTER TABLE `kategori`
  ADD PRIMARY KEY (`kategori_id`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`order_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `order_items`
--
ALTER TABLE `order_items`
  ADD PRIMARY KEY (`item_id`),
  ADD KEY `order_id` (`order_id`),
  ADD KEY `produk_id` (`produk_id`);

--
-- Indexes for table `produk`
--
ALTER TABLE `produk`
  ADD PRIMARY KEY (`produk_id`),
  ADD KEY `kategori_id` (`kategori_id`);

--
-- Indexes for table `riwayat_transaksi`
--
ALTER TABLE `riwayat_transaksi`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `produk_id` (`produk_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `kategori`
--
ALTER TABLE `kategori`
  MODIFY `kategori_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `order_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT for table `order_items`
--
ALTER TABLE `order_items`
  MODIFY `item_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;

--
-- AUTO_INCREMENT for table `produk`
--
ALTER TABLE `produk`
  MODIFY `produk_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `riwayat_transaksi`
--
ALTER TABLE `riwayat_transaksi`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

--
-- Constraints for table `order_items`
--
ALTER TABLE `order_items`
  ADD CONSTRAINT `order_items_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`),
  ADD CONSTRAINT `order_items_ibfk_2` FOREIGN KEY (`produk_id`) REFERENCES `produk` (`produk_id`) ON DELETE CASCADE;

--
-- Constraints for table `produk`
--
ALTER TABLE `produk`
  ADD CONSTRAINT `produk_ibfk_1` FOREIGN KEY (`kategori_id`) REFERENCES `kategori` (`kategori_id`);

--
-- Constraints for table `riwayat_transaksi`
--
ALTER TABLE `riwayat_transaksi`
  ADD CONSTRAINT `riwayat_transaksi_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  ADD CONSTRAINT `riwayat_transaksi_ibfk_2` FOREIGN KEY (`produk_id`) REFERENCES `produk` (`produk_id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
