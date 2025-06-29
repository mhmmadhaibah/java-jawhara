-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 29 Jun 2025 pada 22.52
-- Versi server: 10.4.32-MariaDB
-- Versi PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `jawhara`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `categories`
--

CREATE TABLE `categories` (
  `id` int(11) NOT NULL,
  `name` varchar(16) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `categories`
--

INSERT INTO `categories` (`id`, `name`) VALUES
(1, 'Bergo'),
(2, 'Dress'),
(3, 'Scarf'),
(4, 'Pashmina');

-- --------------------------------------------------------

--
-- Struktur dari tabel `outlets`
--

CREATE TABLE `outlets` (
  `id` int(11) NOT NULL,
  `name` varchar(16) NOT NULL,
  `description` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `outlets`
--

INSERT INTO `outlets` (`id`, `name`, `description`) VALUES
(1, 'JS Building', 'Toko Cabang Utama'),
(2, 'WhatsApp Admin', 'Pesanan Masuk dari WA'),
(3, 'TikTok Shop', 'Marketplace'),
(4, 'Tokopedia', 'Marketplace'),
(5, 'Shopee', 'Marketplace');

-- --------------------------------------------------------

--
-- Struktur dari tabel `products`
--

CREATE TABLE `products` (
  `id` int(11) NOT NULL,
  `supplier_id` int(11) NOT NULL,
  `category_id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `products`
--

INSERT INTO `products` (`id`, `supplier_id`, `category_id`, `name`) VALUES
(1, 1, 1, 'Bestari Series'),
(2, 1, 1, 'Daily Lady'),
(3, 1, 1, 'Gendhis Series'),
(4, 1, 1, 'Btary Series'),
(5, 1, 1, 'Malika Series'),
(6, 1, 1, 'Anisa Series'),
(7, 1, 1, 'Daily Damara'),
(8, 1, 1, 'Daily Delia'),
(9, 1, 1, 'Daily Saliva'),
(10, 1, 2, 'Habibi Mecca Series'),
(11, 1, 2, 'Annora Series'),
(12, 1, 2, 'Ariana Series'),
(13, 1, 2, 'Manohara Series'),
(14, 1, 2, 'Helena Series'),
(15, 1, 2, 'Khadijah Series'),
(16, 1, 2, 'Madina Series'),
(17, 1, 3, 'Azizah Series'),
(18, 1, 3, 'Floresta Series'),
(19, 1, 3, 'Caroline Series'),
(20, 1, 3, 'Monogram Series'),
(21, 1, 3, 'Calanta Series'),
(22, 1, 3, 'Delarosa Series'),
(23, 1, 3, 'Sherina Series'),
(24, 1, 4, 'Daily Fatimah'),
(25, 1, 4, 'Daily Elma'),
(26, 2, 1, 'Sutra Sofia'),
(27, 2, 1, 'Yasmin Layla'),
(28, 2, 2, 'Gamis Syarifah'),
(29, 2, 2, 'Gamis Aisyah'),
(30, 2, 4, 'Dubai Silk'),
(31, 2, 4, 'Medina Luxe'),
(32, 2, 3, 'Zayna Huda'),
(33, 2, 3, 'Jasmine'),
(34, 3, 1, 'Elif Selin'),
(35, 3, 1, 'Yasemin'),
(36, 3, 4, 'Istanbul Silk'),
(37, 3, 4, 'Ankara Voile'),
(38, 3, 3, 'Ottoman Luxe'),
(39, 3, 3, 'Bosphorus'),
(40, 3, 3, 'Suzan Minel'),
(41, 3, 2, 'Emine Selma'),
(42, 3, 2, 'Dilara Esma'),
(43, 3, 3, 'Eylul Ruya'),
(44, 3, 3, 'Zehra Topkapi');

-- --------------------------------------------------------

--
-- Struktur dari tabel `product_stocks`
--

CREATE TABLE `product_stocks` (
  `id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `product_stocks`
--

INSERT INTO `product_stocks` (`id`, `product_id`, `quantity`) VALUES
(1, 1, 23),
(2, 2, 48),
(3, 3, 60),
(4, 4, 24),
(5, 5, 24),
(6, 6, 24),
(7, 7, 24),
(8, 8, 12),
(9, 9, 36),
(10, 10, 16),
(11, 11, 36),
(12, 12, 24),
(13, 13, 36),
(14, 14, 24),
(15, 15, 36),
(16, 16, 24),
(17, 17, 36),
(18, 18, 24),
(19, 19, 24),
(20, 20, 24),
(21, 21, 24),
(22, 22, 24),
(23, 23, 60),
(24, 24, 35),
(25, 25, 35),
(26, 26, 12),
(27, 27, 35),
(28, 28, 36),
(29, 29, 36),
(30, 30, 43),
(31, 31, 36),
(32, 32, 36),
(33, 33, 24),
(34, 34, 24),
(35, 35, 36),
(36, 36, 35),
(37, 37, 36),
(38, 38, 36),
(39, 39, 36),
(40, 40, 24),
(41, 41, 36),
(42, 42, 36),
(43, 43, 0),
(44, 44, 0);

-- --------------------------------------------------------

--
-- Struktur dari tabel `stock_adjustments`
--

CREATE TABLE `stock_adjustments` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `notes` text DEFAULT NULL,
  `timestamp` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `stock_adjustments`
--

INSERT INTO `stock_adjustments` (`id`, `user_id`, `product_id`, `quantity`, `notes`, `timestamp`) VALUES
(1, 2, 30, 7, 'Gudang', '2025-06-29 20:22:01'),
(2, 2, 31, 2, 'Gudang', '2025-06-29 20:22:15');

-- --------------------------------------------------------

--
-- Struktur dari tabel `suppliers`
--

CREATE TABLE `suppliers` (
  `id` int(11) NOT NULL,
  `name` varchar(16) NOT NULL,
  `address` text NOT NULL,
  `phone` varchar(20) NOT NULL,
  `email` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `suppliers`
--

INSERT INTO `suppliers` (`id`, `name`, `address`, `phone`, `email`) VALUES
(1, 'Jawhara Boutique', 'Jl. Kincan Raya', '02121386789', 'jawhara.boutique@gmail.com'),
(2, 'Abaya Dubai', 'Jl. Condet', '02194422444', 'abaya.dubai@gmail.com'),
(3, 'Hurrem Turkiye', 'Jl. Pejaten', '02177723455', 'hurrem.turkiye@gmail.com');

-- --------------------------------------------------------

--
-- Struktur dari tabel `transactions`
--

CREATE TABLE `transactions` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `outlet_id` int(11) DEFAULT NULL,
  `type` enum('IN','OUT') NOT NULL,
  `notes` text DEFAULT NULL,
  `timestamp` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `transactions`
--

INSERT INTO `transactions` (`id`, `user_id`, `outlet_id`, `type`, `notes`, `timestamp`) VALUES
(1, 7, NULL, 'IN', 'Suppliers', '2025-06-29 20:26:57'),
(2, 7, NULL, 'IN', 'Abaya Dubai', '2025-06-29 20:34:11'),
(3, 7, NULL, 'IN', 'Hurrem Turkiye', '2025-06-29 20:34:50'),
(4, 7, NULL, 'IN', 'Jawhara Boutique', '2025-06-29 20:36:57'),
(5, 7, 5, 'OUT', 'Penyelesaian Pesanan', '2025-06-29 20:39:47'),
(6, 8, 3, 'OUT', 'Penyelesaian Pesanan', '2025-06-29 20:40:37'),
(7, 3, 1, 'OUT', 'Penjualan Toko', '2025-06-29 20:45:31'),
(8, 3, NULL, 'IN', 'Jawhara Boutique', '2025-06-29 20:46:26'),
(9, 9, 1, 'OUT', 'Penjualan Toko', '2025-06-29 20:47:52');

-- --------------------------------------------------------

--
-- Struktur dari tabel `transaction_details`
--

CREATE TABLE `transaction_details` (
  `id` int(11) NOT NULL,
  `transaction_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `transaction_details`
--

INSERT INTO `transaction_details` (`id`, `transaction_id`, `product_id`, `quantity`) VALUES
(1, 1, 2, 36),
(2, 1, 7, 24),
(3, 1, 8, 12),
(4, 1, 9, 36),
(5, 1, 27, 36),
(6, 1, 35, 36),
(7, 1, 3, 36),
(8, 1, 11, 36),
(9, 1, 13, 36),
(10, 1, 15, 36),
(11, 1, 28, 36),
(12, 1, 41, 36),
(13, 1, 29, 36),
(14, 1, 42, 36),
(15, 1, 17, 36),
(16, 1, 23, 36),
(17, 1, 32, 36),
(18, 1, 38, 36),
(19, 1, 39, 36),
(20, 1, 24, 36),
(21, 1, 25, 36),
(22, 1, 30, 36),
(23, 1, 31, 36),
(24, 1, 36, 36),
(25, 1, 37, 36),
(26, 2, 26, 12),
(27, 2, 33, 24),
(28, 3, 34, 24),
(29, 3, 40, 24),
(30, 4, 1, 24),
(31, 4, 6, 24),
(32, 4, 4, 24),
(33, 4, 5, 24),
(34, 4, 3, 24),
(35, 4, 14, 24),
(36, 4, 10, 24),
(37, 4, 16, 24),
(38, 4, 12, 24),
(39, 4, 19, 24),
(40, 4, 22, 24),
(41, 4, 20, 24),
(42, 4, 18, 24),
(43, 4, 21, 24),
(44, 4, 23, 24),
(45, 5, 24, 1),
(46, 5, 25, 1),
(47, 5, 31, 1),
(48, 6, 10, 7),
(49, 7, 1, 1),
(50, 7, 36, 1),
(51, 7, 31, 1),
(52, 7, 10, 1),
(53, 8, 2, 12),
(54, 9, 27, 1);

-- --------------------------------------------------------

--
-- Struktur dari tabel `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `role` enum('Admin','Staff') NOT NULL DEFAULT 'Staff',
  `username` varchar(16) NOT NULL,
  `password` varchar(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `users`
--

INSERT INTO `users` (`id`, `name`, `role`, `username`, `password`) VALUES
(1, 'Admin', 'Admin', 'admin', '5f4dcc3b5aa765d61d8327deb882cf99'),
(2, 'Muhammad Haibah', 'Admin', 'haibah', '5f4dcc3b5aa765d61d8327deb882cf99'),
(3, 'Dandy Septiawan', 'Staff', 'dandy', '5f4dcc3b5aa765d61d8327deb882cf99'),
(4, 'Taufik Laleno', 'Staff', 'topik', '5f4dcc3b5aa765d61d8327deb882cf99'),
(5, 'Muhammad Dwi Kurniawan', 'Staff', 'iwan', '5f4dcc3b5aa765d61d8327deb882cf99'),
(6, 'Dany Primero', 'Staff', 'dany', '5f4dcc3b5aa765d61d8327deb882cf99'),
(7, 'Paramita Susanti', 'Staff', 'mita', '5f4dcc3b5aa765d61d8327deb882cf99'),
(8, 'Tasya Laila Syakira', 'Staff', 'tasya', '5f4dcc3b5aa765d61d8327deb882cf99'),
(9, 'Arwindy Reviana', 'Staff', 'windy', '5f4dcc3b5aa765d61d8327deb882cf99');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `outlets`
--
ALTER TABLE `outlets`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `product_stocks`
--
ALTER TABLE `product_stocks`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `stock_adjustments`
--
ALTER TABLE `stock_adjustments`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `suppliers`
--
ALTER TABLE `suppliers`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `transactions`
--
ALTER TABLE `transactions`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `transaction_details`
--
ALTER TABLE `transaction_details`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `categories`
--
ALTER TABLE `categories`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT untuk tabel `outlets`
--
ALTER TABLE `outlets`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT untuk tabel `products`
--
ALTER TABLE `products`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=45;

--
-- AUTO_INCREMENT untuk tabel `product_stocks`
--
ALTER TABLE `product_stocks`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=45;

--
-- AUTO_INCREMENT untuk tabel `stock_adjustments`
--
ALTER TABLE `stock_adjustments`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT untuk tabel `suppliers`
--
ALTER TABLE `suppliers`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT untuk tabel `transactions`
--
ALTER TABLE `transactions`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT untuk tabel `transaction_details`
--
ALTER TABLE `transaction_details`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=55;

--
-- AUTO_INCREMENT untuk tabel `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
