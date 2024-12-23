DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
                                    `id` bigint NOT NULL AUTO_INCREMENT,
                                    `user_name` varchar(128) DEFAULT NULL,
                                    `password` varchar(128) DEFAULT NULL,
                                    `mobile` varchar(128) DEFAULT NULL,
                                    `name` varchar(128) DEFAULT NULL,
                                    `point` tinyint DEFAULT NULL,
                                    `status` tinyint DEFAULT NULL,
                                    `creator_id` bigint DEFAULT NULL,
                                    `creator_name` varchar(128) DEFAULT NULL,
                                    `modifier_id` bigint DEFAULT NULL,
                                    `modifier_name` varchar(128) DEFAULT NULL,
                                    `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                    `gmt_modified` datetime DEFAULT NULL,
                                    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11168 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `customer` (`id`, `user_name`, `password`, `mobile`, `name`, `point`, `status`, `creator_id`, `creator_name`, `modifier_id`, `modifier_name`, `gmt_create`, `gmt_modified`) VALUES
(1, 'user1', 'password1', '12345678901', 'Customer1', 10, 1, 0, 'admin', NULL, NULL, NOW(), NULL),
(2, 'user2', 'password2', '12345678902', 'Customer2', 20, 1,0, 'admin', NULL, NULL, NOW(), NULL),
(3, 'user3', 'password3', '12345678903', 'Customer3', 30, 1,0, 'admin', NULL, NULL, NOW(), NULL),
(4, 'user4', 'password4', '12345678904', 'Customer4', 40, 1,0, 'admin', NULL, NULL, NOW(), NULL),
(5, 'user5', 'password5', '12345678905', 'Customer5', 50, 1,0, 'admin', NULL, NULL, NOW(), NULL),
(6, 'user6', 'password6', '12345678906', 'Customer6', 60, 1,0, 'admin', NULL, NULL, NOW(), NULL),
(7, 'user7', 'password7', '12345678907', 'Customer7', 70, 0,0, 'admin', NULL, NULL, NOW(), NULL),
(8, 'user8', 'password8', '12345678908', 'Customer8', 80, 0,0, 'admin', NULL, NULL, NOW(), NULL),
(9, 'user9', 'password9', '12345678909', 'Customer9', 90, -1,0, 'admin', NULL, NULL, NOW(), NULL),
(10, 'user10', 'password10', '12345678910', 'Customer10', 100, -1,0, 'admin', NULL, NULL, NOW(), NULL);


DROP TABLE IF EXISTS `customer_address`;
CREATE TABLE `customer_address` (
                                    `id` bigint NOT NULL AUTO_INCREMENT,
                                    `customer_id` bigint DEFAULT NULL,
                                    `region_id` bigint DEFAULT NULL,
                                    `detail_address` varchar(500) DEFAULT NULL,
                                    `consignee` varchar(128) DEFAULT NULL,
                                    `mobile` varchar(128) DEFAULT NULL,
                                    `be_default` tinyint DEFAULT NULL,
                                    `creator_id` bigint DEFAULT NULL,
                                    `creator_name` varchar(128) DEFAULT NULL,
                                    `modifier_id` bigint DEFAULT NULL,
                                    `modifier_name` varchar(128) DEFAULT NULL,
                                    `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                    `gmt_modified` datetime DEFAULT NULL,
                                    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11168 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


