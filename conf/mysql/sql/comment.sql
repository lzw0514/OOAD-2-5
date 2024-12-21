-- Host: mysql    Database: comment

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content` varchar(128) DEFAULT NULL,
  `reject_reason` varchar(128) DEFAULT NULL,
  `type` tinyint DEFAULT NULL,
  `orderitem_id` bigint DEFAULT NULL,
  `reviewer_id` bigint DEFAULT NULL,
  `product_id` bigint DEFAULT NULL,
  `shop_id` bigint DEFAULT NULL,
  `reply_id` bigint DEFAULT NULL,
  `add_id` bigint DEFAULT NULL,
  `parent_id` bigint DEFAULT NULL,
  `creator_id` bigint DEFAULT NULL,
  `creator_name` varchar(128) DEFAULT NULL,
  `modifier_id` bigint DEFAULT NULL,
  `modifier_name` varchar(128) DEFAULT NULL,
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime DEFAULT NULL,
  `gmt_publish` datetime DEFAULT NULL,
  `replyable` BOOLEAN DEFAULT FALSE,
  `addtionable` BOOLEAN DEFAULT FALSE,
  `status` tinyint DEFAULT NULL,
  `report_reason` varchar(128) DEFAULT NULL,

  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=57148 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES 
(1,'东西很好', NULL, 0, 12345, 1, 1559, 11, NULL, 3, NULL, 514, '张三', NULL, NULL, '2024-12-18 18:10:26', '2024-12-18 18:29:26', '2024-12-18 18:29:26', FALSE, FALSE,1,NULL),
(2,'某宝比OOMALL商城的东西便宜多了，大家不要在这里买东西', '内容含广告信息', 1, NULL, 1, 1559,11, NULL, NULL, 1, 514, '张三', NULL, NULL, '2024-12-20 18:29:26', '2024-12-20 18:30:26', NULL,FALSE, FALSE,2,NULL),
(3,'客服态度很好，但商品质量差。', NULL, 1, NULL, 1, 1559, 11, 7, NULL, 1, 514,'张三', NULL, NULL, '2024-12-18 18:29:26', '2024-12-18 18:30:26', '2024-12-18 18:29:26', FALSE, FALSE,1,NULL),
(4,'东西很好', NULL, 0, 12348, 1, 1600, 11, NULL, NULL, NULL, 514, '张三', NULL, NULL, '2024-12-18 18:25:26', '2024-12-18 18:29:26', '2024-12-18 18:29:26', TRUE,TRUE,1,NULL),
(5,'东西很好', NULL, 0, 12349, 1, 1600, 12, NULL, NULL, NULL, 514, '张三', NULL, NULL,'2024-12-18 18:29:26', NULL,NULL, TRUE, TRUE,1,NULL),
(6,'感谢您的支持。', NULL, 2, NULL, 1, 1559, 11, NULL, NULL, 1, 11, 'MAYDAY店铺', NULL, NULL,'2024-12-18 18:29:26', '2024-12-18 18:29:26','2024-12-18 18:29:26', FALSE, FALSE,1,NULL),
(7,'抱歉给您带来不好的体验。', NULL, 2, NULL, 1, 1559, 11, NULL, NULL, 3, 11, 'MAYDAY店铺', NULL, NULL,'2024-12-18 18:29:26', '2024-12-18 18:29:26','2024-12-18 18:29:26', FALSE, FALSE,1,NULL),
(8,'客服态度很好，但商品质量差。', NULL, 0, 12350, 1, 1600, 11, 9, NULL, NULL, 514, '张三', NULL, NULL,'2024-12-18 18:29:26', '2024-12-18 18:29:26','2024-12-18 18:29:26', TRUE, FALSE,1,NULL),
(9,'客服态度很好，但商品质量差。', NULL, 1, 12350, 1, 1600, 11, NULL, NULL, 8, 514, '张三', NULL, NULL,'2024-12-18 18:29:26', '2024-12-18 18:29:26','2024-12-18 18:29:26', TRUE, FALSE,1,NULL),
(10,'某宝比OOMALL商城的东西便宜多了，大家不要在这里买东西', '内容含广告信息', 0, 12351, 1, 1600, 11, NULL, NULL, NULL, 514, '张三', NULL, NULL,'2024-12-18 18:29:26', NULL,NULL, FALSE, FALSE,2,NULL),
(11,'某宝比OOMALL商城的东西便宜多了，大家不要在这里买东西', NULL, 0, 12352, NULL, 1600, 11, NULL, NULL, NULL, 514, '张三', NULL, NULL,'2024-12-18 18:29:26', NULL,NULL, FALSE, FALSE,0,NULL),
(12,'东西很好', NULL, 0, 12353, NULL, 1600, 11, NULL, NULL, NULL, 514, '张三', NULL, NULL,'2024-12-18 18:29:26', NULL,NULL, FALSE, FALSE,0,NULL),
(13,'客服态度很好，但商品质量差。', NULL, 1, 12356, NULL, 1600, 11, NULL, NULL, 15, 514, '张三', NULL, NULL,'2024-12-18 18:29:26', NULL,NULL, FALSE, FALSE,0,NULL),
(14,'抱歉给您带来不好的体验', NULL, 2, 12356, NULL, 1600, 11, NULL, NULL, 15, 11, 'MAYDAY商铺', NULL, NULL,'2024-12-18 18:29:26', NULL,NULL, FALSE, FALSE,0,NULL),
(15,'东西很好', NULL, 0, 12356, 1, 1600, 11, NULL, 16, NULL, 514, '张三', NULL, NULL,'2024-12-18 18:29:26','2024-12-18 18:29:26','2024-12-18 18:29:26',TRUE, FALSE,1,NULL),
(16,'客服态度很好，但商品质量差。', NULL, 1, 12356,1, 1600, 11, NULL, NULL, 15, 514, '张三', NULL, NULL,'2024-12-18 18:29:26', '2024-12-18 18:29:26','2024-12-18 18:29:26', TRUE, FALSE,1,NULL),
(17,'抱歉给您带来不好的体验', NULL, 2, 12356, NULL, 1600, 11, NULL, 16, NULL, 11, 'MAYDAY商铺', NULL, NULL,'2024-12-18 18:29:26', NULL,NULL, FALSE, FALSE,0,NULL),
(18,'东西很好', NULL, 0, 12357, 1, 1600, 11, NULL, NULL, NULL, 515, '张三', NULL, NULL, '2024-12-18 18:25:26', '2024-12-18 18:29:26', '2024-12-18 18:29:26', TRUE,TRUE,1,NULL),
(19,'客服态度很好，但商品质量差。', NULL, 1, 12358, 1, 1600, 11, NULL, NULL, 30, 515, '张三', NULL, NULL, '2024-12-18 18:25:26', '2024-12-18 18:29:26', '2024-12-18 18:29:26', TRUE,FALSE,1,NULL),
(20,'东西很好', NULL, 0, 12359, 1, 1600, 11, 22, 21, NULL, 514, '张三', NULL, NULL, '2024-12-18 18:25:26', '2024-12-18 18:29:26', '2024-12-18 18:29:26', FALSE,FALSE,4,'评论传播不实信息，可能误导他人'),
(21,'客服态度很好，但商品质量差。', NULL, 1, 12359, 1, 1600, 11, 23, NULL, 20, 514, '张三', NULL, NULL, '2024-12-18 18:25:26', '2024-12-18 18:29:26', '2024-12-18 18:29:26', FALSE,FALSE,1,NULL),
(22,'抱歉给您带来不好的体验', NULL, 2, 12359, 1, 1600, 11, NULL, NULL, 20, 11, 'MAYDAY商铺', NULL, NULL,'2024-12-18 18:29:26','2024-12-18 18:29:26','2024-12-18 18:29:26', FALSE, FALSE,0,NULL),
(23,'抱歉给您带来不好的体验', NULL, 2, 12359, 1, 1600, 11, NULL, NULL, 21, 11, 'MAYDAY商铺', NULL, NULL,'2024-12-18 18:29:26','2024-12-18 18:29:26','2024-12-18 18:29:26', FALSE, FALSE,0,NULL),
(24,'客服态度很好，但商品质量差。', NULL, 1, 12360, 1, 1600, 11, 25, NULL, 30, 514, '张三', NULL, NULL, '2024-12-18 18:25:26', '2024-12-18 18:29:26', '2024-12-18 18:29:26', FALSE,FALSE,4,'评论传播不实信息，可能误导他人'),
(25,'抱歉给您带来不好的体验', NULL, 2, 12360, 1, 1600, 11, NULL, NULL, 24, 11, 'MAYDAY商铺', NULL, NULL, '2024-12-18 18:25:26', '2024-12-18 18:29:26', '2024-12-18 18:29:26', FALSE,FALSE,4,'评论传播不实信息，可能误导他人');


/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;


DROP TABLE IF EXISTS `order_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_item` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `product_id` bigint DEFAULT NULL,
  `shop_id` bigint DEFAULT NULL,
  `quantity` INT  DEFAULT NULL,
  `customer_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=57148 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `order_item` WRITE;
/*!40000 ALTER TABLE `order_item` DISABLE KEYS */;
INSERT INTO `order_item` VALUES 
(12341,1550,10,1,514),
(12342,1551,11,2,514),
(12343,1559,11,1,514),
(12344,1559,11,1,515),
(12345,1559,11,2,514);
/*!40000 ALTER TABLE `order_item` ENABLE KEYS */;
UNLOCK TABLES;