INSERT INTO `customer_address` (`id`, `customer_id`, `region_id`, `detail_address`, `consignee`, `mobile`, `be_default`, `creator_id`, `creator_name`, `modifier_id`, `modifier_name`, `gmt_create`, `gmt_modified`) VALUES
(1, 1, 101, '北京市朝阳区建国路88号1001室', 'User1', '12345678901', 1, 1, 'user1', NULL, NULL, NOW(), NULL),
(2, 1, 102, '上海市浦东新区张江高科技园区盛大大厦2楼', 'User1', '12345678901', 0, 1, 'user1', NULL, NULL, NOW(), NULL),
(3, 2, 201, '广东省广州市天河路100号天汇大厦', 'User2', '12345678902', 1, 2, 'user2', NULL, NULL, NOW(), NULL),
(4, 2, 202, '浙江省杭州市西湖区紫荆花路88号', 'User2', '12345678902', 0, 2, 'user2', NULL, NULL, NOW(), NULL),
(5, 3, 301, '江苏省南京市玄武区珠江路100号', 'User3', '12345678903', 1, 3, 'user3', NULL, NULL, NOW(), NULL),
(6, 3, 302, '北京市海淀区上地十街10号', 'User3', '12345678903', 0, 3, 'user3', NULL, NULL, NOW(), NULL),
(7, 4, 401, '上海市徐汇区衡山路32号', 'User4', '12345678904', 1, 4, 'user4', NULL, NULL, NOW(), NULL),
(8, 4, 402, '四川省成都市高新区天府大道88号', 'User4', '12345678904', 0, 4, 'user4', NULL, NULL, NOW(), NULL),
(9, 5, 501, '湖北省武汉市江汉区解放大道200号', 'User5', '12345678905', 0, 5, 'user5', NULL, NULL, NOW(), NULL),
(10, 5, 502, '内蒙古自治区呼和浩特市赛罕区新华大街50号', 'User5', '12345678905', 0, 5, 'user5', NULL, NULL, NOW(), NULL),
(11, 5, 502, '内蒙古自治区包头市昆区钢铁大街10号', 'User5', '12345678905', 0, 5, 'user5', NULL, NULL, NOW(), NULL),
(12, 5, 502, '内蒙古自治区巴彦淖尔市临河区胜利街60号', 'User5', '12345678905', 0, 5, 'user5', NULL, NULL, NOW(), NULL),
(13, 5, 502, '内蒙古自治区通辽市科尔沁区长春路200号', 'User5', '12345678905', 0, 5, 'user5', NULL, NULL, NOW(), NULL),
(14, 5, 502, '内蒙古自治区兴安盟乌兰浩特市阳光路45号', 'User5', '12345678905', 0, 5, 'user5', NULL, NULL, NOW(), NULL),
(15, 5, 502, '内蒙古自治区通辽市科尔沁区长春路201号', 'User5', '12345678905', 0, 5, 'user5', NULL, NULL, NOW(), NULL),
(16, 5, 502, '内蒙古自治区鄂尔多斯市东胜区文化路88号', 'User5', '12345678905', 0, 5, 'user5', NULL, NULL, NOW(), NULL),
(17, 5, 502, '内蒙古自治区巴彦淖尔市临河区胜利街61号', 'User5', '12345678905', 0, 5, 'user5', NULL, NULL, NOW(), NULL),
(18, 5, 502, '内蒙古自治区巴彦淖尔市临河区胜利街62号', 'User5', '12345678905', 0, 5, 'user5', NULL, NULL, NOW(), NULL),
(19, 5, 502, '内蒙古自治区巴彦淖尔市临河区胜利街63号', 'User5', '12345678905', 0, 5, 'user5', NULL, NULL, NOW(), NULL),
(20, 5, 502, '内蒙古自治区巴彦淖尔市临河区胜利街64号', 'User5', '12345678905', 0, 5, 'user5', NULL, NULL, NOW(), NULL),
(21, 5, 502, '内蒙古自治区巴彦淖尔市临河区胜利街号65', 'User5', '12345678905', 0, 5, 'user5', NULL, NULL, NOW(), NULL),
(22, 5, 502, '内蒙古自治区巴彦淖尔市临河区胜利街66号', 'User5', '12345678905', 0, 5, 'user5', NULL, NULL, NOW(), NULL),
(23, 5, 502, '内蒙古自治区巴彦淖尔市临河区胜利街67号', 'User5', '12345678905', 0, 5, 'user5', NULL, NULL, NOW(), NULL),
(24, 5, 502, '内蒙古自治区巴彦淖尔市临河区胜利街68号', 'User5', '12345678905', 0, 5, 'user5', NULL, NULL, NOW(), NULL),
(25, 5, 502, '内蒙古自治区巴彦淖尔市临河区胜利街69号', 'User5', '12345678905', 0, 5, 'user5', NULL, NULL, NOW(), NULL),
(26, 5, 502, '内蒙古自治区巴彦淖尔市临河区胜利街70号', 'User5', '12345678905', 0, 5, 'user5', NULL, NULL, NOW(), NULL),
(27, 5, 502, '内蒙古自治区巴彦淖尔市临河区胜利街71号', 'User5', '12345678905', 0, 5, 'user5', NULL, NULL, NOW(), NULL),
(28, 5, 502, '内蒙古自治区巴彦淖尔市临河区胜利街72号', 'User5', '12345678905', 0, 5, 'user5', NULL, NULL, NOW(), NULL),
(29, 5, 502, '内蒙古自治区巴彦淖尔市临河区胜利街73号', 'User5', '12345678905', 0, 5, 'user5', NULL, NULL, NOW(), NULL);

DROP TABLE IF EXISTS `customer_cart`;
CREATE TABLE `customer_cart` (
                                 `id` bigint NOT NULL AUTO_INCREMENT,
                                 `customer_id` bigint DEFAULT NULL,
                                 `product_id` bigint DEFAULT NULL,
                                 `quantity` bigint DEFAULT NULL,
                                 `creator_id` bigint DEFAULT NULL,
                                 `creator_name` varchar(128) DEFAULT NULL,
                                 `modifier_id` bigint DEFAULT NULL,
                                 `modifier_name` varchar(128) DEFAULT NULL,
                                 `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                 `gmt_modified` datetime DEFAULT NULL,
                                 `product_name` varchar(128) DEFAULT NULL,
                                 `spec` varchar(128) DEFAULT NULL,


                                 PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4408 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
INSERT INTO `customer_cart` (`id`, `customer_id`, `product_id`, `quantity`, `creator_id`, `creator_name`, `modifier_id`, `modifier_name`, `gmt_create`, `gmt_modified`,`product_name`,`spec`) VALUES
(1, 1, 101, 2, 1, 'user1', NULL, NULL, NOW(), NULL,'清凉绿茶','500Ml瓶装'),
(2, 1, 102, 1, 1, 'user1', NULL, NULL, NOW(), NULL,'闪速U盘','32GB'),
(3, 2, 201, 3, 2, 'user2', NULL, NULL, NOW(), NULL,'儿童口罩','5个/包'),
(4, 2, 202, 2, 2, 'user2', NULL, NULL, NOW(), NULL,'高清平板电脑','8英寸'),
(5, 3, 301, 1, 3, 'user3', NULL, NULL, NOW(), NULL,'无线蓝牙耳机','超薄款'),
(6, 3, 302, 4, 3, 'user3', NULL, NULL, NOW(), NULL,'精美画框','30cm x 40cm'),
(7, 4, 401, 2, 4, 'user4', NULL, NULL, NOW(), NULL,'柔软床单','100%纯'),
(8, 4, 402, 3, 4, 'user4', NULL, NULL, NOW(), NULL,'复印纸','15页/包'),
(9, 5, 501, 1, 5, 'user5', NULL, NULL, NOW(), NULL,'无线鼠标','黑色款'),
(10, 5, 502, 5, 5, 'user5', NULL, NULL, NOW(), NULL,'LED台灯','70cm');


DROP TABLE IF EXISTS `customer_coupon`;
CREATE TABLE `customer_coupon` (
                                    `id` bigint NOT NULL AUTO_INCREMENT,
                                    `customer_id` bigint DEFAULT NULL,
                                    `act_id` bigint DEFAULT NULL,
                                    `name` varchar(500) DEFAULT NULL,
                                    `gmt_begin` datetime DEFAULT NULL,
                                    `gmt_end` datetime DEFAULT NULL,
                                    `status` tinyint DEFAULT NULL,
                                    `creator_id` bigint DEFAULT NULL,
                                    `creator_name` varchar(128) DEFAULT NULL,
                                    `modifier_id` bigint DEFAULT NULL,
                                    `modifier_name` varchar(128) DEFAULT NULL,
                                    `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                    `gmt_modified` datetime DEFAULT NULL,
                                    `gmt_receive` datetime DEFAULT NULL,
                                    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11168 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `customer_coupon` (`id`, `customer_id`, `act_id`, `name`, `gmt_begin`, `gmt_end`, `status`, `creator_id`, `creator_name`, `modifier_id`, `modifier_name`, `gmt_create`, `gmt_modified`,`gmt_receive`) VALUES
(1, 1, 1, '新年折扣', '2024-01-01 00:00:00', '2024-01-31 23:59:59', 1, 1, 'user1', NULL, NULL, NOW(), NULL,'2024-12-22 20:40:59'),
(2, 1, 2, '春季大促', '2024-02-01 00:00:00', '2024-02-28 23:59:59', 0, 1, 'user1', NULL, NULL, NOW(), NULL,'2024-01-31 23:59:59'),
(3, 2, 3, '黑五促销', '2024-06-01 00:00:00', '2024-06-30 23:59:59', 1, 2, 'user2', NULL, NULL, NOW(), NULL,'2024-01-31 23:59:59'),
(4, 2, 4, '圣诞专享', '2024-11-01 00:00:00', '2024-11-30 23:59:59', 1, 2, 'user2', NULL, NULL, NOW(), NULL,'2024-01-31 23:59:59'),
(5, 3, 5, '新用户专享', '2024-12-01 00:00:00', '2024-12-25 23:59:59', 0, 3, 'user3', NULL, NULL, NOW(), NULL,'2024-01-31 23:59:59'),
(6, 3, 6, '周末狂欢', '2024-03-01 00:00:00', '2024-03-15 23:59:59', 1, 3, 'user3', NULL, NULL, NOW(), NULL,'2024-01-31 23:59:59'),
(7, 4, 7, '会员专享', '2024-04-01 00:00:00', '2024-04-30 23:59:59', 1, 4, 'user4', NULL, NULL, NOW(), NULL,'2024-01-31 23:59:59'),
(8, 4, 8, '周中惊喜', '2024-05-01 00:00:00', '2024-05-07 23:59:59', 1, 4, 'user4', NULL, NULL, NOW(), NULL,'2024-01-31 23:59:59'),
(9, 5, 9, '双十一', '2024-11-01 00:00:00', '2024-11-11 23:59:59', 1, 5, 'user5', NULL, NULL, NOW(), NULL,'2024-01-31 23:59:59'),
(10, 5, 10, '夏季清仓', '2024-11-25 00:00:00', '2024-12-01 23:59:59', 1, 5, 'user5', NULL, NULL, NOW(), NULL,'2024-01-31 23:59:59');


DROP TABLE IF EXISTS `coupon_act`;
CREATE TABLE `coupon_act` (
                                   `id` bigint NOT NULL AUTO_INCREMENT,
                                   `name` varchar(500) DEFAULT NULL,
                                   `description` varchar(500) DEFAULT NULL,
                                   `constraint_type` tinyint DEFAULT NULL,
                                   `gmt_begin` datetime DEFAULT NULL,
                                   `gmt_end` datetime DEFAULT NULL,
                                   `status` tinyint DEFAULT NULL,
                                   `max_user` bigint DEFAULT NULL,
                                   `max_count` bigint DEFAULT NULL,
                                   `remain_count` bigint DEFAULT NULL,
                                   `creator_id` bigint DEFAULT NULL,
                                   `creator_name` varchar(128) DEFAULT NULL,
                                   `modifier_id` bigint DEFAULT NULL,
                                   `modifier_name` varchar(128) DEFAULT NULL,
                                   `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                   `gmt_modified` datetime DEFAULT NULL,
                                   `min_interval` bigint DEFAULT NULL,

                                   PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11168 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `coupon_act` (`id`, `name`, `description`, `constraint_type`, `gmt_begin`, `gmt_end`, `status`, `max_user`, `max_count`, `remain_count`, `creator_id`, `creator_name`, `modifier_id`, `modifier_name`, `gmt_create`, `gmt_modified`,`min_interval`) VALUES
(1, '新年折扣', '庆祝新年，全场商品享受8折优惠', 1, '2024-01-01 00:00:00', '2024-01-31 23:59:59', 1, NULL, 100, 50, 0, 'admin', NULL, NULL, NOW(), NULL,6000000),
(2, '春季大促', '春季特卖，最高可享50%的折扣', 1, '2024-02-01 00:00:00', '2024-02-28 23:59:59', 1, NULL, 200, 150, 0, 'admin', NULL, NULL, NOW(), NULL,600),
(3, '黑五促销', '黑五购物节，全场商品超低折扣', 1, '2024-06-01 00:00:00', '2024-06-30 23:59:59', 0, NULL, 150, 75, 0, 'admin', NULL, NULL, NOW(), NULL,600),
(4, '圣诞专享', '部分商品低至3折', 1, '2024-11-01 00:00:00', '2024-11-30 23:59:59', 1, NULL, 300, 0, 0, 'admin', NULL, NULL, NOW(), NULL,600),
(5, '新用户专享', '首次注册用户领取100元优惠券，限量3000张', 1, '2024-12-01 00:00:00', '2024-12-25 23:59:59', 1, NULL, 400, 250, 0, 'admin', NULL, NULL, NOW(), NULL,600),
(6, '周末狂欢', '购物满300元减50元', 2, '2024-03-01 00:00:00', '2024-03-15 23:59:59', 1, 1, NULL, NULL, 0, 'admin', NULL, NULL, NOW(), NULL,NULL),
(7, '会员专享', '会员独享8折优惠，首次购买可叠加使用', 2, '2024-04-01 00:00:00', '2024-04-30 23:59:59', 1, 2, NULL, NULL, 0, 'admin', NULL, NULL, NOW(), NULL,NULL),
(8, '周中惊喜', '每周三，特定商品享受5折优惠，仅限1000名用户', 2, '2024-05-01 00:00:00', '2024-05-07 23:59:59', 0, 1, NULL, NULL, 0, 'admin', NULL, NULL, NOW(), NULL,NULL),
(9, '双十一', '双十一购物节，全场商品满减最高可达300元', 2, '2024-11-01 00:00:00', '2024-11-11 23:59:59', 1, 2, NULL, NULL, 0, 'admin', NULL, NULL, NOW(), NULL,NULL),
(10,'夏季清仓', '夏季清仓特卖，所有商品低至2折', 2, '2024-11-25 00:00:00', '2024-12-01 23:59:59', 1, 1, NULL, NULL, 0, 'admin', NULL, NULL, NOW(), NULL,NULL);